/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.domain.ThumbnailList;
import io.terrible.api.services.MediaFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/thumbnails")
@RequiredArgsConstructor
public class ThumbnailController {

  private final MediaFileService mediaFileService;

  @PostMapping
  public Mono<MediaFile> save(@RequestBody final ThumbnailList thumbnails) {

    return mediaFileService
        .findByPath(thumbnails.getVideoPath())
        .doOnNext(mediaFile -> mediaFile.setThumbnails(thumbnails.getThumbnails()))
        .flatMap(mediaFileService::save);
  }
}
