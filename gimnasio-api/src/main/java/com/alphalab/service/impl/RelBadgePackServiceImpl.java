package com.alphalab.service.impl;

import com.alphalab.domain.RelBadgePack;
import com.alphalab.repository.CheckinRepository;
import com.alphalab.repository.RelBadgePackRepository;
import com.alphalab.service.RelBadgePackService;
import com.alphalab.service.dto.RelBadgePackDTO;
import com.alphalab.service.dto.RelBadgePackFullDTO;
import com.alphalab.service.dto.RelBadgePackRSDTO;
import com.alphalab.service.mapper.RelBadgePackFullMapper;
import com.alphalab.service.mapper.RelBadgePackMapper;
import com.alphalab.service.mapper.RelBadgePackRSMapper;
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
 * Service Implementation for managing {@link RelBadgePack}.
 */
@Service
@Transactional
public class RelBadgePackServiceImpl implements RelBadgePackService {

    private final Logger log = LoggerFactory.getLogger(RelBadgePackServiceImpl.class);

    private final RelBadgePackRepository relBadgePackRepository;

    private final RelBadgePackMapper relBadgePackMapper;

    private final RelBadgePackRSMapper relBadgePackRSMapper;
    private final RelBadgePackFullMapper relBadgePackFullMapper;

    private final CheckinRepository checkinRepository;

    private MessageSource messageSource;

    public RelBadgePackServiceImpl(
        RelBadgePackRepository relBadgePackRepository,
        RelBadgePackMapper relBadgePackMapper,
        RelBadgePackRSMapper relBadgePackRSMapper,
        RelBadgePackFullMapper relBadgePackFullMapper,
        CheckinRepository checkinRepository,
        MessageSource messageSource) {
        this.relBadgePackRepository = relBadgePackRepository;
        this.relBadgePackMapper = relBadgePackMapper;
        this.relBadgePackRSMapper = relBadgePackRSMapper;
        this.relBadgePackFullMapper = relBadgePackFullMapper;
        this.checkinRepository = checkinRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<RelBadgePackDTO> save(RelBadgePackDTO relBadgePackDTO) {
        log.debug("Request to save RelBadgePack : {}", relBadgePackDTO);
        return relBadgePackRepository.save(relBadgePackMapper.toEntity(relBadgePackDTO)).map(relBadgePackMapper::toDto);
    }

    @Override
    public Mono<RelBadgePackDTO> update(RelBadgePackDTO relBadgePackDTO) {
        log.debug("Request to update RelBadgePack : {}", relBadgePackDTO);
        return relBadgePackRepository.save(relBadgePackMapper.toEntity(relBadgePackDTO)).map(relBadgePackMapper::toDto);
    }

    @Override
    public Mono<RelBadgePackDTO> partialUpdate(RelBadgePackDTO relBadgePackDTO) {
        log.debug("Request to partially update RelBadgePack : {}", relBadgePackDTO);

        return relBadgePackRepository
            .findById(relBadgePackDTO.getId())
            .map(existingRelBadgePack -> {
                relBadgePackMapper.partialUpdate(existingRelBadgePack, relBadgePackDTO);

                return existingRelBadgePack;
            })
            .flatMap(relBadgePackRepository::save)
            .map(relBadgePackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RelBadgePackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelBadgePacks");
        return relBadgePackRepository.findAllBy(pageable).map(relBadgePackMapper::toDto);
    }

    public Mono<Long> countAll() {
        return relBadgePackRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RelBadgePackDTO> findOne(Long id) {
        log.debug("Request to get RelBadgePack : {}", id);
        return relBadgePackRepository.findById(id).map(relBadgePackMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete RelBadgePack : {}", id);
        return relBadgePackRepository.deleteById(id);
    }

    @Override
    public Flux<RelBadgePackDTO> findAllPacksByBadge(Pageable pageable, Long badge_id) {
        return relBadgePackRepository
            .findAllPacksByBadge(badge_id, pageable)
            .flatMap(data -> {
                log.debug("this is returned data {}", data);
                return Mono.just(data);
            })
            .map(relBadgePackMapper::toDto);
    }

    @Override
    public Flux<RelBadgePackRSDTO> findAllPacksByBadgeRS(Pageable pageable, Long badge_id) {
        return relBadgePackRepository
            .findAllPacksByBadge(badge_id, pageable)
            .map(relBadgePackRSMapper::toDto)
            .flatMap(data -> {
                log.debug("badge data 0000 :{}", data);
                if (data.getPack().getWorkoutSessions() != null) return checkinRepository
                    .countAllCheckinOfRelBadgePack(data.getId())
                    .flatMap(count -> {
                        log.debug("checkin count  :{}", count);
                        data.setRemainingSessions(new Long(data.getPack().getWorkoutSessions() - count));
                        return Mono.just(data);
                    }); else return Mono.just(data);
            });
    }

    @Override
    public Mono<RelBadgePackDTO> assignPackToBadge(RelBadgePackDTO relBadgePackDTO) {
        log.debug("Request to assign pack to badge : {}", relBadgePackDTO);
        return relBadgePackRepository
            .findRelBadgePackWherePackIsActive(relBadgePackDTO.getBadge().getId())
            .hasElement()
            .flatMap(hasData -> {
                if (hasData) {
                    return Mono.error(new RuntimeException(messageSource.getMessage("error.relBadgePack", null, LocaleContextHolder.getLocale())));
                } else {
                    return save(relBadgePackDTO);
                }
            });
    }

    @Override
    public Flux<RelBadgePackDTO> findRelBadgePackByGym(Long gym_id, Pageable pageable) {
        return relBadgePackRepository.findRelBadgePackByGym(gym_id, pageable).map(relBadgePackMapper::toDto);
    }

    @Override
    public Flux<RelBadgePackFullDTO> findAllBadgesByGym(Long gym_id, Pageable pageable) {
        return relBadgePackRepository
            .findAllBadgesWithOrWithoutPack(gym_id, pageable)
            .map(relBadgePackFullMapper::toDto)
            .flatMap(data -> {
                log.debug("badge data 0000 :{}", data);
                if (data.getPackWorkoutSessions() != null) return checkinRepository
                    .countAllCheckinOfRelBadgePack(data.getRelBadgePackId())
                    .flatMap(count -> {
                        log.debug("checkin count  :{}", count);
                        data.setRemainingSessions(new Long(data.getPackWorkoutSessions() - count));
                        return Mono.just(data);
                    }); else return Mono.just(data);
            });
    }

    @Override
    public Mono<Long> countAllBadgesByGym(Long gym_id) {
        return relBadgePackRepository.countAllBadgesWithOrWithoutPack(gym_id);
    }

    @Override
    public Mono<RelBadgePackDTO> findRelBadgePackByBadge(Long badge_id) {
        return relBadgePackRepository.findRelBadgePackByBadge(badge_id).map(relBadgePackMapper::toDto);
    }

    @Override
    public Mono<RelBadgePackDTO> activateBadgePack(RelBadgePackDTO relBadgePackDTO) {
        log.debug("Request to activate RelBadgePack : {}", relBadgePackDTO);
        return relBadgePackRepository
            .findRelOfBadgeWhereStatusIsActive(relBadgePackDTO.getBadge().getId())
            .hasElement()
            .flatMap(dataFound -> {
                if (dataFound) return Mono.error(
                    new RuntimeException(messageSource.getMessage("error.checkin.rdv", null, LocaleContextHolder.getLocale()))
                ); else return update(relBadgePackDTO);
            });
    }
}
