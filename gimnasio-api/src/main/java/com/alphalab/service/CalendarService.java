package com.alphalab.service;

import com.alphalab.service.dto.CalendarDTO;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.alphalab.domain.Calendar}.
 */
public interface CalendarService {
    /**
     * Save a calendar.
     *
     * @param calendarDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CalendarDTO> save(CalendarDTO calendarDTO);

    /**
     * Updates a calendar.
     *
     * @param calendarDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CalendarDTO> update(CalendarDTO calendarDTO);

    /**
     * Partially updates a calendar.
     *
     * @param calendarDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CalendarDTO> partialUpdate(CalendarDTO calendarDTO);

    /**
     * Get all the calendars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CalendarDTO> findAll(Pageable pageable);

    /**
     * Returns the number of calendars available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" calendar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CalendarDTO> findOne(Long id);

    /**
     * Delete the "id" calendar.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);

    /**
     * Get the calendar by gym.
     *
     * @param gym_id the id of the entity.
     * @return the entity.
     */
    Mono<CalendarDTO> findCalendarByGym(Long gym_id);

    /**
     * Get the calendar of current badge.
     *
     * @return the entity.
     */
    Mono<CalendarDTO> findOneOfCurrentBadge(Principal principal) throws Exception;
}
