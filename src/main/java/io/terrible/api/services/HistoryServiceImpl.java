package io.terrible.api.services;

import io.terrible.api.domain.History;
import io.terrible.api.domain.MediaFile;
import io.terrible.api.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repository;

    @Override
    public Flux<History> findAll() {

        return repository.findAll();
    }

    @Override
    public Mono<History> addToHistory(final MediaFile mediaFile) {

        final String historyId = "1"; // Just use one document at the moment.

        return repository.findById(historyId)
                .defaultIfEmpty(History.builder().id(historyId).build())
                .doOnNext(history -> history.getResults().add(mediaFile))
                .flatMap(repository::save);
    }

    @Override
    public Mono<History> findById(final String id) {

        return repository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(final String id) {

        return repository.deleteById(id);
    }

}
