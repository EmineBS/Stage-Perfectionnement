package com.alphalab.service.impl;

import com.alphalab.domain.Feature;
import com.alphalab.repository.FeatureRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.FeatureService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.FeatureDTO;
import com.alphalab.service.mapper.FeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Feature}.
 */
@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureServiceImpl.class);

    private final FeatureRepository featureRepository;

    private final FeatureMapper featureMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    public FeatureServiceImpl(
        FeatureRepository featureRepository,
        FeatureMapper featureMapper,
        BadgeService badgeService,
        UserService userService
    ) {
        this.featureRepository = featureRepository;
        this.featureMapper = featureMapper;
        this.badgeService = badgeService;
        this.userService = userService;
    }

    @Override
    public Mono<FeatureDTO> save(FeatureDTO featureDTO) {
        log.debug("Request to save Feature 133 : {}", featureDTO);
        return featureRepository.save(featureMapper.toEntity(featureDTO)).map(featureMapper::toDto);
    }

    @Override
    public Mono<FeatureDTO> update(FeatureDTO featureDTO) {
        log.debug("Request to update Feature : {}", featureDTO);
        return featureRepository.save(featureMapper.toEntity(featureDTO)).map(featureMapper::toDto);
    }

    @Override
    public Mono<FeatureDTO> partialUpdate(FeatureDTO featureDTO) {
        log.debug("Request to partially update Feature : {}", featureDTO);

        return featureRepository
            .findById(featureDTO.getId())
            .map(existingFeature -> {
                featureMapper.partialUpdate(existingFeature, featureDTO);

                return existingFeature;
            })
            .flatMap(featureRepository::save)
            .map(featureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<FeatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Features");
        return featureRepository.findAllBy(pageable).map(featureMapper::toDto);
    }

    public Mono<Long> countAll() {
        return featureRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<FeatureDTO> findOne(Long id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findById(id).map(featureMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Feature : {}", id);
        return featureRepository.deleteById(id);
    }

    @Override
    public Flux<FeatureDTO> findAllByGymId(Long gym_id) {
        return featureRepository.findAllByGymId(gym_id).map(featureMapper::toDto);
    }

    @Override
    public Flux<FeatureDTO> findAllByGym(Pageable pageable,Long gym_id) {
        return featureRepository.findAllByGym(pageable, gym_id).map(featureMapper::toDto);
    }

    @Override
    public Mono<Long> countAllByGym(Long gym_id) {
        return featureRepository.countAllByGym(gym_id);
    }
}
