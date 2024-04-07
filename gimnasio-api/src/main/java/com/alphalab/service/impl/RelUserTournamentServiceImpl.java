package com.alphalab.service.impl;

import com.alphalab.domain.RelUserTournament;
import com.alphalab.domain.User;
import com.alphalab.repository.OffrePackRepository;
import com.alphalab.repository.RelUserTournamentRepository;
import com.alphalab.service.TournamentService;
import com.alphalab.service.RelUserTournamentService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.RelUserTournamentDTO;
import com.alphalab.service.mapper.RelUserTournamentMapper;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class RelUserTournamentServiceImpl implements RelUserTournamentService {

    private final Logger log = LoggerFactory.getLogger(RelUserTournamentServiceImpl.class);

    private final RelUserTournamentRepository relUserTournamentRepository;

    private final RelUserTournamentMapper relUserTournamentMapper;

    private final OffrePackRepository packRepository;

    private final TournamentService tournamentService;

    private final UserService userService;

    private MessageSource messageSource;

    public RelUserTournamentServiceImpl(
        RelUserTournamentRepository relUserTournamentRepository,
        RelUserTournamentMapper relUserTournamentMapper,
        OffrePackRepository packRepository,
        TournamentService tournamentService,
        UserService userService,
        MessageSource messageSource) {
        this.relUserTournamentRepository = relUserTournamentRepository;
        this.relUserTournamentMapper = relUserTournamentMapper;
        this.packRepository = packRepository;
        this.tournamentService = tournamentService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<RelUserTournamentDTO> save(RelUserTournamentDTO relUserTournamentDTO) {
        log.debug("Request to save RelUserTournament : {}", relUserTournamentDTO);
        return relUserTournamentRepository.save(relUserTournamentMapper.toEntity(relUserTournamentDTO)).map(relUserTournamentMapper::toDto);
    }

    @Override
    public Mono<RelUserTournamentDTO> update(RelUserTournamentDTO relUserTournamentDTO) {
        log.debug("Request to update RelUserTournament : {}", relUserTournamentDTO);
        return relUserTournamentRepository.save(relUserTournamentMapper.toEntity(relUserTournamentDTO)).map(relUserTournamentMapper::toDto);
    }

    @Override
    public Mono<RelUserTournamentDTO> partialUpdate(RelUserTournamentDTO relUserTournamentDTO) {
        log.debug("Request to partially update RelUserTournament : {}", relUserTournamentDTO);

        return relUserTournamentRepository
            .findById(relUserTournamentDTO.getId())
            .map(existingRelUserTournament -> {
                relUserTournamentMapper.partialUpdate(existingRelUserTournament, relUserTournamentDTO);

                return existingRelUserTournament;
            })
            .flatMap(relUserTournamentRepository::save)
            .map(relUserTournamentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RelUserTournamentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelUserTournaments");
        return relUserTournamentRepository.findAllBy(pageable).map(relUserTournamentMapper::toDto);
    }

    public Mono<Long> countAll() {
        return relUserTournamentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RelUserTournamentDTO> findOne(Long id) {
        log.debug("Request to get RelUserTournament : {}", id);
        return relUserTournamentRepository.findById(id).map(relUserTournamentMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete RelUserTournament : {}", id);
        return relUserTournamentRepository.deleteById(id);
    }

    @Override
    public Flux<RelUserTournamentDTO> findUsersByTournament(Pageable pageable, Long tournament_id) {
        return relUserTournamentRepository.findUsersByTournament(tournament_id, pageable.getPageNumber(), pageable.getPageSize()).map(relUserTournamentMapper::toDto);
    }

    @Override
    public Mono<Long> countUsersByTournament(Long tournament_id) {
        return relUserTournamentRepository.countUsersByTournament(tournament_id);
    }
}

