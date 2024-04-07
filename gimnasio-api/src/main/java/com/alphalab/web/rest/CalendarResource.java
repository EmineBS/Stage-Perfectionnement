package com.alphalab.web.rest;

import com.alphalab.repository.CalendarRepository;
import com.alphalab.service.CalendarService;
import com.alphalab.service.dto.CalendarDTO;
import com.alphalab.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.alphalab.domain.Calendar}.
 */
@RestController
@RequestMapping("/api")
public class CalendarResource {

    private final Logger log = LoggerFactory.getLogger(CalendarResource.class);

    private static final String ENTITY_NAME = "calendar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalendarService calendarService;

    private final CalendarRepository calendarRepository;

    public CalendarResource(CalendarService calendarService, CalendarRepository calendarRepository) {
        this.calendarService = calendarService;
        this.calendarRepository = calendarRepository;
    }

    /**
     * {@code POST  /calendars} : Create a new calendar.
     *
     * @param calendarDTO the calendarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendarDTO, or with status {@code 400 (Bad Request)} if the calendar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calendars")
    public Mono<ResponseEntity<CalendarDTO>> createCalendar(@RequestBody CalendarDTO calendarDTO) throws URISyntaxException {
        log.debug("REST request to save Calendar : {}", calendarDTO);
        if (calendarDTO.getId() != null) {
            throw new BadRequestAlertException("A new calendar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return calendarService
            .save(calendarDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/calendars/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /calendars/:id} : Updates an existing calendar.
     *
     * @param id the id of the calendarDTO to save.
     * @param calendarDTO the calendarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarDTO,
     * or with status {@code 400 (Bad Request)} if the calendarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calendars/{id}")
    public Mono<ResponseEntity<CalendarDTO>> updateCalendar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CalendarDTO calendarDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Calendar : {}, {}", id, calendarDTO);
        if (calendarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return calendarRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return calendarService
                    .update(calendarDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /calendars/:id} : Partial updates given fields of an existing calendar, field will ignore if it is null
     *
     * @param id the id of the calendarDTO to save.
     * @param calendarDTO the calendarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarDTO,
     * or with status {@code 400 (Bad Request)} if the calendarDTO is not valid,
     * or with status {@code 404 (Not Found)} if the calendarDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the calendarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/calendars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<CalendarDTO>> partialUpdateCalendar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CalendarDTO calendarDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Calendar partially : {}, {}", id, calendarDTO);
        if (calendarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return calendarRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<CalendarDTO> result = calendarService.partialUpdate(calendarDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /calendars} : get all the calendars.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calendars in body.
     */
    @GetMapping("/calendars")
    public Mono<ResponseEntity<List<CalendarDTO>>> getAllCalendars(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Calendars");
        return calendarService
            .countAll()
            .zipWith(calendarService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /calendars/:id} : get the "id" calendar.
     *
     * @param id the id of the calendarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendars/{id}")
    public Mono<ResponseEntity<CalendarDTO>> getCalendar(@PathVariable Long id) {
        log.debug("REST request to get Calendar : {}", id);
        Mono<CalendarDTO> calendarDTO = calendarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarDTO);
    }

    /**
     * {@code DELETE  /calendars/:id} : delete the "id" calendar.
     *
     * @param id the id of the calendarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calendars/{id}")
    public Mono<ResponseEntity<Void>> deleteCalendar(@PathVariable Long id) {
        log.debug("REST request to delete Calendar : {}", id);
        return calendarService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }

    /**
     * {@code GET  /calendars/gym/:gym_id} : get the calendar by gym.
     *
     * @param gym_id the id of the calendarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendars/gym/{gym_id}")
    public Mono<ResponseEntity<CalendarDTO>> getCalendarByGym(@PathVariable Long gym_id) {
        log.debug("REST request to get Calendar by gym : {}", gym_id);
        Mono<CalendarDTO> calendarDTO = calendarService.findCalendarByGym(gym_id);
        return ResponseUtil.wrapOrNotFound(calendarDTO);
    }

    /**
     * {@code GET  /calendars/badge-account} : get the "id" calendar.
     *
     * @param principal the session.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendars/badge-account")
    public Mono<ResponseEntity<CalendarDTO>> getCalendarByCurrentBadge(Principal principal) throws Exception {
        log.debug("REST request to get Calendar of current badge: {}", principal);
        Mono<CalendarDTO> calendarDTO = calendarService.findOneOfCurrentBadge(principal);
        return ResponseUtil.wrapOrNotFound(calendarDTO);
    }
}
