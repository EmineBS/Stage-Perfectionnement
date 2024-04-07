package com.alphalab.service;

import com.alphalab.service.dto.TournamentDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TournamentService {
    /**
     * Save a badge.
     *
     * @param tournamentDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<TournamentDTO> save(TournamentDTO tournamentDTO);

    /**
     * Updates a badge.
     *
     * @param tournamentDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<TournamentDTO> update(TournamentDTO tournamentDTO);

    /**
     * Partially updates a badge.
     *
     * @param tournamentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<TournamentDTO> partialUpdate(TournamentDTO tournamentDTO);

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TournamentDTO> findAll(Pageable pageable);

    /**
     * Returns the number of badges available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" badge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<TournamentDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     *
     * @param gameid the id of the entity.
     */
    Flux<TournamentDTO> findAllTournamentsOfGame(Pageable pageable, Long gameid);
    /**
     *
     * @param gameid the id of the entity.
     */
    Mono<Long> countFindAllTournamentsOfGame(Long gameid);
}
