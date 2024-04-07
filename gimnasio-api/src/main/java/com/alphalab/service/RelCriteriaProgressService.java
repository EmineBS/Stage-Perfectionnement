package com.alphalab.service;

import com.alphalab.service.dto.CriteriaBadgeProgressDTO;
import com.alphalab.service.dto.RelCriteriaProgressDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.RelCriteriaProgress}.
 */
public interface RelCriteriaProgressService {
    /**
     * Save a relCriteriaProgress.
     *
     * @param relCriteriaProgressDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RelCriteriaProgressDTO> save(RelCriteriaProgressDTO relCriteriaProgressDTO);

    /**
     * Updates a relCriteriaProgress.
     *
     * @param relCriteriaProgressDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<RelCriteriaProgressDTO> update(RelCriteriaProgressDTO relCriteriaProgressDTO);

    /**
     * Partially updates a relCriteriaProgress.
     *
     * @param relCriteriaProgressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<RelCriteriaProgressDTO> partialUpdate(RelCriteriaProgressDTO relCriteriaProgressDTO);

    /**
     * Get all the relCriteriaProgresss.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RelCriteriaProgressDTO> findAll(Pageable pageable);

    /**
     * Returns the number of relCriteriaProgresss available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" relCriteriaProgress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<RelCriteriaProgressDTO> findOne(Long id);

    /**
     * Delete the "id" relCriteriaProgress.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get all the relCriteriaProgresss by badge.
     *
     * @return the list of entities.
     */
    Flux<CriteriaBadgeProgressDTO> findAllCriteriaByBadge(Long gymId, Long badgeId);
}
