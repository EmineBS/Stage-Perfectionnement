package com.alphalab.service.impl;

import com.alphalab.domain.Amine;
import com.alphalab.domain.User;
import com.alphalab.repository.AmineRepository;
import com.alphalab.repository.BadgeRepository;
import com.alphalab.service.AmineService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.AmineDTO;
import com.alphalab.service.dto.BadgeDTO;
import com.alphalab.service.mapper.AmineMapper;
import com.alphalab.service.mapper.BadgeMapper;
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

/**
 * Service Implementation for managing {@link Amine}.
 */
@Service
@Transactional
public class AmineServiceImpl implements AmineService {

    private final Logger log = LoggerFactory.getLogger(AmineServiceImpl.class);

    private final AmineRepository amineRepository;

    private final AmineMapper amineMapper;

    private final UserService userService;
    private MessageSource messageSource;

    public AmineServiceImpl(AmineRepository amineRepository, AmineMapper amineMapper, UserService userService, MessageSource messageSource) {
        this.amineRepository = amineRepository;
        this.amineMapper = amineMapper;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<AmineDTO> save(AmineDTO amineDTO) {
        log.debug("Request to save Badge : {}", amineDTO);
        return amineRepository.save(amineMapper.toEntity(amineDTO)).map(amineMapper::toDto);
    }

    @Override
    public Mono<AmineDTO> update(AmineDTO amineDTO) {
        log.debug("Request to update Amine : {}", amineDTO);
        return amineRepository.save(amineMapper.toEntity(amineDTO)).map(amineMapper::toDto);
    }

    @Override
    public Mono<AmineDTO> partialUpdate(AmineDTO amineDTO) {
        log.debug("Request to partially update Amine : {}", amineDTO);

        return amineRepository
            .findById(amineDTO.getId())
            .map(existingBadge -> {
                amineMapper.partialUpdate(existingBadge, amineDTO);

                return existingBadge;
            })
            .flatMap(amineRepository::save)
            .map(amineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<AmineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Amines");
        return amineRepository.findAllBy(pageable).map(amineMapper::toDto);
    }

    public Mono<Long> countAll() {
        return amineRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<AmineDTO> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return amineRepository.findById(id).map(amineMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        return amineRepository.deleteById(id);
    }

}
