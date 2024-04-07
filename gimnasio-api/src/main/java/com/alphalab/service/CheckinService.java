package com.alphalab.service;

import com.alphalab.domain.CheckIn;
import com.alphalab.domain.enumeration.CheckinStatus;
import com.alphalab.service.dto.CheckinDTO;
import com.alphalab.service.dto.CheckinResultDTO;
import java.security.Principal;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link CheckIn}.
 */
public interface CheckinService {
    /**
     * Save a Checkin.
     *
     * @param CheckinDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CheckinDTO> save(CheckinDTO CheckinDTO, Principal principal) throws Exception;

    /**
     * Updates a Checkin.
     *
     * @param CheckinDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CheckinDTO> update(CheckinDTO CheckinDTO, Principal principal) throws Exception;

    /**
     * Partially updates a Checkin.
     *
     * @param CheckinDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CheckinDTO> partialUpdate(CheckinDTO CheckinDTO);

    /**
     * Get all the Checkins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CheckinDTO> findAll(Pageable pageable);

    /**
     * Returns the number of Checkins available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" Checkin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CheckinDTO> findOne(Long id);

    /**
     * Delete the "id" Checkin.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
    /**
     * Count badge checkin.
     *
     * @param badge_uid the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Long> countAllCheckinOfBadge(String badge_uid, Long pack_id);

    Flux<CheckinDTO> findAllByUser(Pageable pageable, Principal principal) throws Exception;

    Mono<Long> countAllCheckinByUser(Principal principal) throws Exception;

    Mono<Long> countAllCheckinOfRelBadgePackId(Long relBadgePackId);

    /**
     * Checkin with badge uid user.
     *
     * @param badge_uid the id of the entity.
     * @return a Mono to signal the deletion
     */

    Mono<CheckinResultDTO> checkinByBadge(String badge_uid, Principal principal) throws Exception;

    Flux<CheckinDTO> findAllByBadgeUID(Pageable pageable, String badge_uid);

    Mono<Long> countAllByBadgeUID(String badge_uid);

    Flux<CheckinDTO> findAllByGym(Pageable pageable, Long gym_id);

    Mono<Long> countAllByGym(Long gym_id);

    Mono<CheckinResultDTO> confirmCheckin(CheckinDTO CheckinDTO, Principal principal) throws Exception;
}
