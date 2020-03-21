/* Licensed under Apache-2.0 */
package io.terrible.api.repository;

import io.terrible.api.domain.MediaFile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface MediaFileRepository extends ReactiveMongoRepository<MediaFile, String> {

  Mono<MediaFile> findByAbsolutePath(String absolutePath);

  Flux<MediaFile> findAllByThumbnailsIsNull();
}
