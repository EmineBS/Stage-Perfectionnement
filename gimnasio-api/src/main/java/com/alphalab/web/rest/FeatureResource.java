package com.alphalab.web.rest;

import com.alphalab.repository.FeatureRepository;
import com.alphalab.service.FeatureService;
import com.alphalab.service.dto.FeatureDTO;
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
 * REST controller for managing {@link com.alphalab.domain.Feature}.
 */
@RestController
@RequestMapping("/api")
public class FeatureResource {

    private final Logger log = LoggerFactory.getLogger(FeatureResource.class);

    private static final String ENTITY_NAME = "feature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeatureService featureService;

    private final FeatureRepository featureRepository;

    public FeatureResource(FeatureService featureService, FeatureRepository featureRepository) {
        this.featureService = featureService;
        this.featureRepository = featureRepository;
    }

    /**
     * {@code POST  /admin/features} : Create a new feature.
     *
     * @param featureDTO the featureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featureDTO, or with status {@code 400 (Bad Request)} if the feature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/features")
    public Mono<ResponseEntity<FeatureDTO>> createFeature(@RequestBody FeatureDTO featureDTO) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", featureDTO);
        if (featureDTO.getId() != null) {
            throw new BadRequestAlertException("A new feature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return featureService
            .save(featureDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/features/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /admin/features/:id} : Updates an existing feature.
     *
     * @param id the id of the featureDTO to save.
     * @param featureDTO the featureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featureDTO,
     * or with status {@code 400 (Bad Request)} if the featureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the featureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin/features/{id}")
    public Mono<ResponseEntity<FeatureDTO>> updateFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeatureDTO featureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Feature : {}, {}", id, featureDTO);
        if (featureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return featureRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return featureService
                    .update(featureDTO)
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
     * {@code PATCH  /admin/features/:id} : Partial updates given fields of an existing feature, field will ignore if it is null
     *
     * @param id the id of the featureDTO to save.
     * @param featureDTO the featureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featureDTO,
     * or with status {@code 400 (Bad Request)} if the featureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the featureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the featureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin/features/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<FeatureDTO>> partialUpdateFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeatureDTO featureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Feature partially : {}, {}", id, featureDTO);
        if (featureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return featureRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<FeatureDTO> result = featureService.partialUpdate(featureDTO);

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
     * {@code GET  /admin/features} : get all the features.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/admin/features")
    public Mono<ResponseEntity<List<FeatureDTO>>> getAllFeatures(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Features");
        return featureService
            .countAll()
            .zipWith(featureService.findAll(pageable).collectList())
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
     * {@code GET  /features/:id} : get the "id" feature.
     *
     * @param id the id of the featureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the featureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features/{id}")
    public Mono<ResponseEntity<FeatureDTO>> getFeature(@PathVariable Long id) {
        log.debug("REST request to get Feature : {}", id);
        Mono<FeatureDTO> featureDTO = featureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(featureDTO);
    }

    /**
     * {@code DELETE  /admin/features/:id} : delete the "id" feature.
     *
     * @param id the id of the featureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/features/{id}")
    public Mono<ResponseEntity<Void>> deleteFeature(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        return featureService
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
     * {@code GET  /features/gym/:gym_id} : get all the features.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/features/gym/{gym_id}")
    public Mono<ResponseEntity<List<FeatureDTO>>> getAllFeaturesOfGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of Features");
        return featureService
            .countAllByGym(gym_id)
            .zipWith(featureService.findAllByGym(pageable, gym_id).collectList())
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
