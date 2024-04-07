package com.alphalab.web.rest;

import com.alphalab.repository.CriteriaRepository;
import com.alphalab.service.CriteriaService;
import com.alphalab.service.dto.CriteriaDTO;
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
 * REST controller for managing {@link com.alphalab.domain.Criteria}.
 */
@RestController
@RequestMapping("/api")
public class CriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CriteriaResource.class);

    private static final String ENTITY_NAME = "criteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CriteriaService criteriaService;

    private final CriteriaRepository criteriaRepository;

    public CriteriaResource(CriteriaService criteriaService, CriteriaRepository criteriaRepository) {
        this.criteriaService = criteriaService;
        this.criteriaRepository = criteriaRepository;
    }

    /**
     * {@code POST  /criterias} : Create a new criteria.
     *
     * @param criteriaDTO the criteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new criteriaDTO, or with status {@code 400 (Bad Request)} if the criteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/criterias")
    public Mono<ResponseEntity<CriteriaDTO>> createCriteria(@RequestBody CriteriaDTO criteriaDTO) throws URISyntaxException {
        log.debug("REST request to save Criteria : {}", criteriaDTO);
        if (criteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new criteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return criteriaService
            .save(criteriaDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/criterias/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /criterias/:id} : Updates an existing criteria.
     *
     * @param id the id of the criteriaDTO to save.
     * @param criteriaDTO the criteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criteriaDTO,
     * or with status {@code 400 (Bad Request)} if the criteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the criteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/criterias/{id}")
    public Mono<ResponseEntity<CriteriaDTO>> updateCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CriteriaDTO criteriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Criteria : {}, {}", id, criteriaDTO);
        if (criteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criteriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return criteriaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return criteriaService
                    .update(criteriaDTO)
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
     * {@code PATCH  /criterias/:id} : Partial updates given fields of an existing criteria, field will ignore if it is null
     *
     * @param id the id of the criteriaDTO to save.
     * @param criteriaDTO the criteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criteriaDTO,
     * or with status {@code 400 (Bad Request)} if the criteriaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the criteriaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the criteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/criterias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<CriteriaDTO>> partialUpdateCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CriteriaDTO criteriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Criteria partially : {}, {}", id, criteriaDTO);
        if (criteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criteriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return criteriaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<CriteriaDTO> result = criteriaService.partialUpdate(criteriaDTO);

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
     * {@code GET  /criterias} : get all the criterias.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of criterias in body.
     */
    @GetMapping("/criterias")
    public Mono<ResponseEntity<List<CriteriaDTO>>> getAllCriterias(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Criterias");
        return criteriaService
            .countAll()
            .zipWith(criteriaService.findAll(pageable).collectList())
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
     * {@code GET  /criterias/:id} : get the "id" criteria.
     *
     * @param id the id of the criteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the criteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/criterias/{id}")
    public Mono<ResponseEntity<CriteriaDTO>> getCriteria(@PathVariable Long id) {
        log.debug("REST request to get Criteria : {}", id);
        Mono<CriteriaDTO> criteriaDTO = criteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(criteriaDTO);
    }

    /**
     * {@code DELETE  /criterias/:id} : delete the "id" criteria.
     *
     * @param id the id of the criteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/criterias/{id}")
    public Mono<ResponseEntity<Void>> deleteCriteria(@PathVariable Long id) {
        log.debug("REST request to delete Criteria : {}", id);
        return criteriaService
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
     * {@code GET  /criterias/gym/:gym_id} : get all the criterias by gym.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of criterias in body.
     */
    @GetMapping("/criterias/gym/{gym_id}")
    public Mono<ResponseEntity<List<CriteriaDTO>>> getAllCriteriasByGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) {
        log.debug("REST request to get a page of Criterias");
        return criteriaService
            .countAllByGymId(gym_id)
            .zipWith(criteriaService.findAllByGymId(pageable, gym_id).collectList())
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
