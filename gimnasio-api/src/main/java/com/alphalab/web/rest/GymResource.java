package com.alphalab.web.rest;

import com.alphalab.repository.GymRepository;
import com.alphalab.service.GymService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.alphalab.domain.Gym}.
 */
@RestController
@RequestMapping("/api")
public class GymResource {

    private final Logger log = LoggerFactory.getLogger(GymResource.class);

    private static final String ENTITY_NAME = "gym";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GymService gymService;

    private final GymRepository gymRepository;

    public GymResource(GymService gymService, GymRepository gymRepository) {
        this.gymService = gymService;
        this.gymRepository = gymRepository;
    }

    /**
     * {@code POST  /admin/gyms} : Create a new gym.
     *
     * @param gymDTO the gymDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gymDTO, or with status {@code 400 (Bad Request)} if the gym has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/gyms_deprecated")
    public Mono<ResponseEntity<GymDTO>> createGym_dep(@RequestBody GymDTO gymDTO) throws URISyntaxException {
        log.debug("REST request to save Gym : {}", gymDTO);
        if (gymDTO.getId() != null) {
            throw new BadRequestAlertException("A new gym cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return gymService
            .save(gymDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/gyms/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /admin/gyms/:id} : Updates an existing gym.
     *
     * @param id the id of the gymDTO to save.
     * @param gymDTO the gymDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gymDTO,
     * or with status {@code 400 (Bad Request)} if the gymDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gymDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin/gyms/{id}")
    public Mono<ResponseEntity<GymDTO>> updateGym(@PathVariable(value = "id", required = false) final Long id, @RequestBody GymDTO gymDTO)
        throws URISyntaxException {
        log.debug("REST request to update Gym : {}, {}", id, gymDTO);
        if (gymDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gymDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return gymRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return gymService
                    .update(gymDTO)
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
     * {@code PATCH  /admin/gyms/:id} : Partial updates given fields of an existing gym, field will ignore if it is null
     *
     * @param id the id of the gymDTO to save.
     * @param gymDTO the gymDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gymDTO,
     * or with status {@code 400 (Bad Request)} if the gymDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gymDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gymDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin/gyms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<GymDTO>> partialUpdateGym(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GymDTO gymDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gym partially : {}, {}", id, gymDTO);
        if (gymDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gymDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return gymRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<GymDTO> result = gymService.partialUpdate(gymDTO);

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
     * {@code GET  /admin/gyms} : get all the gyms.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gyms in body.
     */
    @GetMapping("/admin/gyms")
    public Mono<ResponseEntity<List<GymDTO>>> getAllGyms(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Gyms");
        return gymService
            .countAll()
            .zipWith(gymService.findAll(pageable).collectList())
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
     * {@code GET  /gyms/:id} : get the "id" gym.
     *
     * @param id the id of the gymDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gymDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gyms/{id}")
    public Mono<ResponseEntity<GymDTO>> getGym(@PathVariable Long id) {
        log.debug("REST request to get Gym : {}", id);
        Mono<GymDTO> gymDTO = gymService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gymDTO);
    }

    /**
     * {@code DELETE  /admin/gyms/:id} : delete the "id" gym.
     *
     * @param id the id of the gymDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/gyms/{id}")
    public Mono<ResponseEntity<Void>> deleteGym(@PathVariable Long id) {
        log.debug("REST request to delete Gym : {}", id);
        return gymService
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
     * {@code POST  /admin/gyms} : Create a new gym.
     *
     * @param gymDTO the gymDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gymDTO, or with status {@code 400 (Bad Request)} if the gym has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/gyms")
    public Mono<ResponseEntity<GymDTO>> createGym(@RequestBody GymDTO gymDTO) throws URISyntaxException {
        log.debug("REST request to save Gym : {}", gymDTO);
        if (gymDTO.getId() != null) {
            throw new BadRequestAlertException("A new gym cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return gymService
            .createGym(gymDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/gyms/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code GET  /gyms} : get all the gyms by current user.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crimes in body.
     */
    @GetMapping("/gyms")
    public Mono<ResponseEntity<List<GymDTO>>> getAllGymsByCurrentUser(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        Principal principal
    ) throws Exception {
        log.debug("REST request to get a list of gyms");
        return gymService
            .countAllGymsByUser(principal)
            .zipWith(gymService.findAllGymsByUser(pageable, principal).collectList())
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
     * {@code GET  /gyms/badge-account} : get the "id" gym.
     *
     * @param principal the id of the gymDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gymDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gyms/badge-account")
    public Mono<ResponseEntity<GymDTO>> getGymOfCurrentBadgeAccount(Principal principal) throws Exception {
        log.debug("REST request to get Gym of current badge account : {}", principal);
        Mono<GymDTO> gymDTO = gymService.findGymOfCurrentBadgeAccount(principal);
        return ResponseUtil.wrapOrNotFound(gymDTO);
    }
}
