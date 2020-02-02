/* Licensed under Apache-2.0 */
package io.terrible.api.repository;

import io.terrible.api.domain.Directory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/** @author Chris Turner (chris@forloop.space) */
public interface DirectoryRepository extends ReactiveMongoRepository<Directory, String> {}
