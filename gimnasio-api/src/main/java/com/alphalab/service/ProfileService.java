package com.alphalab.service;

import com.alphalab.service.dto.ProfileDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.Profile}.
 */
public interface ProfileService {
    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<ProfileDTO> save(ProfileDTO profileDTO);

    /**
     * Updates a profile.
     *
     * @param profileDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ProfileDTO> update(ProfileDTO profileDTO);

    /**
     * Partially updates a profile.
     *
     * @param profileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ProfileDTO> partialUpdate(ProfileDTO profileDTO);

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<ProfileDTO> findAll(Pageable pageable);

    /**
     * Returns the number of profiles available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ProfileDTO> findOne(Long id);

    /**
     * Delete the "id" profile.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get the current profile.
     *
     * @return the entity.
     */
    Mono<ProfileDTO> findCurrentProfile(Principal principal) throws Exception;

    /**
     * Get the profile by badge.
     *
     * @return the entity.
     */
    Mono<ProfileDTO> findOneByBadge(Long badge_id);
}
