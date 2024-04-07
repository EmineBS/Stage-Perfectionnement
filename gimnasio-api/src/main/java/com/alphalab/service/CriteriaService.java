package com.alphalab.service;

import com.alphalab.service.dto.CriteriaDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.Criteria}.
 */
public interface CriteriaService {
    /**
     * Save a criteria.
     *
     * @param criteriaDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CriteriaDTO> save(CriteriaDTO criteriaDTO);

    /**
     * Updates a criteria.
     *
     * @param criteriaDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CriteriaDTO> update(CriteriaDTO criteriaDTO);

    /**
     * Partially updates a criteria.
     *
     * @param criteriaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CriteriaDTO> partialUpdate(CriteriaDTO criteriaDTO);

    /**
     * Get all the criterias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CriteriaDTO> findAll(Pageable pageable);

    /**
     * Returns the number of criterias available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" criteria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CriteriaDTO> findOne(Long id);

    /**
     * Delete the "id" criteria.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get all the criterias by gym.
     *
     * @param gym_id the id of the entity.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CriteriaDTO> findAllByGymId(Pageable pageable, Long gym_id);

    /**
     *
     * @param gym_id the id of the entity.
     * Returns the number of criterias available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAllByGymId(Long gym_id);

    /**
     * Get all the criterias by gym.
     *
     * @param gym_id the id of the entity.
     * @return the list of entities.
     */
    Flux<CriteriaDTO> findAllOfGym(Long gym_id);
}
