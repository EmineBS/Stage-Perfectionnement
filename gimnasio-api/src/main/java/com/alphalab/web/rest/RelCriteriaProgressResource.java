package com.alphalab.web.rest;

import com.alphalab.repository.RelCriteriaProgressRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.RelCriteriaProgressService;
import com.alphalab.service.dto.CriteriaBadgeProgressDTO;
import com.alphalab.service.dto.RelCriteriaProgressDTO;
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
 * REST controller for managing {@link com.alphalab.domain.RelCriteriaProgress}.
 */
@RestController
@RequestMapping("/api")
public class RelCriteriaProgressResource {

    private final Logger log = LoggerFactory.getLogger(RelCriteriaProgressResource.class);

    private static final String ENTITY_NAME = "relCriteriaProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelCriteriaProgressService relCriteriaProgressService;

    private final RelCriteriaProgressRepository relCriteriaProgressRepository;

    private final BadgeService badgeService;

    public RelCriteriaProgressResource(
        RelCriteriaProgressService relCriteriaProgressService,
        RelCriteriaProgressRepository relCriteriaProgressRepository,
        BadgeService badgeService
    ) {
        this.relCriteriaProgressService = relCriteriaProgressService;
        this.relCriteriaProgressRepository = relCriteriaProgressRepository;
        this.badgeService = badgeService;
    }

    /**
     * {@code POST  /relCriteriaProgresss} : Create a new relCriteriaProgress.
     *
     * @param relCriteriaProgressDTO the relCriteriaProgressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relCriteriaProgressDTO, or with status {@code 400 (Bad Request)} if the relCriteriaProgress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relCriteriaProgresss")
    public Mono<ResponseEntity<RelCriteriaProgressDTO>> createRelCriteriaProgress(
        @RequestBody RelCriteriaProgressDTO relCriteriaProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to save RelCriteriaProgress : {}", relCriteriaProgressDTO);
        if (relCriteriaProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new relCriteriaProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return relCriteriaProgressService
            .save(relCriteriaProgressDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/relCriteriaProgresss/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /relCriteriaProgresss/:id} : Updates an existing relCriteriaProgress.
     *
     * @param id the id of the relCriteriaProgressDTO to save.
     * @param relCriteriaProgressDTO the relCriteriaProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relCriteriaProgressDTO,
     * or with status {@code 400 (Bad Request)} if the relCriteriaProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relCriteriaProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relCriteriaProgresss/{id}")
    public Mono<ResponseEntity<RelCriteriaProgressDTO>> updateRelCriteriaProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelCriteriaProgressDTO relCriteriaProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelCriteriaProgress : {}, {}", id, relCriteriaProgressDTO);
        if (relCriteriaProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relCriteriaProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relCriteriaProgressRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return relCriteriaProgressService
                    .update(relCriteriaProgressDTO)
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
     * {@code PATCH  /relCriteriaProgresss/:id} : Partial updates given fields of an existing relCriteriaProgress, field will ignore if it is null
     *
     * @param id the id of the relCriteriaProgressDTO to save.
     * @param relCriteriaProgressDTO the relCriteriaProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relCriteriaProgressDTO,
     * or with status {@code 400 (Bad Request)} if the relCriteriaProgressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relCriteriaProgressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relCriteriaProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/relCriteriaProgresss/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RelCriteriaProgressDTO>> partialUpdateRelCriteriaProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelCriteriaProgressDTO relCriteriaProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelCriteriaProgress partially : {}, {}", id, relCriteriaProgressDTO);
        if (relCriteriaProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relCriteriaProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relCriteriaProgressRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RelCriteriaProgressDTO> result = relCriteriaProgressService.partialUpdate(relCriteriaProgressDTO);

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
     * {@code GET  /relCriteriaProgresss} : get all the relCriteriaProgresss.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relCriteriaProgresss in body.
     */
    @GetMapping("/admin/relCriteriaProgresss")
    public Mono<ResponseEntity<List<RelCriteriaProgressDTO>>> getAllRelCriteriaProgresss(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RelCriteriaProgresss");
        return relCriteriaProgressService
            .countAll()
            .zipWith(relCriteriaProgressService.findAll(pageable).collectList())
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
     * {@code GET  /relCriteriaProgresss/:id} : get the "id" relCriteriaProgress.
     *
     * @param id the id of the relCriteriaProgressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relCriteriaProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relCriteriaProgresss/{id}")
    public Mono<ResponseEntity<RelCriteriaProgressDTO>> getRelCriteriaProgress(@PathVariable Long id) {
        log.debug("REST request to get RelCriteriaProgress : {}", id);
        Mono<RelCriteriaProgressDTO> relCriteriaProgressDTO = relCriteriaProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relCriteriaProgressDTO);
    }

    /**
     * {@code DELETE  /relCriteriaProgresss/:id} : delete the "id" relCriteriaProgress.
     *
     * @param id the id of the relCriteriaProgressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relCriteriaProgresss/{id}")
    public Mono<ResponseEntity<Void>> deleteRelCriteriaProgress(@PathVariable Long id) {
        log.debug("REST request to delete RelCriteriaProgress : {}", id);
        return relCriteriaProgressService
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
     * {@code GET  /relCriteriaProgresss/gym/badge} : get the progress of badge.
     *
     * @param badge_id the id badge.
     * @param gym_id the id gym.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relCriteriaProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relCriteriaProgresss/gym/badge/{gym_id}/{badge_id}")
    public Flux<CriteriaBadgeProgressDTO> findAllCriteriaByBadgeSession(@PathVariable Long gym_id, @PathVariable Long badge_id) {
        log.debug("REST request to get RelCriteriaProgress : {} {}", badge_id, gym_id);
        try {
            return relCriteriaProgressService.findAllCriteriaByBadge(gym_id, badge_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /relCriteriaProgresss/account} : get the progress of badge.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relCriteriaProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relCriteriaProgresss/account")
    public Flux<CriteriaBadgeProgressDTO> getCriteriaProgressOfBadgeSession(Principal principal) throws Exception {
        log.debug("REST request to get RelCriteriaProgress :{}", principal);
        return badgeService
            .findOneBySession(principal)
            .flux()
            .flatMap(badge -> {
                try {
                    return relCriteriaProgressService.findAllCriteriaByBadge(badge.getGymId(), badge.getId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
