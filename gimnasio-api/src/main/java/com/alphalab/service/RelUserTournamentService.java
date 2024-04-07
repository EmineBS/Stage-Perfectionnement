package com.alphalab.service;

import com.alphalab.service.dto.RelUserTournamentDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface RelUserTournamentService {
    Mono<RelUserTournamentDTO> save(RelUserTournamentDTO relUserTournamentDTO);

    Mono<RelUserTournamentDTO> update(RelUserTournamentDTO relUserTournamentDTO);

    Mono<RelUserTournamentDTO> partialUpdate(RelUserTournamentDTO relUserTournamentDTO);

    Flux<RelUserTournamentDTO> findAll(Pageable pageable);

    Mono<Long> countAll();

    Mono<RelUserTournamentDTO> findOne(Long id);

    Mono<Void> delete(Long id);

    Flux<RelUserTournamentDTO> findUsersByTournament(Pageable pageable, Long tournament_id);

    Mono<Long> countUsersByTournament(Long tournament_id);
}
