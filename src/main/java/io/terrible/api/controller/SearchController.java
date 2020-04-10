/* Licensed under Apache-2.0 */

package io.terrible.api.controller;

import io.terrible.api.domain.MediaFile;
import io.terrible.api.services.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search/index")
    public Mono<String> index() {

        return searchService.createIndex("media").then(searchService.index("media"));
    }

    @GetMapping("/search")
    public Flux<MediaFile> search(@RequestParam final String query) {

        return searchService.search(query);
    }

}
