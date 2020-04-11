/* Licensed under Apache-2.0 */
package io.terrible.api.repository;

import io.terrible.api.domain.MediaList;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaListRepository extends ReactiveMongoRepository<MediaList, String> {}
