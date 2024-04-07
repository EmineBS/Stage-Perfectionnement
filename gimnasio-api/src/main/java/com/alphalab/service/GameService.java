package com.alphalab.service;

import com.alphalab.service.dto.GameDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface GameService {
    /**
     * Save a game.
     *
     * @param gameDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<GameDTO> save(GameDTO gameDTO);

    /**
     * Updates a game.
     *
     * @param gameDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<GameDTO> update(GameDTO gameDTO);

    /**
     * Partially updates a badge.
     *
     * @param gameDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<GameDTO> partialUpdate(GameDTO gameDTO);

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<GameDTO> findAll(Pageable pageable);

    /**
     * Returns the number of badges available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" game.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<GameDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

}