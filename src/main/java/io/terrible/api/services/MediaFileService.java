/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.MediaFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MediaFileService {

  Flux<MediaFile> findAll();

  Flux<MediaFile> findAllByThumbnailsIsNull();

  Mono<MediaFile> findById(String id);

  Mono<MediaFile> findByPath(String absolutePath);

  Mono<MediaFile> save(MediaFile mediaFile);

  Mono<Void> deleteAll();

  Flux<Object> findAllGroupedByDate();
}
