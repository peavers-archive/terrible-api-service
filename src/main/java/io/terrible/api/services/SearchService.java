/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.MediaFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
public interface SearchService {

  Mono<String> createIndex(String index) ;

  Mono<String> index(String index);

  Flux<MediaFile> search(String query);
}
