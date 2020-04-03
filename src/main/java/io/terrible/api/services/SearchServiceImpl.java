/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.repository.MediaFileRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

  private final MediaFileRepository mediaFileRepository;

  private final RestHighLevelClient client;

  @Override
  public SearchResponse search(final String query) throws IOException {

    final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(QueryBuilders.termQuery("name", query));
    sourceBuilder.from(0);
    sourceBuilder.size(5);
    sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

    final SearchRequest searchRequest = new SearchRequest();
    searchRequest.indices("media");
    searchRequest.source(sourceBuilder);

    return client.search(searchRequest, RequestOptions.DEFAULT);
  }

  @Override
  public Flux<BulkRequest> bulkIndex() {
    final BulkRequest request = new BulkRequest();

    return mediaFileRepository
        .findAll()
        .map(this::createIndexRequest)
        .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty))
        .map(request::add)
        .doOnComplete(() -> sendRequest(request));
  }

  private Optional<IndexRequest> createIndexRequest(final MediaFile mediaFile) {

    try {
      final XContentBuilder builder = XContentFactory.jsonBuilder();
      builder.startObject();
      {
        builder.field("name", mediaFile.getName());
        builder.field("path", mediaFile.getAbsolutePath());
        builder.timeField("importedTime", mediaFile.getImportedTime());
      }
      builder.endObject();

      return Optional.of(new IndexRequest("media").id(mediaFile.getId()).source(builder));
    } catch (final Exception e) {
      log.warn("Unable to index {}", mediaFile.getName());
      return Optional.empty();
    }
  }

  private void sendRequest(final BulkRequest request) {
    try {
      client.bulk(request, RequestOptions.DEFAULT);
    } catch (final IOException e) {
      log.error("Unable to send bulk request {} {}", e.getMessage(), e);
    }
  }
}
