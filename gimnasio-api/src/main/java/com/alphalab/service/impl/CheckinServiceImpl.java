package com.alphalab.service.impl;

import com.alphalab.domain.CheckIn;
import com.alphalab.domain.OffrePack;
import com.alphalab.domain.RelBadgePack;
import com.alphalab.domain.User;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.domain.enumeration.CheckinStatus;
import com.alphalab.domain.enumeration.RDVStatus;
import com.alphalab.repository.CheckinRepository;
import com.alphalab.repository.RelBadgePackRepository;
import com.alphalab.service.*;
import com.alphalab.service.dto.CheckinDTO;
import com.alphalab.service.dto.CheckinResultDTO;
import com.alphalab.service.mapper.CheckinMapper;
import com.alphalab.service.mapper.RDVMapper;
import com.alphalab.service.mapper.RelBadgePackMapper;
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
 * Service Implementation for managing {@link CheckIn}.
 */
@Service
@Transactional
public class CheckinServiceImpl implements CheckinService {

    private final Logger log = LoggerFactory.getLogger(CheckinServiceImpl.class);

    private final CheckinRepository CheckinRepository;

    private final RelBadgePackRepository relBadgePackRepository;

    private final CheckinMapper CheckinMapper;

    private final UserService userService;

    private final RelUserGymService relUserGymService;

    private final RelBadgePackService relBadgePackService;

    private final RelBadgePackMapper relBadgePackMapper;

    private final RDVService rdvService;

    private final RDVMapper rdvMapper;

    private MessageSource messageSource;

    public CheckinServiceImpl(
        CheckinRepository CheckinRepository,
        RelBadgePackRepository relBadgePackRepository,
        CheckinMapper CheckinMapper,
        UserService userService,
        RelUserGymService relUserGymService,
        RelBadgePackService relBadgePackService,
        RelBadgePackMapper relBadgePackMapper,
        RDVService rdvService,
        RDVMapper rdvMapper,
        MessageSource messageSource) {
        this.CheckinRepository = CheckinRepository;
        this.relBadgePackRepository = relBadgePackRepository;
        this.CheckinMapper = CheckinMapper;
        this.userService = userService;
        this.relUserGymService = relUserGymService;
        this.relBadgePackService = relBadgePackService;
        this.relBadgePackMapper = relBadgePackMapper;
        this.rdvService = rdvService;
        this.rdvMapper = rdvMapper;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<CheckinDTO> save(CheckinDTO checkinDTO, Principal principal) throws Exception {
        log.debug("Request to save Checkin : {}", checkinDTO);
        CheckIn checkIn = CheckinMapper.toEntity(checkinDTO);
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
                checkIn.setUserId(user.getLogin());
            } else {
                throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
            }
        }
        return CheckinRepository.save(checkIn).map(CheckinMapper::toDto);
    }

    @Override
    public Mono<CheckinDTO> update(CheckinDTO checkinDTO, Principal principal) throws Exception {
        log.debug("Request to update Checkin : {}", checkinDTO);
        CheckIn checkIn = CheckinMapper.toEntity(checkinDTO);
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
                checkIn.setUserId(user.getLogin());
            } else {
                throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
            }
        }
        return CheckinRepository.save(checkIn).map(CheckinMapper::toDto);
    }

    @Override
    public Mono<CheckinDTO> partialUpdate(CheckinDTO CheckinDTO) {
        log.debug("Request to partially update Checkin : {}", CheckinDTO);

        return CheckinRepository
            .findById(CheckinDTO.getId())
            .map(existingCheckin -> {
                CheckinMapper.partialUpdate(existingCheckin, CheckinDTO);
                return existingCheckin;
            })
            .flatMap(CheckinRepository::save)
            .map(CheckinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CheckinDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Checkins");
        return CheckinRepository.findAllBy(pageable).map(CheckinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CheckinDTO> findAllByUser(Pageable pageable, Principal principal) throws Exception {
        log.debug("Request to get all Checkins by user");

        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
                return CheckinRepository.findAllByUserId(pageable, user.getLogin()).map(CheckinMapper::toDto);
            }
        }
        throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
    }

    public Mono<Long> countAll() {
        return CheckinRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CheckinDTO> findOne(Long id) {
        log.debug("Request to get Checkin : {}", id);
        return CheckinRepository.findById(id).map(CheckinMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Checkin : {}", id);
        return CheckinRepository.deleteById(id);
    }

    @Override
    public Mono<Long> countAllCheckinOfBadge(String badge_uid, Long pack_id) {
        return CheckinRepository.countAllCheckinOfBadge(badge_uid, pack_id);
    }

    @Override
    public Mono<Long> countAllCheckinByUser(Principal principal) throws Exception {
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
                return CheckinRepository.countAllCheckinByUser(user.getLogin());
            } else {
                throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
            }
        }
        throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
    }

    @Override
    public Mono<Long> countAllCheckinOfRelBadgePackId(Long relBadgePackId) {
        return CheckinRepository.countAllCheckinOfRelBadgePack(relBadgePackId);
    }

    @Override
    public Mono<CheckinResultDTO> checkinByBadge(String badge_uid, Principal principal) throws Exception {
        //first check rel between user and gym
        return relUserGymService
            .checkRelBetweenUserAndGym(badge_uid, principal)
            .hasElement()
            .flatMap(dataFound -> {
                if (
                    dataFound
                ) return relBadgePackRepository //second get pack
                    .findActiveByRelBadgePackId(badge_uid)
                    .flatMap(relBadgePack ->
                        countAllCheckinOfRelBadgePackId(relBadgePack.getId())
                            .flatMap(count -> {
                                log.debug("relBadgePack {}: pack {}: count {}:", relBadgePack.getId(), relBadgePack.getPackId(), count);
                                CheckinDTO checkinDTO = new CheckinDTO();
                                OffrePack pack = relBadgePack.getPack();
                                Long remaining = pack.getWorkoutSessions() - count;

                                checkinDTO.setBadgeUid(badge_uid);
                                checkinDTO.setRelBadgePackId(relBadgePack.getId());

                                //can checkin again
                                if (pack.getWorkoutSessions() > count) {
                                    if (pack.getAutoConfirm()) {
                                        //check rdv enabled
                                        if (pack.getRdvEnabled()) {
                                            log.debug("pack rdv is enabled: {}", pack.getRdvEnabled());
                                            //get the closest rdv
                                            Long finalRemaining = remaining;
                                            return rdvService
                                                .getRDVBookedToday(relBadgePack.getBadgeId())
                                                .flatMap(rdvFound -> {
                                                    checkinDTO.setStatus(CheckinStatus.CONFIRMED);
                                                    Long finalRemainingDec = finalRemaining - 1;
                                                    rdvFound.setStatus(RDVStatus.DONE);

                                                    rdvService
                                                        .update(rdvMapper.toDto(rdvFound))
                                                        .subscribe(data -> log.debug("update rdv works!! {}", data));
                                                    if (finalRemainingDec == 0) {
                                                        relBadgePack.setStatus(BadgePackStatus.FINISHED);
                                                        relBadgePackService
                                                            .update(relBadgePackMapper.toDto(relBadgePack))
                                                            .subscribe(relBP -> log.debug("updated badge pack relation: {}", relBP));
                                                    }
                                                    return checkinResult(checkinDTO, principal, relBadgePack, count, finalRemainingDec);
                                                })
                                                .switchIfEmpty(
                                                    Mono.error(
                                                        new RuntimeException(messageSource.getMessage("error.checkin.rdv", null, LocaleContextHolder.getLocale()))
                                                    )
                                                );
                                        } else {
                                            checkinDTO.setStatus(CheckinStatus.CONFIRMED);
                                            remaining -= 1;
                                            if (remaining == 0) {
                                                relBadgePack.setStatus(BadgePackStatus.FINISHED);
                                                relBadgePackService
                                                    .update(relBadgePackMapper.toDto(relBadgePack))
                                                    .subscribe(relBP -> log.debug("updated badge pack relation: {}", relBP));
                                            }
                                        }
                                    } else {
                                        checkinDTO.setStatus(CheckinStatus.PENDING);
                                    }
                                    return checkinResult(checkinDTO, principal, relBadgePack, count, remaining);
                                }
                                //can't checkin
                                else {
                                    checkinDTO.setStatus(CheckinStatus.REFUSED);
                                    return checkinResult(checkinDTO, principal, relBadgePack, count, 0L);
                                }
                            })
                    )
                    .switchIfEmpty(Mono.error(new RuntimeException(messageSource.getMessage("error.checkin.relBadgePack", null, LocaleContextHolder.getLocale())))); else return Mono.error(
                    new RuntimeException(messageSource.getMessage("error.checkin.relGymUser", null, LocaleContextHolder.getLocale()))
                );
            });
    }

    public Mono<CheckinResultDTO> checkinResult(
        CheckinDTO checkinDTO,
        Principal principal,
        RelBadgePack relBadgePack,
        Long count,
        Long checkinLeft
    ) {
        try {
            return save(checkinDTO, principal)
                .flatMap(data -> {
                    data.setBadgeId(relBadgePack.getBadge().getId());
                    data.setBadgeUid(relBadgePack.getBadge().getUid());
                    data.setPackId(relBadgePack.getPack().getId());
                    data.setPackName(relBadgePack.getPack().getName());
                    data.setPackWorkoutSessions(new Long(relBadgePack.getPack().getWorkoutSessions()));
                    CheckinResultDTO checkinResultDTO = new CheckinResultDTO(data, count, checkinLeft);
                    return Mono.just(checkinResultDTO);
                });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Flux<CheckinDTO> findAllByBadgeUID(Pageable pageable, String badge_uid) {
        return CheckinRepository.findAllByBadgeUID(pageable, badge_uid).map(CheckinMapper::toDto);
    }

    @Override
    public Mono<Long> countAllByBadgeUID(String badge_uid) {
        return CheckinRepository.countAllByBadgeUID(badge_uid);
    }

    @Override
    public Flux<CheckinDTO> findAllByGym(Pageable pageable, Long gym_id) {
        return CheckinRepository.findAllByGym(pageable, gym_id).map(CheckinMapper::toDto);
    }

    @Override
    public Mono<Long> countAllByGym(Long gym_id) {
        return CheckinRepository.countAllByGym(gym_id);
    }

    @Override
    public Mono<CheckinResultDTO> confirmCheckin(CheckinDTO checkinDTO, Principal principal) throws Exception {
        return update(checkinDTO, principal)
            .flatMap(data ->
                countAllCheckinOfRelBadgePackId(data.getRelBadgePackId())
                    .flatMap(count -> {
                        data.setBadgeId(checkinDTO.getBadgeId());
                        data.setBadgeUid(checkinDTO.getBadgeUid());
                        data.setPackId(checkinDTO.getPackId());
                        data.setPackName(checkinDTO.getPackName());
                        data.setPackWorkoutSessions(checkinDTO.getPackWorkoutSessions());
                        log.debug("CheckinDTO {}", data);
                        CheckinResultDTO checkinResultDTO = new CheckinResultDTO(data, count, data.getPackWorkoutSessions() - count);
                        return Mono.just(checkinResultDTO);
                    })
            );
    }
}
