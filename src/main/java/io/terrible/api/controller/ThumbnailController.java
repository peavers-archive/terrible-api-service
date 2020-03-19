/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.domain.ThumbnailList;
import io.terrible.api.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ThumbnailController {

  private final MediaFileRepository mediaFileRepository;

  @PostMapping("/thumbnails")
  public Mono<MediaFile> save(@RequestBody final ThumbnailList thumbnails) {

    return mediaFileRepository
        .findByAbsolutePath(thumbnails.getVideoPath())
        .doOnNext(mediaFile -> mediaFile.setThumbnails(thumbnails.getThumbnails()))
        .flatMap(mediaFileRepository::save);
  }
}
