package com.alphalab.web.rest;

import com.alphalab.repository.BadgeRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.GymService;
import com.alphalab.service.dto.BadgeDTO;
import com.alphalab.service.dto.GymDTO;
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
 * REST controller for managing {@link com.alphalab.domain.Badge}.
 */
@RestController
@RequestMapping("/api")
public class BadgeResource {

    private final Logger log = LoggerFactory.getLogger(BadgeResource.class);

    private static final String ENTITY_NAME = "badge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeService badgeService;

    private final GymService gymService;

    private final BadgeRepository badgeRepository;

    public BadgeResource(BadgeService badgeService, BadgeRepository badgeRepository, GymService gymService) {
        this.badgeService = badgeService;
        this.badgeRepository = badgeRepository;
        this.gymService = gymService;
    }

    /**
     * {@code POST  /badges} : Create a new badge.
     *
     * @param badgeDTO the badgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeDTO, or with status {@code 400 (Bad Request)} if the badge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badges")
    public Mono<ResponseEntity<BadgeDTO>> createBadge(@RequestBody BadgeDTO badgeDTO) throws URISyntaxException {
        log.debug("REST request to save Badge : {}", badgeDTO);
        if (badgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new badge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return badgeService
            .save(badgeDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/badges/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /badges/:id} : Updates an existing badge.
     *
     * @param id the id of the badgeDTO to save.
     * @param badgeDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/badges/{id}")
    public Mono<ResponseEntity<BadgeDTO>> updateBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BadgeDTO badgeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Badge : {}, {}", id, badgeDTO);
        if (badgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return badgeRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return badgeService
                    .update(badgeDTO)
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
     * {@code PATCH  /badges/:id} : Partial updates given fields of an existing badge, field will ignore if it is null
     *
     * @param id the id of the badgeDTO to save.
     * @param badgeDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the badgeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/badges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<BadgeDTO>> partialUpdateBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BadgeDTO badgeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Badge partially : {}, {}", id, badgeDTO);
        if (badgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return badgeRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<BadgeDTO> result = badgeService.partialUpdate(badgeDTO);

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
     * {@code GET  /badges} : get all the badges.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("/badges")
    public Mono<ResponseEntity<List<BadgeDTO>>> getAllBadges(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Badges");
        return badgeService
            .countAll()
            .zipWith(badgeService.findAll(pageable).collectList())
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
     * {@code GET  /badges/:id} : get the "id" badge.
     *
     * @param id the id of the badgeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badges/{id}")
    public Mono<ResponseEntity<BadgeDTO>> getBadge(@PathVariable Long id) {
        log.debug("REST request to get Badge : {}", id);
        Mono<BadgeDTO> badgeDTO = badgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeDTO);
    }

    /**
     * {@code DELETE  /badges/:id} : delete the "id" badge.
     *
     * @param id the id of the badgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badges/{id}")
    public Mono<ResponseEntity<Void>> deleteBadge(@PathVariable Long id) {
        log.debug("REST request to delete Badge : {}", id);
        return badgeService
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
     * {@code GET  /badges/gym/:gym_id} : get all the badges by gym.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("/badges/gym/{gym_id}")
    public Mono<ResponseEntity<List<BadgeDTO>>> getAllBadgesByGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of Badges");
        return badgeService
            .countFindAllBadgesOfGym(gym_id)
            .zipWith(badgeService.findAllBadgesOfGym(pageable, gym_id).collectList())
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
     * {@code GET  /badges/account} : get the badge by session.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badges/account")
    public Mono<ResponseEntity<BadgeDTO>> getBadgeBySession(Principal principal) throws Exception {
        log.debug("REST request to get Badge by session: {}", principal);
        Mono<BadgeDTO> badgeDTO = badgeService.findOneBySession(principal);
        return ResponseUtil.wrapOrNotFound(badgeDTO);
    }
}
