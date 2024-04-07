package com.alphalab.service;

import com.alphalab.service.dto.RelGymFeatureDTO;
import com.alphalab.service.dto.RelUserGymDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.RelGymFeature}.
 */
public interface RelGymFeatureService {
    /**
     * Save a relGymFeature.
     *
     * @param relGymFeatureDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RelGymFeatureDTO> save(RelGymFeatureDTO relGymFeatureDTO);

    /**
     * Updates a relGymFeature.
     *
     * @param relGymFeatureDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<RelGymFeatureDTO> update(RelGymFeatureDTO relGymFeatureDTO);

    /**
     * Partially updates a relGymFeature.
     *
     * @param relGymFeatureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<RelGymFeatureDTO> partialUpdate(RelGymFeatureDTO relGymFeatureDTO);

    /**
     * Get all the relGymFeatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RelGymFeatureDTO> findAll(Pageable pageable);

    /**
     * Returns the number of relGymFeatures available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" relGymFeature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<RelGymFeatureDTO> findOne(Long id);

    /**
     * Delete the "id" relGymFeature.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get the relGymFeature where feature is badge_designation by gym.
     *
     * @param gym_id the id of the entity.
     * @return the entity.
     */
    Mono<RelGymFeatureDTO> findRelOfGymWhereFeatureIsBadgeDesignation(Long gym_id);

    /**
     * get Users by gym.
     *
     * @param gym_id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Flux<RelGymFeatureDTO> findFeaturesByGym(Pageable pageable, Long gym_id);

    /**
     * count Users by gym.
     *
     * @param gym_id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Long> countFeaturesByGym(Long gym_id);

    /**
     * findRelByFeatureAndGym.
     *
     * @param gym_id the id of the entity.
     * @param feature_id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<RelGymFeatureDTO> findRelByFeatureAndGym(Long gym_id, Long feature_id);

}
