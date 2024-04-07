package com.alphalab.service;

import com.alphalab.service.dto.RelBadgePackDTO;
import com.alphalab.service.dto.RelBadgePackFullDTO;
import com.alphalab.service.dto.RelBadgePackRSDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.RelBadgePack}.
 */
public interface RelBadgePackService {
    /**
     * Save a relBadgePack.
     *
     * @param relBadgePackDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RelBadgePackDTO> save(RelBadgePackDTO relBadgePackDTO);

    /**
     * Updates a relBadgePack.
     *
     * @param relBadgePackDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<RelBadgePackDTO> update(RelBadgePackDTO relBadgePackDTO);

    /**
     * Partially updates a relBadgePack.
     *
     * @param relBadgePackDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<RelBadgePackDTO> partialUpdate(RelBadgePackDTO relBadgePackDTO);

    /**
     * Get all the relBadgePacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RelBadgePackDTO> findAll(Pageable pageable);

    /**
     * Returns the number of relBadgePacks available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" relBadgePack.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<RelBadgePackDTO> findOne(Long id);

    /**
     * Delete the "id" relBadgePack.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get all the packs and relBadgePack by badge.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RelBadgePackDTO> findAllPacksByBadge(Pageable pageable, Long badge_id);

    Flux<RelBadgePackRSDTO> findAllPacksByBadgeRS(Pageable pageable, Long badge_id);

    /**
     * Assign pack to a specific badge
     *
     * @return the list of entities.
     */
    Mono<RelBadgePackDTO> assignPackToBadge(RelBadgePackDTO relBadgePackDTO);

    /**
     * Get all the relBadgePack by badge.
     *
     * @param gym_id the pagination information.
     * @return the list of entities.
     */
    Flux<RelBadgePackDTO> findRelBadgePackByGym(Long gym_id, Pageable pageable);

    Flux<RelBadgePackFullDTO> findAllBadgesByGym(Long gym_id, Pageable pageable);

    Mono<Long> countAllBadgesByGym(Long gym_id);

    /**
     * Get the relBadgePack by badge.
     *
     * @param badge_id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<RelBadgePackDTO> findRelBadgePackByBadge(Long badge_id);

    Mono<RelBadgePackDTO> activateBadgePack(RelBadgePackDTO relBadgePackDTO);
}
