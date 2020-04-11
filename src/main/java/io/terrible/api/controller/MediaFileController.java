/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.services.MediaFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MediaFileController {

  private final MediaFileService mediaFileService;

  @GetMapping("/media-files")
  public Flux<MediaFile> findAll() {

    return mediaFileService.findAll();
  }

  @GetMapping("/media-files/{id}")
  public Mono<MediaFile> findById(@PathVariable final String id) {

    return mediaFileService.findById(id);
  }

  @PostMapping("/media-files")
  public Mono<MediaFile> save(@RequestBody final MediaFile mediaFile) {

    return mediaFileService.save(mediaFile);
  }

  @DeleteMapping("/media-files")
  public Mono<Void> deleteAll() {

    return mediaFileService.deleteAll();
  }
}
