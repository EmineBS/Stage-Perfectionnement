package com.alphalab.service;

import com.alphalab.service.dto.ProgressDTO;
import com.alphalab.service.dto.SaveProgressDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.Progress}.
 */
public interface ProgressService {
    /**
     * Save a progress.
     *
     * @param progressDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<ProgressDTO> save(SaveProgressDTO progressDTO);

    /**
     * Updates a progress.
     *
     * @param progressDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ProgressDTO> update(ProgressDTO progressDTO);

    /**
     * Partially updates a progress.
     *
     * @param progressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ProgressDTO> partialUpdate(ProgressDTO progressDTO);

    /**
     * Get all the progresss.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<ProgressDTO> findAll(Pageable pageable);

    /**
     * Returns the number of progresss available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" progress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ProgressDTO> findOne(Long id);

    /**
     * Delete the "id" progress.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
