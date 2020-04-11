/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

  Flux<Boolean> createThumbnails();

  Mono<Boolean> scanDirectory(String path);
}
