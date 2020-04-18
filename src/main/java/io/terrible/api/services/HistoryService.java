package io.terrible.api.services;

import io.terrible.api.domain.History;
import io.terrible.api.domain.MediaFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HistoryService {

    Flux<History> findAll();

    Mono<History> addToHistory(MediaFile mediaFile);

    Mono<History> findById(String directoryId);

    Mono<Void> deleteById(String directoryId);
    
}
