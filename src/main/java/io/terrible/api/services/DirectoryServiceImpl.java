/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import io.terrible.api.domain.Directory;
import io.terrible.api.repository.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {

  private final DirectoryRepository directoryRepository;

  @Override
  public Flux<Directory> findAll() {

    return directoryRepository.findAll();
  }

  @Override
  public Mono<Directory> save(final Directory directory) {

    // Don't save empty directories
    if (StringUtils.isAnyEmpty(directory.getPath())) {
      return Mono.empty();
    }

    // Hard replace all values
    return directoryRepository.save(directory);
  }

  @Override
  public Mono<Directory> findById(final String directoryId) {

    return directoryRepository.findById(directoryId);
  }

  @Override
  public Mono<Void> deleteById(final String directoryId) {

    return directoryRepository.deleteById(directoryId);
  }
}
