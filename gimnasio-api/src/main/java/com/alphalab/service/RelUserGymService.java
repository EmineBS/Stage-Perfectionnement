package com.alphalab.service;

import com.alphalab.service.dto.RelUserGymDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.RelUserGym}.
 */
public interface RelUserGymService {
    /**
     * Save a relUserGym.
     *
     * @param relUserGymDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RelUserGymDTO> save(RelUserGymDTO relUserGymDTO);

    /**
     * Updates a relUserGym.
     *
     * @param relUserGymDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<RelUserGymDTO> update(RelUserGymDTO relUserGymDTO);

    /**
     * Partially updates a relUserGym.
     *
     * @param relUserGymDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<RelUserGymDTO> partialUpdate(RelUserGymDTO relUserGymDTO);

    /**
     * Get all the relUserGyms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RelUserGymDTO> findAll(Pageable pageable);

    /**
     * Returns the number of relUserGyms available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" relUserGym.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<RelUserGymDTO> findOne(Long id);

    /**
     * Delete the "id" relUserGym.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * check realation between user and gym.
     *
     * @param badge_uid the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<RelUserGymDTO> checkRelBetweenUserAndGym(String badge_uid, Principal principal) throws Exception;

    /**
     * get Users by gym.
     *
     * @param gym_id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Flux<RelUserGymDTO> findUsersByGym(Pageable pageable, Long gym_id);

    /**
     * count Users by gym.
     *
     * @param gym_id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Long> countUsersByGym(Long gym_id);
}
