package com.alphalab.service.impl;

import com.alphalab.domain.ExtBadgeDesignation;
import com.alphalab.repository.ExtBadgeDesignationRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.ExtBadgeDesignationService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.ExtBadgeDesignationDTO;
import com.alphalab.service.mapper.ExtBadgeDesignationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ExtBadgeDesignation}.
 */
@Service
@Transactional
public class ExtBadgeDesignationServiceImpl implements ExtBadgeDesignationService {

    private final Logger log = LoggerFactory.getLogger(ExtBadgeDesignationServiceImpl.class);

    private final ExtBadgeDesignationRepository extBadgeDesignationRepository;

    private final ExtBadgeDesignationMapper extBadgeDesignationMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    public ExtBadgeDesignationServiceImpl(
        ExtBadgeDesignationRepository extBadgeDesignationRepository,
        ExtBadgeDesignationMapper extBadgeDesignationMapper,
        BadgeService badgeService,
        UserService userService
    ) {
        this.extBadgeDesignationRepository = extBadgeDesignationRepository;
        this.extBadgeDesignationMapper = extBadgeDesignationMapper;
        this.badgeService = badgeService;
        this.userService = userService;
    }

    @Override
    public Mono<ExtBadgeDesignationDTO> save(ExtBadgeDesignationDTO extBadgeDesignationDTO) {
        log.debug("Request to save ExtBadgeDesignation 133 : {}", extBadgeDesignationDTO);
        return extBadgeDesignationRepository
            .save(extBadgeDesignationMapper.toEntity(extBadgeDesignationDTO))
            .map(extBadgeDesignationMapper::toDto);
    }

    @Override
    public Mono<ExtBadgeDesignationDTO> update(ExtBadgeDesignationDTO extBadgeDesignationDTO) {
        log.debug("Request to update ExtBadgeDesignation : {}", extBadgeDesignationDTO);
        return extBadgeDesignationRepository
            .save(extBadgeDesignationMapper.toEntity(extBadgeDesignationDTO))
            .map(extBadgeDesignationMapper::toDto);
    }

    @Override
    public Mono<ExtBadgeDesignationDTO> partialUpdate(ExtBadgeDesignationDTO extBadgeDesignationDTO) {
        log.debug("Request to partially update ExtBadgeDesignation : {}", extBadgeDesignationDTO);

        return extBadgeDesignationRepository
            .findById(extBadgeDesignationDTO.getId())
            .map(existingExtBadgeDesignation -> {
                extBadgeDesignationMapper.partialUpdate(existingExtBadgeDesignation, extBadgeDesignationDTO);

                return existingExtBadgeDesignation;
            })
            .flatMap(extBadgeDesignationRepository::save)
            .map(extBadgeDesignationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ExtBadgeDesignationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExtBadgeDesignations");
        return extBadgeDesignationRepository.findAllBy(pageable).map(extBadgeDesignationMapper::toDto);
    }

    public Mono<Long> countAll() {
        return extBadgeDesignationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ExtBadgeDesignationDTO> findOne(Long id) {
        log.debug("Request to get ExtBadgeDesignation : {}", id);
        return extBadgeDesignationRepository.findById(id).map(extBadgeDesignationMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete ExtBadgeDesignation : {}", id);
        return extBadgeDesignationRepository.deleteById(id);
    }

    @Override
    public Mono<ExtBadgeDesignationDTO> findExtBadgeDesignationByBadge(Long badge_id) {
        return extBadgeDesignationRepository.findByBadge(badge_id).map(extBadgeDesignationMapper::toDto);
    }
}
