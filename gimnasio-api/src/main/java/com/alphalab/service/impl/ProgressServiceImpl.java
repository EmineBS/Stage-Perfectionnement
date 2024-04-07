package com.alphalab.service.impl;

import com.alphalab.domain.Progress;
import com.alphalab.repository.ProgressRepository;
import com.alphalab.service.*;
import com.alphalab.service.dto.CriteriaValue;
import com.alphalab.service.dto.ProgressDTO;
import com.alphalab.service.dto.RelCriteriaProgressDTO;
import com.alphalab.service.dto.SaveProgressDTO;
import com.alphalab.service.mapper.ProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Progress}.
 */
@Service
@Transactional
public class ProgressServiceImpl implements ProgressService {

    private final Logger log = LoggerFactory.getLogger(ProgressServiceImpl.class);

    private final ProgressRepository progressRepository;

    private final ProgressMapper progressMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    private final RelCriteriaProgressService relCriteriaProgressService;

    public ProgressServiceImpl(
        ProgressRepository progressRepository,
        ProgressMapper progressMapper,
        BadgeService badgeService,
        UserService userService,
        RelCriteriaProgressService relCriteriaProgressService
    ) {
        this.progressRepository = progressRepository;
        this.progressMapper = progressMapper;
        this.badgeService = badgeService;
        this.userService = userService;
        this.relCriteriaProgressService = relCriteriaProgressService;
    }

    @Override
    public Mono<ProgressDTO> save(SaveProgressDTO savedProgressDTO) {
        log.debug("Request to save Progress 133 : {}", savedProgressDTO);
        ProgressDTO progressDto = new ProgressDTO();
        progressDto.setBadgeId(savedProgressDTO.getBadgeId());
        return progressRepository
            .save(progressMapper.toEntity(progressDto))
            .flatMap(savedProgress -> {
                for (CriteriaValue criteriaValue : savedProgressDTO.getCriteriaValues()) {
                    RelCriteriaProgressDTO relCriteriaProgress = new RelCriteriaProgressDTO();
                    relCriteriaProgress.setProgressId(savedProgress.getId());
                    relCriteriaProgress.setCriteriaId(criteriaValue.getIdCriteria());
                    relCriteriaProgress.setValue(criteriaValue.getValue());
                    relCriteriaProgressService
                        .save(relCriteriaProgress)
                        .subscribe(relSaved -> log.debug("rel criteria value saved", relSaved));
                }
                return Mono.just(savedProgress);
            })
            .map(progressMapper::toDto);
    }

    @Override
    public Mono<ProgressDTO> update(ProgressDTO progressDTO) {
        log.debug("Request to update Progress : {}", progressDTO);
        return progressRepository.save(progressMapper.toEntity(progressDTO)).map(progressMapper::toDto);
    }

    @Override
    public Mono<ProgressDTO> partialUpdate(ProgressDTO progressDTO) {
        log.debug("Request to partially update Progress : {}", progressDTO);

        return progressRepository
            .findById(progressDTO.getId())
            .map(existingProgress -> {
                progressMapper.partialUpdate(existingProgress, progressDTO);

                return existingProgress;
            })
            .flatMap(progressRepository::save)
            .map(progressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ProgressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Progresss");
        return progressRepository.findAllBy(pageable).map(progressMapper::toDto);
    }

    public Mono<Long> countAll() {
        return progressRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ProgressDTO> findOne(Long id) {
        log.debug("Request to get Progress : {}", id);
        return progressRepository.findById(id).map(progressMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Progress : {}", id);
        return progressRepository.deleteById(id);
    }
}
