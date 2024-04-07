package com.alphalab.service.impl;

import com.alphalab.domain.RelGymFeature;
import com.alphalab.repository.RelGymFeatureRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.RelGymFeatureService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.RelGymFeatureDTO;
import com.alphalab.service.mapper.RelGymFeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link RelGymFeature}.
 */
@Service
@Transactional
public class RelGymFeatureServiceImpl implements RelGymFeatureService {

    private final Logger log = LoggerFactory.getLogger(RelGymFeatureServiceImpl.class);

    private final RelGymFeatureRepository relGymFeatureRepository;

    private final RelGymFeatureMapper relGymFeatureMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    private MessageSource messageSource;

    public RelGymFeatureServiceImpl(
        RelGymFeatureRepository relGymFeatureRepository,
        RelGymFeatureMapper relGymFeatureMapper,
        BadgeService badgeService,
        UserService userService,
        MessageSource messageSource) {
        this.relGymFeatureRepository = relGymFeatureRepository;
        this.relGymFeatureMapper = relGymFeatureMapper;
        this.badgeService = badgeService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<RelGymFeatureDTO> save(RelGymFeatureDTO relGymFeatureDTO) {
        log.debug("Request to save RelGymFeature 133 : {}", relGymFeatureDTO);
        return relGymFeatureRepository
            .findRelByFeatureAndGym(relGymFeatureDTO.getGymId(), relGymFeatureDTO.getFeatureId())
            .hasElement()
            .flatMap(dataFound -> {
                if (dataFound) return Mono.error(
                    new RuntimeException(messageSource.getMessage("error.relGymFeature", null, LocaleContextHolder.getLocale()))
                );
                else return relGymFeatureRepository.save(relGymFeatureMapper.toEntity(relGymFeatureDTO)).map(relGymFeatureMapper::toDto);
            });
    }

    @Override
    public Mono<RelGymFeatureDTO> update(RelGymFeatureDTO relGymFeatureDTO) {
        log.debug("Request to update RelGymFeature : {}", relGymFeatureDTO);
        return relGymFeatureRepository.save(relGymFeatureMapper.toEntity(relGymFeatureDTO)).map(relGymFeatureMapper::toDto);
    }

    @Override
    public Mono<RelGymFeatureDTO> partialUpdate(RelGymFeatureDTO relGymFeatureDTO) {
        log.debug("Request to partially update RelGymFeature : {}", relGymFeatureDTO);

        return relGymFeatureRepository
            .findById(relGymFeatureDTO.getId())
            .map(existingRelGymFeature -> {
                relGymFeatureMapper.partialUpdate(existingRelGymFeature, relGymFeatureDTO);

                return existingRelGymFeature;
            })
            .flatMap(relGymFeatureRepository::save)
            .map(relGymFeatureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RelGymFeatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelGymFeatures");
        return relGymFeatureRepository.findAllBy(pageable).map(relGymFeatureMapper::toDto);
    }

    public Mono<Long> countAll() {
        return relGymFeatureRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RelGymFeatureDTO> findOne(Long id) {
        log.debug("Request to get RelGymFeature : {}", id);
        return relGymFeatureRepository.findById(id).map(relGymFeatureMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete RelGymFeature : {}", id);
        return relGymFeatureRepository.deleteById(id);
    }

    @Override
    public Mono<RelGymFeatureDTO> findRelOfGymWhereFeatureIsBadgeDesignation(Long gym_id) {
        return relGymFeatureRepository.findRelOfGymWhereFeatureIsBadgeDesignation(gym_id).map(relGymFeatureMapper::toDto);
    }

    @Override
    public Flux<RelGymFeatureDTO> findFeaturesByGym(Pageable pageable, Long gym_id) {
        return relGymFeatureRepository.findFeaturesByGym(pageable, gym_id).map(relGymFeatureMapper::toDto);
    }

    @Override
    public Mono<Long> countFeaturesByGym(Long gym_id) {
        return relGymFeatureRepository.countFeaturesByGym(gym_id);
    }

    @Override
    public Mono<RelGymFeatureDTO> findRelByFeatureAndGym(Long gym_id, Long feature_id) {
        return relGymFeatureRepository.findRelByFeatureAndGym(gym_id, feature_id).map(relGymFeatureMapper::toDto);
    }
}
