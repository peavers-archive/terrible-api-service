package io.terrible.api.services;

import io.terrible.api.domain.MediaFile;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SearchServiceImpl implements SearchService {

    private static final String INDEX = "media";

    private final WebClient webClient = WebClient.create("http://localhost:8082");

    @Override
    public Mono<String> createIndex(final String index) {

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("index", INDEX);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/search/create-index").queryParams(params).build())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> index(final String index) {

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("index", INDEX);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/search/index").queryParams(params).build())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Flux<MediaFile> search(final String query) {

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("index", INDEX);
        params.add("query", query);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search").queryParams(params).build())
                .retrieve()
                .bodyToFlux(MediaFile.class);
    }

}
