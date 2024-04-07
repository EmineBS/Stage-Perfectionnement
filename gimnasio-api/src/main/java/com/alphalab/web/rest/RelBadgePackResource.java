package com.alphalab.web.rest;

import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.repository.RelBadgePackRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.RelBadgePackService;
import com.alphalab.service.dto.RelBadgePackDTO;
import com.alphalab.service.dto.RelBadgePackFullDTO;
import com.alphalab.service.dto.RelBadgePackRSDTO;
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
 * REST controller for managing {@link com.alphalab.domain.RelBadgePack}.
 */
@RestController
@RequestMapping("/api")
public class RelBadgePackResource {

    private final Logger log = LoggerFactory.getLogger(RelBadgePackResource.class);

    private static final String ENTITY_NAME = "relBadgePack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelBadgePackService relBadgePackService;

    private final RelBadgePackRepository relBadgePackRepository;
    private final BadgeService badgeService;

    public RelBadgePackResource(
        RelBadgePackService relBadgePackService,
        RelBadgePackRepository relBadgePackRepository,
        BadgeService badgeService
    ) {
        this.relBadgePackService = relBadgePackService;
        this.relBadgePackRepository = relBadgePackRepository;
        this.badgeService = badgeService;
    }

    /**
     * {@code POST  /rel_badge_pack} : Create a new relBadgePack.
     *
     * @param relBadgePackDTO the relBadgePackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relBadgePackDTO, or with status {@code 400 (Bad Request)} if the relBadgePack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rel_badge_pack")
    public Mono<ResponseEntity<RelBadgePackDTO>> createRelBadgePack(@RequestBody RelBadgePackDTO relBadgePackDTO)
        throws URISyntaxException {
        log.debug("REST request to save RelBadgePack : {}", relBadgePackDTO);
        if (relBadgePackDTO.getId() != null) {
            throw new BadRequestAlertException("A new relBadgePack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return relBadgePackService
            .assignPackToBadge(relBadgePackDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/rel_badge_pack/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /rel_badge_pack/:id} : Updates an existing relBadgePack.
     *
     * @param id the id of the relBadgePackDTO to save.
     * @param relBadgePackDTO the relBadgePackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relBadgePackDTO,
     * or with status {@code 400 (Bad Request)} if the relBadgePackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relBadgePackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rel_badge_pack/{id}")
    public Mono<ResponseEntity<RelBadgePackDTO>> updateRelBadgePack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelBadgePackDTO relBadgePackDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelBadgePack : {}, {}", id, relBadgePackDTO);
        if (relBadgePackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relBadgePackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relBadgePackRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return relBadgePackService
                    .update(relBadgePackDTO)
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
     * {@code PATCH  /rel_badge_pack/:id} : Partial updates given fields of an existing relBadgePack, field will ignore if it is null
     *
     * @param id the id of the relBadgePackDTO to save.
     * @param relBadgePackDTO the relBadgePackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relBadgePackDTO,
     * or with status {@code 400 (Bad Request)} if the relBadgePackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relBadgePackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relBadgePackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rel_badge_pack/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RelBadgePackDTO>> partialUpdateRelBadgePack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelBadgePackDTO relBadgePackDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelBadgePack partially : {}, {}", id, relBadgePackDTO);
        if (relBadgePackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relBadgePackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relBadgePackRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }
                Mono<RelBadgePackDTO> result = relBadgePackService.partialUpdate(relBadgePackDTO);
                if (relBadgePackDTO.getStatus() == BadgePackStatus.ACTIVE) {
                    return relBadgePackService
                        .activateBadgePack(relBadgePackDTO)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                        .map(res ->
                            ResponseEntity
                                .ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                                .body(res)
                        );
                } else return relBadgePackService
                    .partialUpdate(relBadgePackDTO)
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
     * {@code GET  /rel_badge_pack} : get all the rel_badge_pack.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rel_badge_pack in body.
     */
    @GetMapping("/rel_badge_pack")
    public Mono<ResponseEntity<List<RelBadgePackDTO>>> getAllRelBadgePacks(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RelBadgePacks");
        return relBadgePackService
            .countAll()
            .zipWith(relBadgePackService.findAll(pageable).collectList())
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
     * {@code GET  /rel_badge_pack/:id} : get the "id" relBadgePack.
     *
     * @param id the id of the relBadgePackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relBadgePackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rel_badge_pack/{id}")
    public Mono<ResponseEntity<RelBadgePackDTO>> getRelBadgePack(@PathVariable Long id) {
        log.debug("REST request to get RelBadgePack : {}", id);
        Mono<RelBadgePackDTO> relBadgePackDTO = relBadgePackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relBadgePackDTO);
    }

    /**
     * {@code DELETE  /rel_badge_pack/:id} : delete the "id" relBadgePack.
     *
     * @param id the id of the relBadgePackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rel_badge_pack/{id}")
    public Mono<ResponseEntity<Void>> deleteRelBadgePack(@PathVariable Long id) {
        log.debug("REST request to delete RelBadgePack : {}", id);
        return relBadgePackService
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
     * {@code GET  /rel_badge_pack/badge/:badge_id} : get all the rel_badge_pack by badge.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crimes in body.
     */
    @GetMapping("/rel_badge_pack/badge/{badge_id}")
    public Mono<ResponseEntity<List<RelBadgePackDTO>>> getAllPackesByBadge(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long badge_id
    ) throws Exception {
        log.debug("REST request to get a page of packs by badge id 1");
        return relBadgePackRepository
            .countAllPacksByBadge(badge_id)
            .zipWith(relBadgePackService.findAllPacksByBadge(pageable, badge_id).collectList())
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
     * {@code GET  /rel_badge_pack/gym/:gym_id} : get all the badges and packs of gym where rel is active.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rel_badge_pack in body.
     */
    @GetMapping("/rel_badge_pack/gym/{gym_id}")
    public Mono<ResponseEntity<List<RelBadgePackDTO>>> getAllRelBadgePacksByGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of RelBadgePacks");
        return relBadgePackService
            .countAll()
            .zipWith(relBadgePackService.findRelBadgePackByGym(gym_id, pageable).collectList())
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
     * {@code GET  /allbadges/gym/:gym_id} : get all the badges and packs of gym where rel is active.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rel_badge_pack in body.
     */
    @GetMapping("/badges-full/gym/{gym_id}")
    public Mono<ResponseEntity<List<RelBadgePackFullDTO>>> getAllBadgePacksByGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of RelBadgePacks");
        return relBadgePackService
            .countAllBadgesByGym(gym_id)
            .zipWith(relBadgePackService.findAllBadgesByGym(gym_id, pageable).collectList())
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
     * {@code GET  /rel_badge_pack/badge-rel/:badge_id} : get the "id" relBadgePack.
     *
     * @param badge_id the id of the relBadgePackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relBadgePackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rel_badge_pack/badge-rel/{badge_id}")
    public Mono<ResponseEntity<RelBadgePackDTO>> getRelBadgePackByBadge(@PathVariable Long badge_id) {
        log.debug("REST request to get RelBadgePack : {}", badge_id);
        Mono<RelBadgePackDTO> relBadgePackDTO = relBadgePackService.findRelBadgePackByBadge(badge_id);
        return ResponseUtil.wrapOrNotFound(relBadgePackDTO);
    }

    /**
     * {@code GET  /rel_badge_pack/account} : get all the rel_badge_pack by badge.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crimes in body.
     */
    @GetMapping("/rel_badge_pack/account")
    public Mono<ResponseEntity<List<RelBadgePackRSDTO>>> getAllPackesBySession(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        Principal principal
    ) throws Exception {
        log.debug("REST request to get a page of packs by badge id 1");
        return badgeService
            .findOneBySession(principal)
            .flatMap(badge -> {
                return relBadgePackRepository
                    .countAllPacksByBadge(badge.getId())
                    .zipWith(relBadgePackService.findAllPacksByBadgeRS(pageable, badge.getId()).collectList())
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
            });
    }
}
