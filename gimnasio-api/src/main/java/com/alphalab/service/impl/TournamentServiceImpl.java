package com.alphalab.service.impl;

import com.alphalab.domain.Tournament;
import com.alphalab.repository.TournamentRepository;
import com.alphalab.service.TournamentService;
import com.alphalab.service.dto.TournamentDTO;
import com.alphalab.service.mapper.TournamentMapper;
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
public class TournamentServiceImpl implements TournamentService {

    private final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private final TournamentRepository tournamentRepository;

    private final TournamentMapper tournamentMapper;

    private MessageSource messageSource;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper, MessageSource messageSource) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<TournamentDTO> save(TournamentDTO tournamentDTO) {
        log.debug("Request to save Tournament : {}", tournamentDTO);
        return tournamentRepository.save(tournamentMapper.toEntity(tournamentDTO)).map(tournamentMapper::toDto);
    }

    @Override
    public Mono<TournamentDTO> update(TournamentDTO tournamentDTO) {
        log.debug("Request to update Tournament : {}", tournamentDTO);
        return tournamentRepository.save(tournamentMapper.toEntity(tournamentDTO)).map(tournamentMapper::toDto);
    }

    @Override
    public Mono<TournamentDTO> partialUpdate(TournamentDTO tournamentDTO) {
        log.debug("Request to partially update Tournament : {}", tournamentDTO);

        return tournamentRepository
            .findById(tournamentDTO.getId())
            .map(existingTournament -> {
                tournamentMapper.partialUpdate(existingTournament, tournamentDTO);

                return existingTournament;
            })
            .flatMap(tournamentRepository::save)
            .map(tournamentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<TournamentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tournaments");
        return tournamentRepository.findAllBy(pageable).map(tournamentMapper::toDto);
    }

    public Mono<Long> countAll() {
        return tournamentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<TournamentDTO> findOne(Long id) {
        log.debug("Request to get Tournament : {}", id);
        return tournamentRepository.findById(id).map(tournamentMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Tournament : {}", id);
        return tournamentRepository.deleteById(id);
    }

    @Override
    public Flux<TournamentDTO> findAllTournamentsOfGame(Pageable pageable, Long gameid) {
        return tournamentRepository.findAllTournamentsOfGame(gameid, pageable).map(tournamentMapper::toDto);
    }

    @Override
    public Mono<Long> countFindAllTournamentsOfGame(Long gameid) {
        return tournamentRepository.countFindAllTournamentsOfGame(gameid);
    }

}