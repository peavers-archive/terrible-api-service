/* Licensed under Apache-2.0 */
package io.terrible.api.repository;

import io.terrible.api.domain.MediaFile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface MediaFileRepository extends ReactiveMongoRepository<MediaFile, Long> {}