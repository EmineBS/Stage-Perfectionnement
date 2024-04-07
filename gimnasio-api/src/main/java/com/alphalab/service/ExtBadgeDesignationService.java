package com.alphalab.service;

import com.alphalab.service.dto.ExtBadgeDesignationDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.ExtBadgeDesignation}.
 */
public interface ExtBadgeDesignationService {
    /**
     * Save a extBadgeDesignation.
     *
     * @param extBadgeDesignationDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<ExtBadgeDesignationDTO> save(ExtBadgeDesignationDTO extBadgeDesignationDTO);

    /**
     * Updates a extBadgeDesignation.
     *
     * @param extBadgeDesignationDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ExtBadgeDesignationDTO> update(ExtBadgeDesignationDTO extBadgeDesignationDTO);

    /**
     * Partially updates a extBadgeDesignation.
     *
     * @param extBadgeDesignationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ExtBadgeDesignationDTO> partialUpdate(ExtBadgeDesignationDTO extBadgeDesignationDTO);

    /**
     * Get all the extBadgeDesignations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<ExtBadgeDesignationDTO> findAll(Pageable pageable);

    /**
     * Returns the number of extBadgeDesignations available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" extBadgeDesignation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ExtBadgeDesignationDTO> findOne(Long id);

    /**
     * Delete the "id" extBadgeDesignation.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get the extBadgeDesignation by badge.
     *
     * @param badge_id the id of the entity.
     * @return the entity.
     */
    Mono<ExtBadgeDesignationDTO> findExtBadgeDesignationByBadge(Long badge_id);
}
