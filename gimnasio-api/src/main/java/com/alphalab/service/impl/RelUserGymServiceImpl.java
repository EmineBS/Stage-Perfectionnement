package com.alphalab.service.impl;

import com.alphalab.domain.RelUserGym;
import com.alphalab.domain.User;
import com.alphalab.repository.OffrePackRepository;
import com.alphalab.repository.RelUserGymRepository;
import com.alphalab.service.GymService;
import com.alphalab.service.RelUserGymService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.RelUserGymDTO;
import com.alphalab.service.mapper.RelUserGymMapper;
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

/**
 * Service Implementation for managing {@link RelUserGym}.
 */
@Service
@Transactional
public class RelUserGymServiceImpl implements RelUserGymService {

    private final Logger log = LoggerFactory.getLogger(RelUserGymServiceImpl.class);

    private final RelUserGymRepository relUserGymRepository;

    private final RelUserGymMapper relUserGymMapper;

    private final OffrePackRepository packRepository;

    private final GymService gymService;

    private final UserService userService;

    private MessageSource messageSource;

    public RelUserGymServiceImpl(
        RelUserGymRepository relUserGymRepository,
        RelUserGymMapper relUserGymMapper,
        OffrePackRepository packRepository,
        GymService gymService,
        UserService userService,
        MessageSource messageSource) {
        this.relUserGymRepository = relUserGymRepository;
        this.relUserGymMapper = relUserGymMapper;
        this.packRepository = packRepository;
        this.gymService = gymService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<RelUserGymDTO> save(RelUserGymDTO relUserGymDTO) {
        log.debug("Request to save RelUserGym : {}", relUserGymDTO);
        return relUserGymRepository.save(relUserGymMapper.toEntity(relUserGymDTO)).map(relUserGymMapper::toDto);
    }

    @Override
    public Mono<RelUserGymDTO> update(RelUserGymDTO relUserGymDTO) {
        log.debug("Request to update RelUserGym : {}", relUserGymDTO);
        return relUserGymRepository.save(relUserGymMapper.toEntity(relUserGymDTO)).map(relUserGymMapper::toDto);
    }

    @Override
    public Mono<RelUserGymDTO> partialUpdate(RelUserGymDTO relUserGymDTO) {
        log.debug("Request to partially update RelUserGym : {}", relUserGymDTO);

        return relUserGymRepository
            .findById(relUserGymDTO.getId())
            .map(existingRelUserGym -> {
                relUserGymMapper.partialUpdate(existingRelUserGym, relUserGymDTO);

                return existingRelUserGym;
            })
            .flatMap(relUserGymRepository::save)
            .map(relUserGymMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RelUserGymDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelUserGyms");
        return relUserGymRepository.findAllBy(pageable).map(relUserGymMapper::toDto);
    }

    public Mono<Long> countAll() {
        return relUserGymRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RelUserGymDTO> findOne(Long id) {
        log.debug("Request to get RelUserGym : {}", id);
        return relUserGymRepository.findById(id).map(relUserGymMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete RelUserGym : {}", id);
        return relUserGymRepository.deleteById(id);
    }

    @Override
    public Mono<RelUserGymDTO> checkRelBetweenUserAndGym(String badge_uid, Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return gymService
                .findGymByBadgeUID(badge_uid)
                .flatMap(gym -> {
                    log.debug("Gym of user founded {} user {}", gym, user.getLogin());
                    return relUserGymRepository
                        .findRelUserGym(user.getLogin(), gym.getId())
                        .flatMap(relF -> {
                            log.debug("rel found {}", relF);
                            return Mono.just(relF);
                        });
                })
                .map(relUserGymMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Flux<RelUserGymDTO> findUsersByGym(Pageable pageable, Long gym_id) {
        return relUserGymRepository.findUsersByGym(gym_id, pageable.getPageNumber(), pageable.getPageSize()).map(relUserGymMapper::toDto);
    }

    @Override
    public Mono<Long> countUsersByGym(Long gym_id) {
        return relUserGymRepository.countUsersByGym(gym_id);
    }
}
