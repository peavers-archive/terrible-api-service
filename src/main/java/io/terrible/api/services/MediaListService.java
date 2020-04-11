/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.MediaList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
public interface MediaListService {

  Flux<MediaList> findAll();

  Mono<MediaList> findById(String id);

  Mono<MediaList> save(MediaList mediaFile);

  Mono<Void> deleteAll();

  Mono<Void> deleteById(String id);
}
