package com.alphalab.service.impl;

import com.alphalab.domain.Badge;
import com.alphalab.domain.User;
import com.alphalab.repository.BadgeRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.BadgeDTO;
import com.alphalab.service.mapper.BadgeMapper;
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
 * Service Implementation for managing {@link Badge}.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    private final BadgeRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    private final UserService userService;
    private MessageSource messageSource;

    public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper, UserService userService, MessageSource messageSource) {
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<BadgeDTO> save(BadgeDTO badgeDTO) {
        log.debug("Request to save Badge : {}", badgeDTO);
        return badgeRepository.save(badgeMapper.toEntity(badgeDTO)).map(badgeMapper::toDto);
    }

    @Override
    public Mono<BadgeDTO> update(BadgeDTO badgeDTO) {
        log.debug("Request to update Badge : {}", badgeDTO);
        return badgeRepository.save(badgeMapper.toEntity(badgeDTO)).map(badgeMapper::toDto);
    }

    @Override
    public Mono<BadgeDTO> partialUpdate(BadgeDTO badgeDTO) {
        log.debug("Request to partially update Badge : {}", badgeDTO);

        return badgeRepository
            .findById(badgeDTO.getId())
            .map(existingBadge -> {
                badgeMapper.partialUpdate(existingBadge, badgeDTO);

                return existingBadge;
            })
            .flatMap(badgeRepository::save)
            .map(badgeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<BadgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Badges");
        return badgeRepository.findAllBy(pageable).map(badgeMapper::toDto);
    }

    public Mono<Long> countAll() {
        return badgeRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<BadgeDTO> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return badgeRepository.findById(id).map(badgeMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        return badgeRepository.deleteById(id);
    }

    @Override
    public Flux<BadgeDTO> findAllBadgesOfGym(Pageable pageable, Long gym_id) {
        return badgeRepository.findAllBadgesOfGym(gym_id, pageable).map(badgeMapper::toDto);
    }

    @Override
    public Mono<Long> countFindAllBadgesOfGym(Long gym_id) {
        return badgeRepository.countFindAllBadgesOfGym(gym_id);
    }

    @Override
    public Mono<BadgeDTO> findOneBySession(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return badgeRepository.findOneBySession(user.getLogin()).map(badgeMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }
}
