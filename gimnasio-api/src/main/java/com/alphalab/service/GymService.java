package com.alphalab.service;

import com.alphalab.service.dto.GymDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.Gym}.
 */
public interface GymService {
    /**
     * Save a gym.
     *
     * @param gymDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<GymDTO> save(GymDTO gymDTO);

    /**
     * Updates a gym.
     *
     * @param gymDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<GymDTO> update(GymDTO gymDTO);

    /**
     * Partially updates a gym.
     *
     * @param gymDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<GymDTO> partialUpdate(GymDTO gymDTO);

    /**
     * Get all the gyms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<GymDTO> findAll(Pageable pageable);

    /**
     * Returns the number of gyms available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" gym.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<GymDTO> findOne(Long id);

    /**
     * Delete the "id" gym.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Create a gym.
     *
     * @param gymDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<GymDTO> createGym(GymDTO gymDTO);

    /**
     * Get all gym by user.
     *
     * @return the persisted entity.
     */
    Flux<GymDTO> findAllGymsByUser(Pageable pageable, Principal principal) throws Exception;

    /**
     * Count all gym by user.
     *
     * @return the persisted entity.
     */
    Mono<Long> countAllGymsByUser(Principal principal) throws Exception;

    /**
     * find gym by badge uid.
     *
     * @param uid the id of the entity.
     * @return the entity.
     */
    Mono<GymDTO> findGymByBadgeUID(String uid);

    /**
     * Get the gym of current badge account.
     *
     * @param principal the id of the entity.
     * @return the entity.
     */
    Mono<GymDTO> findGymOfCurrentBadgeAccount(Principal principal) throws Exception;
}
