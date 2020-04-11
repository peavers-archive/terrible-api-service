/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.MediaList;
import io.terrible.api.services.MediaListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MediaListController {

  private final MediaListService mediaListService;

  @GetMapping("media-lists/{id}")
  public Mono<MediaList> findById(@PathVariable final String id) {
    return mediaListService.findById(id);
  }

  @GetMapping("media-lists")
  public Flux<MediaList> findAll() {
    return mediaListService.findAll();
  }

  @PostMapping("media-lists")
  public Mono<MediaList> save(@RequestBody final MediaList mediaList) {
    return mediaListService.save(mediaList);
  }

  @DeleteMapping("media-lists/{id}")
  public Mono<Void> deleteById(@PathVariable final String id) {
    return mediaListService.deleteById(id);
  }

  @DeleteMapping("media-lists")
  public Mono<Void> delete() {
    return mediaListService.deleteAll();
  }
}
