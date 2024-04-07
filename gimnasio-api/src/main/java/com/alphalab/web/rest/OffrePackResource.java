package com.alphalab.web.rest;

import com.alphalab.repository.OffrePackRepository;
import com.alphalab.service.OffrePackService;
import com.alphalab.service.dto.OffrePackDTO;
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
 * REST controller for managing {@link com.alphalab.domain.OffrePack}.
 */
@RestController
@RequestMapping("/api")
public class OffrePackResource {

    private final Logger log = LoggerFactory.getLogger(OffrePackResource.class);

    private static final String ENTITY_NAME = "pack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffrePackService packService;

    private final OffrePackRepository packRepository;

    public OffrePackResource(OffrePackService packService, OffrePackRepository packRepository) {
        this.packService = packService;
        this.packRepository = packRepository;
    }

    /**
     * {@code POST  /packs} : Create a new pack.
     *
     * @param packDTO the packDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packDTO, or with status {@code 400 (Bad Request)} if the pack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/packs")
    public Mono<ResponseEntity<OffrePackDTO>> createOffrePack(@RequestBody OffrePackDTO packDTO) throws URISyntaxException {
        log.debug("REST request to save OffrePack : {}", packDTO);
        if (packDTO.getId() != null) {
            throw new BadRequestAlertException("A new pack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return packService
            .save(packDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/packs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /packs/:id} : Updates an existing pack.
     *
     * @param id the id of the packDTO to save.
     * @param packDTO the packDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packDTO,
     * or with status {@code 400 (Bad Request)} if the packDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/packs/{id}")
    public Mono<ResponseEntity<OffrePackDTO>> updateOffrePack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OffrePackDTO packDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OffrePack : {}, {}", id, packDTO);
        if (packDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return packRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return packService
                    .update(packDTO)
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
     * {@code PATCH  /packs/:id} : Partial updates given fields of an existing pack, field will ignore if it is null
     *
     * @param id the id of the packDTO to save.
     * @param packDTO the packDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packDTO,
     * or with status {@code 400 (Bad Request)} if the packDTO is not valid,
     * or with status {@code 404 (Not Found)} if the packDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the packDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/packs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<OffrePackDTO>> partialUpdateOffrePack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OffrePackDTO packDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OffrePack partially : {}, {}", id, packDTO);
        if (packDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return packRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<OffrePackDTO> result = packService.partialUpdate(packDTO);

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
     * {@code GET  /packs} : get all the packs.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packs in body.
     */
    @GetMapping("/packs")
    public Mono<ResponseEntity<List<OffrePackDTO>>> getAllOffrePacks(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of OffrePacks");
        return packService
            .countAll()
            .zipWith(packService.findAll(pageable).collectList())
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
     * {@code GET  /packs/:id} : get the "id" pack.
     *
     * @param id the id of the packDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/packs/{id}")
    public Mono<ResponseEntity<OffrePackDTO>> getOffrePack(@PathVariable Long id) {
        log.debug("REST request to get OffrePack : {}", id);
        Mono<OffrePackDTO> packDTO = packService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packDTO);
    }

    /**
     * {@code DELETE  /packs/:id} : delete the "id" pack.
     *
     * @param id the id of the packDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/packs/{id}")
    public Mono<ResponseEntity<Void>> deleteOffrePack(@PathVariable Long id) {
        log.debug("REST request to delete OffrePack : {}", id);
        return packService
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
     * {@code GET  /packs/gym/:gym_id} : get all the packs by gym.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crimes in body.
     */
    @GetMapping("/packs/gym/{gym_id}")
    public Mono<ResponseEntity<List<OffrePackDTO>>> getAllPackesByGym(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gym_id
    ) throws Exception {
        log.debug("REST request to get a page of packs by badge id 1");
        return packService
            .countAllPacksByGym(gym_id)
            .zipWith(packService.findAllPacksByGym(pageable, gym_id).collectList())
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
