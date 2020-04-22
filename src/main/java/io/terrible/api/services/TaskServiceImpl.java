/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.configuration.TerribleConfig;
import io.terrible.api.domain.MediaFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TerribleConfig config;

  private final MediaFileService mediaFileService;

  private final WebClient webClient = WebClient.create();

  @Override
  public Flux<String> createThumbnails() {

    log.info("Create thumbnails");

    return mediaFileService
        .findAllByThumbnailsIsNull()
        .map(MediaFile::getPath)
        .flatMap(
            path ->
                webClient
                    .get()
                    .uri(
                        uriBuilder ->
                            uriBuilder
                                .scheme(config.getTaskProcessor().getScheme())
                                .host(config.getTaskProcessor().getHost())
                                .port(config.getTaskProcessor().getPort())
                                .pathSegment("task")
                                .pathSegment("thumbnails")
                                .queryParam("path", path)
                                .build())
                    .retrieve()
                    .bodyToMono(String.class));
  }

  @Override
  public Mono<String> scanDirectory(final String path) {

    log.info("Scan directory");

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .scheme(config.getTaskProcessor().getScheme())
                    .host(config.getTaskProcessor().getHost())
                    .port(config.getTaskProcessor().getPort())
                    .pathSegment("task")
                    .pathSegment("directories")
                    .queryParam("path", path)
                    .build())
        .retrieve()
        .bodyToMono(String.class);
  }
}
