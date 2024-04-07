package com.alphalab.web.rest;

import com.alphalab.repository.AmineRepository;
import com.alphalab.service.AmineService;
import com.alphalab.service.dto.AmineDTO;
import com.alphalab.web.rest.errors.BadRequestAlertException;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * REST controller for managing {@link com.alphalab.domain.Amine}.
 */
@RestController
@RequestMapping("/api")
public class AmineResource {

    private final Logger log = LoggerFactory.getLogger(AmineResource.class);

    private static final String ENTITY_NAME = "amine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmineService amineService;
    private final AmineRepository amineRepository;

    public AmineResource(AmineService amineService, AmineRepository amineRepository) {
        this.amineService = amineService;
        this.amineRepository = amineRepository;
    }

    /**
     * {@code POST  /badges} : Create a new badge.
     *
     * @param amineDTO the badgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeDTO, or with status {@code 400 (Bad Request)} if the badge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amines")
    public Mono<ResponseEntity<AmineDTO>> createAmine(@RequestBody AmineDTO amineDTO) throws URISyntaxException {
        log.debug("REST request to save Amine : {}", amineDTO);
        if (amineDTO.getId() != null) {
            throw new BadRequestAlertException("A new badge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return amineService
            .save(amineDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/amines/" + result.getId()))
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
     * @param amineDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amines/{id}")
    public Mono<ResponseEntity<AmineDTO>> updateAmine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmineDTO amineDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Badge : {}, {}", id, amineDTO);
        if (amineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return amineRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return amineService
                    .update(amineDTO)
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
     * @param amineDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the badgeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/amines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<AmineDTO>> partialUpdateAmine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmineDTO amineDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Badge partially : {}, {}", id, amineDTO);
        if (amineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return amineRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<AmineDTO> result = amineService.partialUpdate(amineDTO);

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
    @GetMapping("/amines")
    public Mono<ResponseEntity<List<AmineDTO>>> getAllAmines(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Amines");
        return amineService
            .countAll()
            .zipWith(amineService.findAll(pageable).collectList())
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
    @GetMapping("/amines/{id}")
    public Mono<ResponseEntity<AmineDTO>> getAmine(@PathVariable Long id) {
        log.debug("REST request to get Amine : {}", id);
        Mono<AmineDTO> amineDTO = amineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amineDTO);
    }

    /**
     * {@code DELETE  /badges/:id} : delete the "id" badge.
     *
     * @param id the id of the badgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amines/{id}")
    public Mono<ResponseEntity<Void>> deleteAmine(@PathVariable Long id) {
        log.debug("REST request to delete Amine : {}", id);
        return amineService
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
}
