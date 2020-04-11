/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.MediaList;
import io.terrible.api.repository.MediaListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaListServiceImpl implements MediaListService {

  private final MediaListRepository repository;

  @Override
  public Flux<MediaList> findAll() {

    return repository.findAll();
  }

  @Override
  public Mono<MediaList> findById(final String id) {

    return repository.findById(id);
  }

  @Override
  public Mono<MediaList> save(final MediaList mediaList) {

    return repository.save(mediaList);
  }

  @Override
  public Mono<Void> deleteAll() {

    return repository.deleteAll();
  }

  @Override
  public Mono<Void> deleteById(final String id) {

    return repository.deleteById(id);
  }
}
