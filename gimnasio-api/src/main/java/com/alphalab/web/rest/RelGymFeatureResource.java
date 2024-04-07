package com.alphalab.web.rest;

import com.alphalab.repository.RelGymFeatureRepository;
import com.alphalab.service.RelGymFeatureService;
import com.alphalab.service.dto.RelGymFeatureDTO;
import com.alphalab.service.dto.RelUserGymDTO;
import com.alphalab.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link com.alphalab.domain.RelGymFeature}.
 */
@RestController
@RequestMapping("/api")
public class RelGymFeatureResource {

    private final Logger log = LoggerFactory.getLogger(RelGymFeatureResource.class);

    private static final String ENTITY_NAME = "relGymFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelGymFeatureService relGymFeatureService;

    private final RelGymFeatureRepository relGymFeatureRepository;

    public RelGymFeatureResource(RelGymFeatureService relGymFeatureService, RelGymFeatureRepository relGymFeatureRepository) {
        this.relGymFeatureService = relGymFeatureService;
        this.relGymFeatureRepository = relGymFeatureRepository;
    }

    /**
     * {@code POST  /admin/rel_gym_features} : Create a new relGymFeature.
     *
     * @param relGymFeatureDTO the relGymFeatureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relGymFeatureDTO, or with status {@code 400 (Bad Request)} if the relGymFeature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/rel_gym_features")
    public Mono<ResponseEntity<RelGymFeatureDTO>> createRelGymFeature(@RequestBody RelGymFeatureDTO relGymFeatureDTO)
        throws URISyntaxException {
        log.debug("REST request to save RelGymFeature : {}", relGymFeatureDTO);
        if (relGymFeatureDTO.getId() != null) {
            throw new BadRequestAlertException("A new relGymFeature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return relGymFeatureService
            .save(relGymFeatureDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/rel_gym_features/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /admin/rel_gym_features/:id} : Updates an existing relGymFeature.
     *
     * @param id the id of the relGymFeatureDTO to save.
     * @param relGymFeatureDTO the relGymFeatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relGymFeatureDTO,
     * or with status {@code 400 (Bad Request)} if the relGymFeatureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relGymFeatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin/rel_gym_features/{id}")
    public Mono<ResponseEntity<RelGymFeatureDTO>> updateRelGymFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelGymFeatureDTO relGymFeatureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelGymFeature : {}, {}", id, relGymFeatureDTO);
        if (relGymFeatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relGymFeatureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relGymFeatureRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return relGymFeatureService
                    .update(relGymFeatureDTO)
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
     * {@code PATCH  /admin/rel_gym_features/:id} : Partial updates given fields of an existing relGymFeature, field will ignore if it is null
     *
     * @param id the id of the relGymFeatureDTO to save.
     * @param relGymFeatureDTO the relGymFeatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relGymFeatureDTO,
     * or with status {@code 400 (Bad Request)} if the relGymFeatureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relGymFeatureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relGymFeatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin/rel_gym_features/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RelGymFeatureDTO>> partialUpdateRelGymFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelGymFeatureDTO relGymFeatureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelGymFeature partially : {}, {}", id, relGymFeatureDTO);
        if (relGymFeatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relGymFeatureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relGymFeatureRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RelGymFeatureDTO> result = relGymFeatureService.partialUpdate(relGymFeatureDTO);

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
     * {@code GET  /admin/rel_gym_features} : get all the rel_gym_features.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rel_gym_features in body.
     */
    @GetMapping("/admin/rel_gym_features")
    public Mono<ResponseEntity<List<RelGymFeatureDTO>>> getAllRelGymFeatures(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RelGymFeatures");
        return relGymFeatureService
            .countAll()
            .zipWith(relGymFeatureService.findAll(pageable).collectList())
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
     * {@code GET  /admin/rel_gym_features/:id} : get the "id" relGymFeature.
     *
     * @param id the id of the relGymFeatureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relGymFeatureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin/rel_gym_features/{id}")
    public Mono<ResponseEntity<RelGymFeatureDTO>> getRelGymFeature(@PathVariable Long id) {
        log.debug("REST request to get RelGymFeature : {}", id);
        Mono<RelGymFeatureDTO> relGymFeatureDTO = relGymFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relGymFeatureDTO);
    }

    /**
     * {@code DELETE  /admin/rel_gym_features/:id} : delete the "id" relGymFeature.
     *
     * @param id the id of the relGymFeatureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/rel_gym_features/{id}")
    public Mono<ResponseEntity<Void>> deleteRelGymFeature(@PathVariable Long id) {
        log.debug("REST request to delete RelGymFeature : {}", id);
        return relGymFeatureService
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
     * {@code GET  /rel_gym_features/gym-feature/:gym_id} : get the "id" feature is badge_designation by gym.
     *
     * @param gym_id the id of the relGymFeatureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relGymFeatureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rel_gym_features/gym-feature/{gym_id}")
    public Mono<ResponseEntity<RelGymFeatureDTO>> getRelOfGymWhereFeatureIsBadgeDesignation(@PathVariable Long gym_id) {
        log.debug("REST request to get RelGymFeature : {}", gym_id);
        Mono<RelGymFeatureDTO> relGymFeatureDTO = relGymFeatureService.findRelOfGymWhereFeatureIsBadgeDesignation(gym_id);
        return ResponseUtil.wrapOrNotFound(relGymFeatureDTO);
    }

    /**
     * {@code GET  /admin/rel_gym_features/features/:gym_id} : get all the rel_gym_features.
     *
     * @param pageable the pagination information.
     * @param gym_id the gym id.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rel_user_gym in body.
     */
    @GetMapping("/admin/rel_gym_features/features/{gym_id}")
    public Mono<ResponseEntity<List<RelGymFeatureDTO>>> getGymFeatures(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of RelGymFeature");
        return relGymFeatureService
            .countFeaturesByGym(gym_id)
            .zipWith(relGymFeatureService.findFeaturesByGym(pageable, gym_id).collectList())
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
}
