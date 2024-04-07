package com.alphalab.web.rest;

import com.alphalab.domain.CheckIn;
import com.alphalab.repository.CheckinRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.CheckinService;
import com.alphalab.service.dto.CheckinDTO;
import com.alphalab.service.dto.CheckinResultDTO;
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
 * REST controller for managing {@link CheckIn}.
 */
@RestController
@RequestMapping("/api")
public class CheckinResource {

    private final Logger log = LoggerFactory.getLogger(CheckinResource.class);

    private static final String ENTITY_NAME = "Checkin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckinService checkinService;

    private final CheckinRepository checkinRepository;

    private final BadgeService badgeService;

    public CheckinResource(CheckinService checkinService, CheckinRepository checkinRepository, BadgeService badgeService) {
        this.checkinService = checkinService;
        this.checkinRepository = checkinRepository;
        this.badgeService = badgeService;
    }

    /**
     * {@code POST  /checkins} : Create a new Checkin.
     *
     * @param CheckinDTO the CheckinDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new CheckinDTO, or with status {@code 400 (Bad Request)} if the Checkin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkins")
    public Mono<ResponseEntity<CheckinDTO>> createCheckin(@RequestBody CheckinDTO CheckinDTO, Principal principal) throws Exception {
        log.debug("REST request to save Checkin : {}", CheckinDTO);
        if (CheckinDTO.getId() != null) {
            throw new BadRequestAlertException("A new Checkin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return checkinService
            .save(CheckinDTO, principal)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/checkin/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /checkins/:id} : Updates an existing Checkin.
     *
     * @param id the id of the CheckinDTO to save.
     * @param CheckinDTO the CheckinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated CheckinDTO,
     * or with status {@code 400 (Bad Request)} if the CheckinDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the CheckinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkins/{id}")
    public Mono<ResponseEntity<CheckinDTO>> updateCheckin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckinDTO CheckinDTO,
        Principal principal
    ) throws URISyntaxException {
        log.debug("REST request to update Checkin : {}, {}", id, CheckinDTO);
        if (CheckinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, CheckinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return checkinRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                try {
                    return checkinService
                        .update(CheckinDTO, principal)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                        .map(result ->
                            ResponseEntity
                                .ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                                .body(result)
                        );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PATCH  /checkins/:id} : Partial updates given fields of an existing Checkin, field will ignore if it is null
     *
     * @param id the id of the CheckinDTO to save.
     * @param CheckinDTO the CheckinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated CheckinDTO,
     * or with status {@code 400 (Bad Request)} if the CheckinDTO is not valid,
     * or with status {@code 404 (Not Found)} if the CheckinDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the CheckinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/checkins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<CheckinDTO>> partialUpdateCheckin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckinDTO CheckinDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Checkin partially : {}, {}", id, CheckinDTO);
        if (CheckinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, CheckinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return checkinRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<CheckinDTO> result = checkinService.partialUpdate(CheckinDTO);

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
     * {@code GET  /checkins} : get all the checkin.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkin in body.
     * @throws Exception
     */
    @GetMapping("/checkins")
    public Mono<ResponseEntity<List<CheckinDTO>>> getAllCheckins(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        Principal principal
    ) throws Exception {
        log.debug("REST request to get a page of Checkins");
        return checkinService
            .countAllCheckinByUser(principal)
            .zipWith(checkinService.findAllByUser(pageable, principal).collectList())
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
     * {@code GET  /checkins/:id} : get the "id" Checkin.
     *
     * @param id the id of the CheckinDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CheckinDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkins/{id}")
    public Mono<ResponseEntity<CheckinDTO>> getCheckin(@PathVariable Long id) {
        log.debug("REST request to get Checkin : {}", id);
        Mono<CheckinDTO> CheckinDTO = checkinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(CheckinDTO);
    }

    /**
     * {@code DELETE  /checkins/:id} : delete the "id" Checkin.
     *
     * @param id the id of the CheckinDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkins/{id}")
    public Mono<ResponseEntity<Void>> deleteCheckin(@PathVariable Long id) {
        log.debug("REST request to delete Checkin : {}", id);
        return checkinService
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
     * {@code GET  /checkin/:uid} : checkin with badge.
     *
     * @param uid the id of the CheckinDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CheckinDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkin/{uid}")
    public Mono<ResponseEntity<CheckinResultDTO>> getCheckin(@PathVariable String uid, Principal principal) throws Exception {
        log.debug("REST request to get Checkin using badge : {}", uid);
        Mono<CheckinResultDTO> CheckinDTO = checkinService.checkinByBadge(uid, principal);
        return ResponseUtil.wrapOrNotFound(CheckinDTO);
    }

    /**
     * {@code GET  /checkins/badge/:badge_uid} : get all the checkin by badge.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param badge_uid a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkin in body.
     * @throws Exception
     */
    @GetMapping("/checkins/badge/{badge_uid}")
    public Mono<ResponseEntity<List<CheckinDTO>>> getAllCheckinsByBadge(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable String badge_uid
    ) throws Exception {
        log.debug("REST request to get a page of Checkins");
        return checkinService
            .countAllByBadgeUID(badge_uid)
            .zipWith(checkinService.findAllByBadgeUID(pageable, badge_uid).collectList())
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
     * {@code GET  /checkins/badge/account} : get all the checkin by session.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param principal a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkin in body.
     * @throws Exception
     */
    @GetMapping("/checkins/badge/account")
    public Mono<ResponseEntity<List<CheckinDTO>>> getAllCheckinsByBadge(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        Principal principal
    ) throws Exception {
        log.debug("REST request to get a page of Checkins by badge session");
        return badgeService.findOneBySession(principal)
            .flatMap(badgeDTO -> checkinService
                .countAllByBadgeUID(badgeDTO.getUid())
                .zipWith(checkinService.findAllByBadgeUID(pageable, badgeDTO.getUid()).collectList())
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
                ));
    }

    /**
     * {@code GET  /checkins/gym/:gym_id} : get all the checkin by gym.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param gym_id a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkin in body.
     * @throws Exception
     */
    @GetMapping("/checkins/gym/{gym_id}")
    public Mono<ResponseEntity<List<CheckinDTO>>> getAllCheckinsOfGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) throws Exception {
        log.debug("REST request to get a page of Checkins");
        return checkinService
            .countAllByGym(gym_id)
            .zipWith(checkinService.findAllByGym(pageable, gym_id).collectList())
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
     * {@code POST  /checkin/confirm} : Confirm a Checkin.
     *
     * @param CheckinDTO the CheckinDTO to confirm.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new CheckinDTO, or with status {@code 400 (Bad Request)} if the Checkin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkin/confirm")
    public Mono<ResponseEntity<CheckinResultDTO>> confirmCheckin(@RequestBody CheckinDTO CheckinDTO, Principal principal) throws Exception {
        log.debug("REST request to confirm Checkin : {}", CheckinDTO);
        Mono<CheckinResultDTO> checkinDTO = checkinService.confirmCheckin(CheckinDTO, principal);
        return ResponseUtil.wrapOrNotFound(checkinDTO);
    }
}
