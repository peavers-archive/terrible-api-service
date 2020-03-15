/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MediaFileController {

    private final MediaFileRepository mediaFileRepository;

    @GetMapping("/media-files")
    public Flux<MediaFile> findAll() {

        return mediaFileRepository.findAll();
    }

    @PostMapping("/media-files")
    public Mono<MediaFile> save(@RequestBody final MediaFile mediaFile) {

        return mediaFileRepository.save(mediaFile);
    }
}
