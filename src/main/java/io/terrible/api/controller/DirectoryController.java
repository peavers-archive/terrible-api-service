/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.Directory;
import io.terrible.api.services.DirectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class DirectoryController {

  private final DirectoryService directoryService;

  @GetMapping("/directory")
  public Flux<Directory> findAll() {

    log.info("findAll");

    return directoryService.findAll();
  }

  @PostMapping("/directory")
  public Mono<Directory> save(@RequestBody final Directory directory) {

    log.info("save {}", directory);

    return directoryService.save(directory);
  }

  @GetMapping("/directory/{directoryId}")
  public Mono<Directory> findById(@PathVariable final String directoryId) {

    log.info("findById {}", directoryId);

    return directoryService.findById(directoryId);
  }

  @DeleteMapping("/directory/{directoryId}")
  public Mono<Void> deleteById(@PathVariable final String directoryId) {

    log.info("deleteById {}", directoryId);

    return directoryService.deleteById(directoryId);
  }
}
