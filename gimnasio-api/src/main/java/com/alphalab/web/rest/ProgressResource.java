package com.alphalab.web.rest;

import com.alphalab.repository.ProgressRepository;
import com.alphalab.service.ProgressService;
import com.alphalab.service.dto.ProgressDTO;
import com.alphalab.service.dto.SaveProgressDTO;
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
 * REST controller for managing {@link com.alphalab.domain.Progress}.
 */
@RestController
@RequestMapping("/api")
public class ProgressResource {

    private final Logger log = LoggerFactory.getLogger(ProgressResource.class);

    private static final String ENTITY_NAME = "progress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgressService progressService;

    private final ProgressRepository progressRepository;

    public ProgressResource(ProgressService progressService, ProgressRepository progressRepository) {
        this.progressService = progressService;
        this.progressRepository = progressRepository;
    }

    //    /**
    //     * {@code POST  /progresss_deprecated} : Create a new progress.
    //     *
    //     * @param progressDTO the progressDTO to create.
    //     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new progressDTO, or with status {@code 400 (Bad Request)} if the progress has already an ID.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PostMapping("/progresss_deprecated")
    //    public Mono<ResponseEntity<ProgressDTO>> createProgress_deprecated(@RequestBody ProgressDTO progressDTO) throws URISyntaxException {
    //        log.debug("REST request to save Progress : {}", progressDTO);
    //        if (progressDTO.getId() != null) {
    //            throw new BadRequestAlertException("A new progress cannot already have an ID", ENTITY_NAME, "idexists");
    //        }
    //        return progressService
    //            .save(progressDTO)
    //            .map(result -> {
    //                try {
    //                    return ResponseEntity
    //                        .created(new URI("/api/progresss/" + result.getId()))
    //                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
    //                        .body(result);
    //                } catch (URISyntaxException e) {
    //                    throw new RuntimeException(e);
    //                }
    //            });
    //    }

    /**
     * {@code PUT  /progresss/:id} : Updates an existing progress.
     *
     * @param id the id of the progressDTO to save.
     * @param progressDTO the progressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated progressDTO,
     * or with status {@code 400 (Bad Request)} if the progressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the progressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/progresss/{id}")
    public Mono<ResponseEntity<ProgressDTO>> updateProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProgressDTO progressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Progress : {}, {}", id, progressDTO);
        if (progressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, progressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return progressRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return progressService
                    .update(progressDTO)
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
     * {@code PATCH  /progresss/:id} : Partial updates given fields of an existing progress, field will ignore if it is null
     *
     * @param id the id of the progressDTO to save.
     * @param progressDTO the progressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated progressDTO,
     * or with status {@code 400 (Bad Request)} if the progressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the progressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the progressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/progresss/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ProgressDTO>> partialUpdateProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProgressDTO progressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Progress partially : {}, {}", id, progressDTO);
        if (progressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, progressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return progressRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ProgressDTO> result = progressService.partialUpdate(progressDTO);

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
     * {@code GET  /progresss} : get all the progresss.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of progresss in body.
     */
    @GetMapping("/progresss")
    public Mono<ResponseEntity<List<ProgressDTO>>> getAllProgresss(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Progresss");
        return progressService
            .countAll()
            .zipWith(progressService.findAll(pageable).collectList())
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
     * {@code GET  /progresss/:id} : get the "id" progress.
     *
     * @param id the id of the progressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the progressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/progresss/{id}")
    public Mono<ResponseEntity<ProgressDTO>> getProgress(@PathVariable Long id) {
        log.debug("REST request to get Progress : {}", id);
        Mono<ProgressDTO> progressDTO = progressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(progressDTO);
    }

    /**
     * {@code DELETE  /progresss/:id} : delete the "id" progress.
     *
     * @param id the id of the progressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/progresss/{id}")
    public Mono<ResponseEntity<Void>> deleteProgress(@PathVariable Long id) {
        log.debug("REST request to delete Progress : {}", id);
        return progressService
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
     * {@code POST  /progresss} : Create a new progress.
     *
     * @param progressDTO the progressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new progressDTO, or with status {@code 400 (Bad Request)} if the progress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/progresss")
    public Mono<ResponseEntity<ProgressDTO>> createProgress(@RequestBody SaveProgressDTO progressDTO) throws URISyntaxException {
        log.debug("REST request to save Progress : {}", progressDTO);
        if (progressDTO.getId() != null) {
            throw new BadRequestAlertException("A new progress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return progressService
            .save(progressDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/progresss/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
