/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.GroupedMediaFile;
import io.terrible.api.domain.MediaFile;
import io.terrible.api.services.HistoryService;
import io.terrible.api.services.MediaFileService;
import io.terrible.api.services.MediaListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MediaFileController {

  private final MediaListService mediaListService;

  private final MediaFileService mediaFileService;

  private final HistoryService historyService;

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

    return mediaFileService
        .deleteAll()
        .then(mediaListService.deleteAll())
        .then(historyService.deleteAll());
  }

  @GetMapping("/group/media-files")
  public Flux<GroupedMediaFile> group(@RequestParam final String group) {

    return mediaFileService.findAllGroupedByDate(group);
  }
}
