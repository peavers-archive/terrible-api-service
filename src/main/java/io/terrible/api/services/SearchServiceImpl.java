/* Licensed under Apache-2.0 */

package io.terrible.api.services;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.Strings.isNullOrEmpty;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    public static final String INDEX = "media";

    private final MediaFileRepository mediaFileRepository;

    private final RestHighLevelClient client;

    @Override
    public SearchResponse search(final String query) throws IOException {

        final String searchField = "absolutePath";

        log.info("Search query {}", query);

        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termsQuery(searchField, query));
        sourceBuilder.from(0);
        sourceBuilder.size(50);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        final SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        searchRequest.source(sourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    @Override
    public Flux<BulkRequest> bulkIndex() {

        try {
            Resource resource = new ClassPathResource("search/_settings.json");
            createIndex(INDEX, false, FileUtils.readFileToString(resource.getFile(), StandardCharsets.UTF_8));

            final BulkRequest request = new BulkRequest();

            return mediaFileRepository.findAll()
                    .map(this::createIndexRequest)
                    .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty))
                    .map(request::add)
                    .doOnComplete(() -> sendRequest(request));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Flux.empty();

    }

    public void refresh(String index) throws IOException {

        log.info("refresh index [{}]", index);
        RefreshRequest request = new RefreshRequest();
        if (!isNullOrEmpty(index)) {
            request.indices(index);
        }
        RefreshResponse refresh = client.indices().refresh(request, RequestOptions.DEFAULT);
        log.info("refresh response: {}", refresh);
    }

    private Optional<IndexRequest> createIndexRequest(final MediaFile mediaFile) {

        try {
            final XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("name", mediaFile.getName());
                builder.field("absolutePath", mediaFile.getAbsolutePath());
                //                builder.timeField("importedTime", mediaFile.getImportedTime());
            }
            builder.endObject();

            return Optional.of(new IndexRequest(INDEX).id(mediaFile.getId()).source(builder));
        } catch (final Exception e) {
            log.warn("Unable to index {}", mediaFile.getName());
            return Optional.empty();
        }
    }

    public void createIndex(String index, boolean ignoreErrors, String indexSettings) throws IOException {

        CreateIndexRequest cir = new CreateIndexRequest(index);
        if (!isNullOrEmpty(indexSettings)) {
            cir.source(indexSettings, XContentType.JSON);
        }

        try {
            client.indices().create(cir, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            if (e.getMessage().contains("resource_already_exists_exception") && !ignoreErrors) {
                throw new RuntimeException("index already exists");
            }
            if (!e.getMessage().contains("resource_already_exists_exception")) {
                throw e;
            }
        }

        waitForHealthyIndex(index);
    }

    public void waitForHealthyIndex(String index) throws IOException {

        client.cluster().health(new ClusterHealthRequest(index).waitForYellowStatus(), RequestOptions.DEFAULT);
    }

    private void sendRequest(final BulkRequest request) {

        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (final IOException e) {
            log.error("Unable to send bulk request {} {}", e.getMessage(), e);
        }
    }

}
