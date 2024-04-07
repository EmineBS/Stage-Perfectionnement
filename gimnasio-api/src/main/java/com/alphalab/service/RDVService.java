package com.alphalab.service;

import com.alphalab.domain.RDV;
import com.alphalab.service.dto.RDVDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.RDV}.
 */
public interface RDVService {
    /**
     * Save a rdv.
     *
     * @param rdvDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RDVDTO> save(RDVDTO rdvDTO);

    /**
     * Updates a rdv.
     *
     * @param rdvDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<RDVDTO> update(RDVDTO rdvDTO);

    /**
     * Partially updates a rdv.
     *
     * @param rdvDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<RDVDTO> partialUpdate(RDVDTO rdvDTO);

    /**
     * Get all the rdvs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RDVDTO> findAll(Pageable pageable);

    /**
     * Returns the number of rdvs available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" rdv.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<RDVDTO> findOne(Long id);

    /**
     * Delete the "id" rdv.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get all the rdvs by badge.
     *
     * @param pageable the pagination information.
     * @param badge_id the pagination information.
     * @return the list of entities.
     */
    Flux<RDVDTO> findAllByBadge(Pageable pageable, Long badge_id);

    /**
     * @param badge_id the pagination information.
     *
     * Returns the number of rdvs available by badge.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAllByBadge(Long badge_id);

    /**
     * @param badge_id the pagination information.
     *
     * Returns the number of rdvs available by badge and status confirmed.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAllByBadgeAndStatusIsConfirmed(Long badge_id);

    /**
     * @param badge_id the pagination information.
     *
     * Returns get Closest RDV Booked by badge.
     * @return the number of entities in the database.
     *
     */
    Mono<RDV> getRDVBookedToday(Long badge_id);

    /**
     * Get all the rdvs by gym.
     *
     * @param pageable the pagination information.
     * @param gym_id the pagination information.
     * @return the list of entities.
     */
    Flux<RDVDTO> findAllByGym(Pageable pageable, Long gym_id);

    /**
     * @param gym_id the pagination information.
     *
     * Returns the number of rdvs available by gym.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAllByGym(Long gym_id);

    /**
     * Get all the rdvs by gym.
     *
     * @return the list of entities.
     */
    Flux<RDVDTO> findAllOfCurrentBadgeAccount(Principal principal) throws Exception;
    Flux<RDVDTO> findAllExceptOfCurrentBadgeAccount(Principal principal) throws Exception;

    /**
     * Get all the rdvs of current calendar.
     *
     * @return the list of entities.
     */
    Flux<RDVDTO> findAllOfCurrentCalendarBadge(Principal principal) throws Exception;

    /**
     * Create rdv.
     *
     * @param rdvDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RDVDTO> createRDV(RDVDTO rdvDTO, Principal principal) throws Exception;
}
