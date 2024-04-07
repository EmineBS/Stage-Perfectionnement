package com.alphalab.service.impl;

import com.alphalab.domain.Game;
import com.alphalab.domain.User;
import com.alphalab.repository.GameRepository;
import com.alphalab.service.GameService;
import com.alphalab.service.dto.GameDTO;
import com.alphalab.service.mapper.GameMapper;
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

import java.security.Principal;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    
    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private MessageSource messageSource;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper, MessageSource messageSource) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<GameDTO> save(GameDTO gameDTO) {
        log.debug("Request to save Badge : {}", gameDTO);
        return gameRepository.save(gameMapper.toEntity(gameDTO)).map(gameMapper::toDto);
    }

    @Override
    public Mono<GameDTO> update(GameDTO gameDTO) {
        log.debug("Request to update Amine : {}", gameDTO);
        return gameRepository.save(gameMapper.toEntity(gameDTO)).map(gameMapper::toDto);
    }

    @Override
    public Mono<GameDTO> partialUpdate(GameDTO gameDTO) {
        log.debug("Request to partially update Amine : {}", gameDTO);

        return gameRepository
            .findById(gameDTO.getId())
            .map(existingBadge -> {
                gameMapper.partialUpdate(existingBadge, gameDTO);

                return existingBadge;
            })
            .flatMap(gameRepository::save)
            .map(gameMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<GameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Games");
        return gameRepository.findAllBy(pageable).map(gameMapper::toDto);
    }

    public Mono<Long> countAll() {
        return gameRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<GameDTO> findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        return gameRepository.findById(id).map(gameMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        return gameRepository.deleteById(id);
    }

}