/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {

  private final MediaFileRepository repository;

  @Override
  public Flux<MediaFile> findAll() {
    log.info("Find all");

    return repository.findAll();
  }

  @Override
  public Flux<MediaFile> findAllByThumbnailsIsNull() {
    log.info("Find all by thumbnail is null");

    return repository.findAllByThumbnailsIsNull();
  }

  @Override
  public Mono<MediaFile> findById(final String id) {
    log.info("Find by id {}", id);

    return repository.findById(id);
  }

  @Override
  public Mono<MediaFile> findByAbsolutePath(final String absolutePath) {
    log.info("Find by absolute path {}", absolutePath);

    return repository.findByAbsolutePath(absolutePath);
  }

  @Override
  public Mono<MediaFile> save(final MediaFile mediaFile) {
    log.info("Save {}", mediaFile);

    return repository.save(mediaFile);
  }

  @Override
  public Mono<Void> deleteAll() {
    log.info("Delete all");

    return repository.deleteAll();
  }
}
