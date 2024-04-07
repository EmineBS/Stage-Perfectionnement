package com.alphalab.service.impl;

import com.alphalab.domain.Criteria;
import com.alphalab.domain.User;
import com.alphalab.repository.CriteriaRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.CriteriaService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.CriteriaDTO;
import com.alphalab.service.mapper.CriteriaMapper;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Criteria}.
 */
@Service
@Transactional
public class CriteriaServiceImpl implements CriteriaService {

    private final Logger log = LoggerFactory.getLogger(CriteriaServiceImpl.class);

    private final CriteriaRepository criteriaRepository;

    private final CriteriaMapper criteriaMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    public CriteriaServiceImpl(
        CriteriaRepository criteriaRepository,
        CriteriaMapper criteriaMapper,
        BadgeService badgeService,
        UserService userService
    ) {
        this.criteriaRepository = criteriaRepository;
        this.criteriaMapper = criteriaMapper;
        this.badgeService = badgeService;
        this.userService = userService;
    }

    @Override
    public Mono<CriteriaDTO> save(CriteriaDTO criteriaDTO) {
        log.debug("Request to save Criteria 133 : {}", criteriaDTO);
        return criteriaRepository.save(criteriaMapper.toEntity(criteriaDTO)).map(criteriaMapper::toDto);
    }

    @Override
    public Mono<CriteriaDTO> update(CriteriaDTO criteriaDTO) {
        log.debug("Request to update Criteria : {}", criteriaDTO);
        return criteriaRepository.save(criteriaMapper.toEntity(criteriaDTO)).map(criteriaMapper::toDto);
    }

    @Override
    public Mono<CriteriaDTO> partialUpdate(CriteriaDTO criteriaDTO) {
        log.debug("Request to partially update Criteria : {}", criteriaDTO);

        return criteriaRepository
            .findById(criteriaDTO.getId())
            .map(existingCriteria -> {
                criteriaMapper.partialUpdate(existingCriteria, criteriaDTO);

                return existingCriteria;
            })
            .flatMap(criteriaRepository::save)
            .map(criteriaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Criterias");
        return criteriaRepository.findAllBy(pageable).map(criteriaMapper::toDto);
    }

    public Mono<Long> countAll() {
        return criteriaRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CriteriaDTO> findOne(Long id) {
        log.debug("Request to get Criteria : {}", id);
        return criteriaRepository.findById(id).map(criteriaMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Criteria : {}", id);
        return criteriaRepository.deleteById(id);
    }

    @Override
    public Flux<CriteriaDTO> findAllByGymId(Pageable pageable, Long gym_id) {
        return criteriaRepository.findByGymId(pageable, gym_id).map(criteriaMapper::toDto);
    }

    @Override
    public Mono<Long> countAllByGymId(Long gym_id) {
        return criteriaRepository.countByGymId(gym_id);
    }

    @Override
    public Flux<CriteriaDTO> findAllOfGym(Long gym_id) {
        return criteriaRepository.findAllOfGym(gym_id).map(criteriaMapper::toDto);
    }
}
