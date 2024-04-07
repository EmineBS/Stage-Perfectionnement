package com.alphalab.web.rest;

import com.alphalab.repository.RelUserTournamentRepository;
import com.alphalab.service.RelUserTournamentService;
import com.alphalab.service.dto.RelUserTournamentDTO;
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
 */
@RestController
@RequestMapping("/api")
public class RelUserTournamentResource {

    private final Logger log = LoggerFactory.getLogger(RelUserTournamentResource.class);

    private static final String ENTITY_NAME = "relUserTournament";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelUserTournamentService relUserTournamentService;

    private final RelUserTournamentRepository relUserTournamentRepository;

    public RelUserTournamentResource(RelUserTournamentService relUserTournamentService, RelUserTournamentRepository relUserTournamentRepository) {
        this.relUserTournamentService = relUserTournamentService;
        this.relUserTournamentRepository = relUserTournamentRepository;
    }

    @PostMapping("/rel_user_tournament")
    public Mono<ResponseEntity<RelUserTournamentDTO>> createRelUserTournament(@RequestBody RelUserTournamentDTO relUserTournamentDTO) throws URISyntaxException {
        log.debug("REST request to save RelUserTournament : {}", relUserTournamentDTO);
        if (relUserTournamentDTO.getId() != null) {
            throw new BadRequestAlertException("A new relUserTournament cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return relUserTournamentService
            .save(relUserTournamentDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/rel_user_tournament/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @PutMapping("/rel_user_tournament/{id}")
    public Mono<ResponseEntity<RelUserTournamentDTO>> updateRelUserTournament(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelUserTournamentDTO relUserTournamentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelUserTournament : {}, {}", id, relUserTournamentDTO);
        if (relUserTournamentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relUserTournamentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relUserTournamentRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return relUserTournamentService
                    .update(relUserTournamentDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    @PatchMapping(value = "/rel_user_tournament/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RelUserTournamentDTO>> partialUpdateRelUserTournament(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelUserTournamentDTO relUserTournamentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelUserTournament partially : {}, {}", id, relUserTournamentDTO);
        if (relUserTournamentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relUserTournamentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relUserTournamentRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RelUserTournamentDTO> result = relUserTournamentService.partialUpdate(relUserTournamentDTO);

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

    @GetMapping("/rel_user_tournament")
    public Mono<ResponseEntity<List<RelUserTournamentDTO>>> getAllRelUserTournaments(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RelUserTournaments");
        return relUserTournamentService
            .countAll()
            .zipWith(relUserTournamentService.findAll(pageable).collectList())
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

    @GetMapping("/rel_user_tournament/{id}")
    public Mono<ResponseEntity<RelUserTournamentDTO>> getRelUserTournament(@PathVariable Long id) {
        log.debug("REST request to get RelUserTournamen : {}", id);
        Mono<RelUserTournamentDTO> relUserTournamentDTO = relUserTournamentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relUserTournamentDTO);
    }

    @DeleteMapping("/rel_user_tournament/{id}")
    public Mono<ResponseEntity<Void>> deleteRelUserTournament(@PathVariable Long id) {
        log.debug("REST request to delete RelUserTournament : {}", id);
        return relUserTournamentService
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

    @GetMapping("/rel_user_tournament/users/{tournament_id}")
    public Mono<ResponseEntity<List<RelUserTournamentDTO>>> getTournamentUsers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long tournament_id
    ) {
        log.debug("REST request to get a page of RelUserTournaments");
        return relUserTournamentService
            .countUsersByTournament(tournament_id)
            .zipWith(relUserTournamentService.findUsersByTournament(pageable, tournament_id).collectList())
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
