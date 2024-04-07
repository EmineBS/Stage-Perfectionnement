package com.alphalab.service.impl;

import com.alphalab.domain.RelCriteriaProgress;
import com.alphalab.repository.RelCriteriaProgressRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.CriteriaService;
import com.alphalab.service.RelCriteriaProgressService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.CriteriaBadgeProgressDTO;
import com.alphalab.service.dto.ProgressValue;
import com.alphalab.service.dto.RelCriteriaProgressDTO;
import com.alphalab.service.mapper.RelCriteriaProgressMapper;
import java.security.Principal;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link RelCriteriaProgress}.
 */
@Service
@Transactional
public class RelCriteriaProgressServiceImpl implements RelCriteriaProgressService {

    private final Logger log = LoggerFactory.getLogger(RelCriteriaProgressServiceImpl.class);

    private final RelCriteriaProgressRepository relCriteriaProgressRepository;

    private final RelCriteriaProgressMapper relCriteriaProgressMapper;

    private final CriteriaService criteriaService;

    private final UserService userService;
    private final BadgeService badgeService;

    public RelCriteriaProgressServiceImpl(
        RelCriteriaProgressRepository relCriteriaProgressRepository,
        RelCriteriaProgressMapper relCriteriaProgressMapper,
        CriteriaService criteriaService,
        UserService userService,
        BadgeService badgeService
    ) {
        this.relCriteriaProgressRepository = relCriteriaProgressRepository;
        this.relCriteriaProgressMapper = relCriteriaProgressMapper;
        this.criteriaService = criteriaService;
        this.userService = userService;
        this.badgeService = badgeService;
    }

    @Override
    public Mono<RelCriteriaProgressDTO> save(RelCriteriaProgressDTO relCriteriaProgressDTO) {
        log.debug("Request to save RelCriteriaProgress : {}", relCriteriaProgressDTO);
        return relCriteriaProgressRepository
            .save(relCriteriaProgressMapper.toEntity(relCriteriaProgressDTO))
            .map(relCriteriaProgressMapper::toDto);
    }

    @Override
    public Mono<RelCriteriaProgressDTO> update(RelCriteriaProgressDTO relCriteriaProgressDTO) {
        log.debug("Request to update RelCriteriaProgress : {}", relCriteriaProgressDTO);
        return relCriteriaProgressRepository
            .save(relCriteriaProgressMapper.toEntity(relCriteriaProgressDTO))
            .map(relCriteriaProgressMapper::toDto);
    }

    @Override
    public Mono<RelCriteriaProgressDTO> partialUpdate(RelCriteriaProgressDTO relCriteriaProgressDTO) {
        log.debug("Request to partially update RelCriteriaProgress : {}", relCriteriaProgressDTO);

        return relCriteriaProgressRepository
            .findById(relCriteriaProgressDTO.getId())
            .map(existingRelCriteriaProgress -> {
                relCriteriaProgressMapper.partialUpdate(existingRelCriteriaProgress, relCriteriaProgressDTO);

                return existingRelCriteriaProgress;
            })
            .flatMap(relCriteriaProgressRepository::save)
            .map(relCriteriaProgressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RelCriteriaProgressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelCriteriaProgresss");
        return relCriteriaProgressRepository.findAllBy(pageable).map(relCriteriaProgressMapper::toDto);
    }

    public Mono<Long> countAll() {
        return relCriteriaProgressRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RelCriteriaProgressDTO> findOne(Long id) {
        log.debug("Request to get RelCriteriaProgress : {}", id);
        return relCriteriaProgressRepository.findById(id).map(relCriteriaProgressMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete RelCriteriaProgress : {}", id);
        return relCriteriaProgressRepository.deleteById(id);
    }

    @Override
    public Flux<CriteriaBadgeProgressDTO> findAllCriteriaByBadge(Long gymId, Long badgeId) {
        return criteriaService
            .findAllOfGym(gymId)
            .flatMap(criteria -> {
                log.debug("criteria log {}", criteria);
                ModelMapper mapper = new ModelMapper();
                CriteriaBadgeProgressDTO criteriaBadgeProgress = mapper.map(criteria, CriteriaBadgeProgressDTO.class);
                return relCriteriaProgressRepository
                    .findProgresssByBadgeAndCriteria(badgeId, criteria.getName())
                    .flatMap(dataFound -> {
                        ProgressValue progressValue = new ProgressValue();
                        progressValue.setValue(dataFound.getValue());
                        progressValue.setWhen(dataFound.getCreatedDate());
                        return Mono.just(progressValue);
                    })
                    .collectList()
                    .flatMap(data -> {
                        ArrayList<ProgressValue> dataProgress = new ArrayList<>(data);
                        criteriaBadgeProgress.setProgressValues(dataProgress);
                        return Mono.just(criteriaBadgeProgress);
                    });
            });
    }
}
