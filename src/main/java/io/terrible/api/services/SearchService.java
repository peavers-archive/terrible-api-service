/* Licensed under Apache-2.0 */
package io.terrible.api.services;

import java.io.IOException;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.search.SearchResponse;
import reactor.core.publisher.Flux;

/** @author Chris Turner (chris@forloop.space) */
public interface SearchService {

  SearchResponse search(String query) throws IOException;

  Flux<BulkRequest> bulkIndex();
}
