package com.alphalab.service;

import com.alphalab.service.dto.AmineDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * Service Interface for managing {@link com.alphalab.domain.Badge}.
 */
public interface AmineService {
    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<AmineDTO> save(AmineDTO badgeDTO);

    /**
     * Updates a badge.
     *
     * @param badgeDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<AmineDTO> update(AmineDTO badgeDTO);

    /**
     * Partially updates a badge.
     *
     * @param amineDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<AmineDTO> partialUpdate(AmineDTO amineDTO);

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<AmineDTO> findAll(Pageable pageable);

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
    Mono<AmineDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

}
