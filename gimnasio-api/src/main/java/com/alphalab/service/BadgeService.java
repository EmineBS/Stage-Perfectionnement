package com.alphalab.service;

import com.alphalab.service.dto.BadgeDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.Badge}.
 */
public interface BadgeService {
    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<BadgeDTO> save(BadgeDTO badgeDTO);

    /**
     * Updates a badge.
     *
     * @param badgeDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<BadgeDTO> update(BadgeDTO badgeDTO);

    /**
     * Partially updates a badge.
     *
     * @param badgeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<BadgeDTO> partialUpdate(BadgeDTO badgeDTO);

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<BadgeDTO> findAll(Pageable pageable);

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
    Mono<BadgeDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get all the badges by gym.
     *
     * @param gym_id the id of the entity.
     */
    Flux<BadgeDTO> findAllBadgesOfGym(Pageable pageable, Long gym_id);
    /**
     * Count all the badges by gym.
     *
     * @param gym_id the id of the entity.
     */
    Mono<Long> countFindAllBadgesOfGym(Long gym_id);

    /**
     * Get the "id" badge.
     *
     * @return the entity.
     */
    Mono<BadgeDTO> findOneBySession(Principal principal) throws Exception;
}
