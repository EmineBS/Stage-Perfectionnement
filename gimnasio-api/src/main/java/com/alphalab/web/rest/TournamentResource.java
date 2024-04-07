package com.alphalab.web.rest;

import com.alphalab.repository.TournamentRepository;
import com.alphalab.service.TournamentService;
import com.alphalab.service.GameService;
import com.alphalab.service.dto.TournamentDTO;
import com.alphalab.service.dto.GameDTO;
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

@RestController
@RequestMapping("/api")
public class TournamentResource {

    private final Logger log = LoggerFactory.getLogger(TournamentResource.class);

    private static final String ENTITY_NAME = "tournament";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TournamentService tournamentService;

    private final GameService gameService;

    private final TournamentRepository tournamentRepository;

    public TournamentResource(TournamentService tournamentService, TournamentRepository tournamentRepository, GameService gameService) {
        this.tournamentService = tournamentService;
        this.tournamentRepository = tournamentRepository;
        this.gameService = gameService;
    }

    /**
     * {@code POST  /badges} : Create a new badge.
     *
     * @param tournamentDTO the tournamentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tournamentDTO, or with status {@code 400 (Bad Request)} if the tournament has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tournaments")
    public Mono<ResponseEntity<TournamentDTO>> createTournament(@RequestBody TournamentDTO tournamentDTO) throws URISyntaxException {
        log.debug("REST request to save tournament : {}", tournamentDTO);
        if (tournamentDTO.getId() != null) {
            throw new BadRequestAlertException("A new tournament cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return tournamentService
            .save(tournamentDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/tournaments/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @PutMapping("/tournaments/{id}")
    public Mono<ResponseEntity<TournamentDTO>> updateTournament(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TournamentDTO tournamentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tournament : {}, {}", id, tournamentDTO);
        if (tournamentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tournamentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return tournamentRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return tournamentService
                    .update(tournamentDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }
    
    @PatchMapping(value = "/tournaments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TournamentDTO>> partialUpdateTournament(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TournamentDTO tournamentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tournament partially : {}, {}", id, tournamentDTO);
        if (tournamentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tournamentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return tournamentRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TournamentDTO> result = tournamentService.partialUpdate(tournamentDTO);

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

    @GetMapping("/tournaments")
    public Mono<ResponseEntity<List<TournamentDTO>>> getAllTournaments(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Tournaments");
        return tournamentService
            .countAll()
            .zipWith(tournamentService.findAll(pageable).collectList())
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

    @GetMapping("/tournaments/{id}")
    public Mono<ResponseEntity<TournamentDTO>> getTournament(@PathVariable Long id) {
        log.debug("REST request to get Tournament : {}", id);
        Mono<TournamentDTO> tournamentDTO = tournamentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tournamentDTO);
    }

    @DeleteMapping("/tournaments/{id}")
    public Mono<ResponseEntity<Void>> deleteTournament(@PathVariable Long id) {
        log.debug("REST request to delete Tournament : {}", id);
        return tournamentService
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

    @GetMapping("/tournaments/game/{gameid}")
    public Mono<ResponseEntity<List<TournamentDTO>>> getAllTournamentsByGame(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable Long gameid
    ) {
        log.debug("REST request to get a page of Tournaments");
        return tournamentService
            .countFindAllTournamentsOfGame(gameid)
            .zipWith(tournamentService.findAllTournamentsOfGame(pageable, gameid).collectList())
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