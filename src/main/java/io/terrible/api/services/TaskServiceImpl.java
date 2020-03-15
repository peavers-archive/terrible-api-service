/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final WebClient webClient =
      WebClient.create("http://localhost:8080"); // io.terrible.processor

  private final MediaFileRepository mediaFileRepository;

  @Override
  public Flux<Boolean> createThumbnails() {

    return mediaFileRepository
        .findAll()
        .map(MediaFile::getAbsolutePath)
        .flatMap(
            path ->
                webClient
                    .get()
                    .uri(
                        uriBuilder ->
                            uriBuilder
                                .pathSegment("task")
                                .pathSegment("thumbnails")
                                .queryParam("path", path)
                                .build())
                    .retrieve()
                    .bodyToMono(Boolean.class));
  }

  @Override
  public Mono<Boolean> scanDirectory(final String path) {

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .pathSegment("task")
                    .pathSegment("directories")
                    .queryParam("path", path)
                    .build())
        .retrieve()
        .bodyToMono(Boolean.class);
  }
}
