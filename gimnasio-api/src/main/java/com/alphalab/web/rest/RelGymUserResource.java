package com.alphalab.web.rest;

import com.alphalab.repository.RelUserGymRepository;
import com.alphalab.service.RelUserGymService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.alphalab.domain.RelUserGym}.
 */
@RestController
@RequestMapping("/api")
public class RelGymUserResource {

    private final Logger log = LoggerFactory.getLogger(RelGymUserResource.class);

    private static final String ENTITY_NAME = "relUserGym";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelUserGymService relUserGymService;

    private final RelUserGymRepository relUserGymRepository;

    public RelGymUserResource(RelUserGymService relUserGymService, RelUserGymRepository relUserGymRepository) {
        this.relUserGymService = relUserGymService;
        this.relUserGymRepository = relUserGymRepository;
    }

    /**
     * {@code POST  /rel_user_gym} : Create a new relUserGym.
     *
     * @param relUserGymDTO the relUserGymDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relUserGymDTO, or with status {@code 400 (Bad Request)} if the relUserGym has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rel_user_gym")
    public Mono<ResponseEntity<RelUserGymDTO>> createRelUserGym(@RequestBody RelUserGymDTO relUserGymDTO) throws URISyntaxException {
        log.debug("REST request to save RelUserGym : {}", relUserGymDTO);
        if (relUserGymDTO.getId() != null) {
            throw new BadRequestAlertException("A new relUserGym cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return relUserGymService
            .save(relUserGymDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/rel_user_gym/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /rel_user_gym/:id} : Updates an existing relUserGym.
     *
     * @param id the id of the relUserGymDTO to save.
     * @param relUserGymDTO the relUserGymDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relUserGymDTO,
     * or with status {@code 400 (Bad Request)} if the relUserGymDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relUserGymDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rel_user_gym/{id}")
    public Mono<ResponseEntity<RelUserGymDTO>> updateRelUserGym(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelUserGymDTO relUserGymDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelUserGym : {}, {}", id, relUserGymDTO);
        if (relUserGymDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relUserGymDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relUserGymRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return relUserGymService
                    .update(relUserGymDTO)
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
     * {@code PATCH  /rel_user_gym/:id} : Partial updates given fields of an existing relUserGym, field will ignore if it is null
     *
     * @param id the id of the relUserGymDTO to save.
     * @param relUserGymDTO the relUserGymDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relUserGymDTO,
     * or with status {@code 400 (Bad Request)} if the relUserGymDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relUserGymDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relUserGymDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rel_user_gym/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RelUserGymDTO>> partialUpdateRelUserGym(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelUserGymDTO relUserGymDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelUserGym partially : {}, {}", id, relUserGymDTO);
        if (relUserGymDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relUserGymDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relUserGymRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RelUserGymDTO> result = relUserGymService.partialUpdate(relUserGymDTO);

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
     * {@code GET  /rel_user_gym} : get all the rel_user_gym.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rel_user_gym in body.
     */
    @GetMapping("/rel_user_gym")
    public Mono<ResponseEntity<List<RelUserGymDTO>>> getAllRelUserGyms(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RelUserGyms");
        return relUserGymService
            .countAll()
            .zipWith(relUserGymService.findAll(pageable).collectList())
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
     * {@code GET  /rel_user_gym/:id} : get the "id" relUserGym.
     *
     * @param id the id of the relUserGymDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relUserGymDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rel_user_gym/{id}")
    public Mono<ResponseEntity<RelUserGymDTO>> getRelUserGym(@PathVariable Long id) {
        log.debug("REST request to get RelUserGym : {}", id);
        Mono<RelUserGymDTO> relUserGymDTO = relUserGymService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relUserGymDTO);
    }

    /**
     * {@code DELETE  /rel_user_gym/:id} : delete the "id" relUserGym.
     *
     * @param id the id of the relUserGymDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rel_user_gym/{id}")
    public Mono<ResponseEntity<Void>> deleteRelUserGym(@PathVariable Long id) {
        log.debug("REST request to delete RelUserGym : {}", id);
        return relUserGymService
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
     * {@code GET  /rel_user_gym/users/:gym_id} : get all the rel_user_gym.
     *
     * @param pageable the pagination information.
     * @param gym_id the gym id.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rel_user_gym in body.
     */
    @GetMapping("/rel_user_gym/users/{gym_id}")
    public Mono<ResponseEntity<List<RelUserGymDTO>>> getGymUsers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of RelUserGyms");
        return relUserGymService
            .countUsersByGym(gym_id)
            .zipWith(relUserGymService.findUsersByGym(pageable, gym_id).collectList())
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
