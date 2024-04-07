package com.alphalab.service;

import com.alphalab.service.dto.GymDTO;
import com.alphalab.service.dto.OffrePackDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.OffrePack}.
 */
public interface OffrePackService {
    /**
     * Save a pack.
     *
     * @param packDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<OffrePackDTO> save(OffrePackDTO packDTO);

    /**
     * Updates a pack.
     *
     * @param packDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<OffrePackDTO> update(OffrePackDTO packDTO);

    /**
     * Partially updates a pack.
     *
     * @param packDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<OffrePackDTO> partialUpdate(OffrePackDTO packDTO);

    /**
     * Get all the packs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<OffrePackDTO> findAll(Pageable pageable);

    /**
     * Returns the number of packs available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" pack.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<OffrePackDTO> findOne(Long id);

    /**
     * Delete the "id" pack.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get all packs by badge.
     *
     * @return the persisted entity.
     */
    Flux<OffrePackDTO> findAllPacksByGym(Pageable pageable, Long gym_id);

    /**
     * Count all packs by badge.
     *
     * @return the persisted entity.
     */
    Mono<Long> countAllPacksByGym(Long gym_id);
}
