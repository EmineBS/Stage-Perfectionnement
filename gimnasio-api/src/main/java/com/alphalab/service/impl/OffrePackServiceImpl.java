package com.alphalab.service.impl;

import com.alphalab.domain.OffrePack;
import com.alphalab.domain.User;
import com.alphalab.repository.OffrePackRepository;
import com.alphalab.service.OffrePackService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.OffrePackDTO;
import com.alphalab.service.mapper.OffrePackMapper;
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
 * Service Implementation for managing {@link OffrePack}.
 */
@Service
@Transactional
public class OffrePackServiceImpl implements OffrePackService {

    private final Logger log = LoggerFactory.getLogger(OffrePackServiceImpl.class);

    private final OffrePackRepository packRepository;

    private final OffrePackMapper packMapper;

    private final UserService userService;

    public OffrePackServiceImpl(OffrePackRepository packRepository, OffrePackMapper packMapper, UserService userService) {
        this.packRepository = packRepository;
        this.packMapper = packMapper;
        this.userService = userService;
    }

    @Override
    public Mono<OffrePackDTO> save(OffrePackDTO packDTO) {
        log.debug("Request to save OffrePack : {}", packDTO);
        return packRepository.save(packMapper.toEntity(packDTO)).map(packMapper::toDto);
    }

    @Override
    public Mono<OffrePackDTO> update(OffrePackDTO packDTO) {
        log.debug("Request to update OffrePack : {}", packDTO);
        return packRepository.save(packMapper.toEntity(packDTO)).map(packMapper::toDto);
    }

    @Override
    public Mono<OffrePackDTO> partialUpdate(OffrePackDTO packDTO) {
        log.debug("Request to partially update OffrePack : {}", packDTO);

        return packRepository
            .findById(packDTO.getId())
            .map(existingOffrePack -> {
                packMapper.partialUpdate(existingOffrePack, packDTO);

                return existingOffrePack;
            })
            .flatMap(packRepository::save)
            .map(packMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<OffrePackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OffrePacks");
        return packRepository.findAllBy(pageable).map(packMapper::toDto);
    }

    public Mono<Long> countAll() {
        return packRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<OffrePackDTO> findOne(Long id) {
        log.debug("Request to get OffrePack : {}", id);
        return packRepository.findById(id).map(packMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete OffrePack : {}", id);
        return packRepository.deleteById(id);
    }

    @Override
    public Flux<OffrePackDTO> findAllPacksByGym(Pageable pageable, Long gym_id) {
        return packRepository.findAllPacksByGym(gym_id, pageable.getPageNumber(), pageable.getPageSize()).map(packMapper::toDto);
    }

    @Override
    public Mono<Long> countAllPacksByGym(Long gym_id) {
        return packRepository.countAllPacksByGym(gym_id);
    }
}
