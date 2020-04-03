/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.services.SearchService;
import java.io.IOException;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.search.SearchHit;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  @GetMapping("/index")
  public Flux<BulkRequest> index() {
    return searchService.bulkIndex();
  }

  @GetMapping("/search")
  public Flux<SearchHit> search(@RequestParam final String query) throws IOException {

    final Iterator<SearchHit> sourceIterator = searchService.search(query).getHits().iterator();
    final Iterable<SearchHit> iterable = () -> sourceIterator;

    return Flux.fromIterable(iterable);
  }
}
