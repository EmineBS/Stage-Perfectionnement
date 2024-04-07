package com.alphalab.web.rest;

import com.alphalab.repository.RDVRepository;
import com.alphalab.service.RDVService;
import com.alphalab.service.dto.RDVDTO;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.alphalab.domain.RDV}.
 */
@RestController
@RequestMapping("/api")
public class RDVResource {

    private final Logger log = LoggerFactory.getLogger(RDVResource.class);

    private static final String ENTITY_NAME = "rdv";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RDVService rdvService;

    private final RDVRepository rdvRepository;

    public RDVResource(RDVService rdvService, RDVRepository rdvRepository) {
        this.rdvService = rdvService;
        this.rdvRepository = rdvRepository;
    }

    /**
     * {@code POST  /rdvs_dep} : Create a new rdv.
     *
     * @param rdvDTO the rdvDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rdvDTO, or with status {@code 400 (Bad Request)} if the rdv has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rdvs_dep")
    public Mono<ResponseEntity<RDVDTO>> createRDV_dep(@RequestBody RDVDTO rdvDTO) throws URISyntaxException {
        log.debug("REST request to save RDV : {}", rdvDTO);
        if (rdvDTO.getId() != null) {
            throw new BadRequestAlertException("A new rdv cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return rdvService
            .save(rdvDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/rdvs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /rdvs/:id} : Updates an existing rdv.
     *
     * @param id the id of the rdvDTO to save.
     * @param rdvDTO the rdvDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rdvDTO,
     * or with status {@code 400 (Bad Request)} if the rdvDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rdvDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rdvs/{id}")
    public Mono<ResponseEntity<RDVDTO>> updateRDV(@PathVariable(value = "id", required = false) final Long id, @RequestBody RDVDTO rdvDTO)
        throws URISyntaxException {
        log.debug("REST request to update RDV : {}, {}", id, rdvDTO);
        if (rdvDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rdvDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return rdvRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return rdvService
                    .update(rdvDTO)
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
     * {@code PATCH  /rdvs/:id} : Partial updates given fields of an existing rdv, field will ignore if it is null
     *
     * @param id the id of the rdvDTO to save.
     * @param rdvDTO the rdvDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rdvDTO,
     * or with status {@code 400 (Bad Request)} if the rdvDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rdvDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rdvDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rdvs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RDVDTO>> partialUpdateRDV(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RDVDTO rdvDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RDV partially : {}, {}", id, rdvDTO);
        if (rdvDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rdvDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return rdvRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RDVDTO> result = rdvService.partialUpdate(rdvDTO);

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
     * {@code GET  /rdvs} : get all the rdvs.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rdvs in body.
     */
    @GetMapping("/rdvs")
    public Mono<ResponseEntity<List<RDVDTO>>> getAllRDVs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RDVs");
        return rdvService
            .countAll()
            .zipWith(rdvService.findAll(pageable).collectList())
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
     * {@code GET  /rdvs/:id} : get the "id" rdv.
     *
     * @param id the id of the rdvDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rdvDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rdvs/{id}")
    public Mono<ResponseEntity<RDVDTO>> getRDV(@PathVariable Long id) {
        log.debug("REST request to get RDV : {}", id);
        Mono<RDVDTO> rdvDTO = rdvService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rdvDTO);
    }

    /**
     * {@code DELETE  /rdvs/:id} : delete the "id" rdv.
     *
     * @param id the id of the rdvDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rdvs/{id}")
    public Mono<ResponseEntity<Void>> deleteRDV(@PathVariable Long id) {
        log.debug("REST request to delete RDV : {}", id);
        return rdvService
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
     * {@code GET  /rdvs/badge/:badge_id} : get all the rdvs by badge.
     *
     * @param pageable the pagination information.
     * @param badge_id the badge id.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rdvs in body.
     */
    @GetMapping("/rdvs/badge/{badge_id}")
    public Mono<ResponseEntity<List<RDVDTO>>> getAllRDVsByBadge(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long badge_id
    ) {
        log.debug("REST request to get a page of RDVs");
        return rdvService
            .countAllByBadge(badge_id)
            .zipWith(rdvService.findAllByBadge(pageable, badge_id).collectList())
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
     * {@code GET  /rdvs/gym/:gym_id} : get all the rdvs by gym.
     *
     * @param pageable the pagination information.
     * @param gym_id the badge id.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rdvs in body.
     */
    @GetMapping("/rdvs/gym/{gym_id}")
    public Mono<ResponseEntity<List<RDVDTO>>> getAllRDVsByGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of RDVs");
        return rdvService
            .countAllByGym(gym_id)
            .zipWith(rdvService.findAllByGym(pageable, gym_id).collectList())
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
     * {@code GET  /rdvs/badge-account} : get the all rdvs of current session.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rdvDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rdvs/badge-account")
    public Flux<RDVDTO> getRDVOfCurrentBadgeAccount(Principal principal) throws Exception {
        log.debug("REST request to get RDV : {}", principal);
        return rdvService.findAllOfCurrentBadgeAccount(principal);
    }

    /**
     * {@code GET  /rdvs/badge-others} : get the all except rdvs of current session.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rdvDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rdvs/badge-others")
    public Flux<RDVDTO> getRDVOfCurrentCalendar(Principal principal) throws Exception {
        log.debug("REST request to get RDV : {}", principal);
        return rdvService.findAllExceptOfCurrentBadgeAccount(principal);
    }

    /**
     * {@code GET  /rdvs/badge-calendar} : get all rdvs with others obfuscated.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rdvDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rdvs/badge-calendar")
    public Flux<RDVDTO> getAllRDVBasedOnCurrentAccount(Principal principal) throws Exception {
        log.debug("REST request to get RDV : {}", principal);
        Flux<RDVDTO> notMine = rdvService.findAllExceptOfCurrentBadgeAccount(principal);
        Flux<RDVDTO> mine = rdvService.findAllOfCurrentBadgeAccount(principal);
        return notMine
            .map(rdv -> {
                rdv.setBadgeId(null);
                rdv.setBadgeUid(null);
                rdv.setBadgePackStatus(null);
                rdv.setRelBadgePackId(null);
                rdv.setPackId(null);
                rdv.setPackName(null);
                rdv.setBadgePackStatus(null);
                rdv.setProfileEmail(null);
                return rdv;
            })
            .mergeWith(mine);
    }

    /**
     * {@code POST  /rdvs} : Create a new rdv.
     *
     * @param rdvDTO the rdvDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rdvDTO, or with status {@code 400 (Bad Request)} if the rdv has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rdvs")
    public Mono<ResponseEntity<RDVDTO>> createRDV(@RequestBody RDVDTO rdvDTO, Principal principal) throws Exception {
        log.debug("REST request to save RDV : {}", rdvDTO);
        if (rdvDTO.getId() != null) {
            throw new BadRequestAlertException("A new rdv cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return rdvService
            .createRDV(rdvDTO, principal)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/rdvs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
