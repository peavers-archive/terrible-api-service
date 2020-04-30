/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.Directory;
import io.terrible.api.services.DirectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/directory")
@RequiredArgsConstructor
public class DirectoryController {

  private final DirectoryService directoryService;

  @GetMapping
  public Mono<Directory> findAll() {

    return directoryService.findAll().next();
  }

  @PostMapping
  public Mono<Directory> save(@RequestBody final Directory directory) {

    return directoryService.save(directory);
  }

  @GetMapping("/{id}")
  public Mono<Directory> findById(@PathVariable final String id) {

    return directoryService.findById(id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteById(@PathVariable final String id) {

    return directoryService.deleteById(id);
  }
}
