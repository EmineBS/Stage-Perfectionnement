package com.alphalab.service.impl;

import com.alphalab.domain.RDV;
import com.alphalab.domain.User;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.repository.RDVRepository;
import com.alphalab.service.*;
import com.alphalab.service.dto.RDVDTO;
import com.alphalab.service.mapper.RDVMapper;
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
 * Service Implementation for managing {@link RDV}.
 */
@Service
@Transactional
public class RDVServiceImpl implements RDVService {

    private final Logger log = LoggerFactory.getLogger(RDVServiceImpl.class);

    private final RDVRepository rdvRepository;

    private final RDVMapper rdvMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    private final RelBadgePackService relBadgePackService;

    private final ProfileService profileService;

    private MessageSource messageSource;

    public RDVServiceImpl(
        RDVRepository rdvRepository,
        RDVMapper rdvMapper,
        BadgeService badgeService,
        UserService userService,
        RelBadgePackService relBadgePackService,
        ProfileService profileService,
        MessageSource messageSource) {
        this.rdvRepository = rdvRepository;
        this.rdvMapper = rdvMapper;
        this.badgeService = badgeService;
        this.userService = userService;
        this.relBadgePackService = relBadgePackService;
        this.profileService = profileService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<RDVDTO> save(RDVDTO rdvDTO) {
        log.debug("Request to save RDV 133 : {}", rdvDTO);
        return rdvRepository.save(rdvMapper.toEntity(rdvDTO)).map(rdvMapper::toDto);
    }

    @Override
    public Mono<RDVDTO> update(RDVDTO rdvDTO) {
        log.debug("Request to update RDV : {}", rdvDTO);
        return rdvRepository.save(rdvMapper.toEntity(rdvDTO)).map(rdvMapper::toDto);
    }

    @Override
    public Mono<RDVDTO> partialUpdate(RDVDTO rdvDTO) {
        log.debug("Request to partially update RDV : {}", rdvDTO);

        return rdvRepository
            .findById(rdvDTO.getId())
            .map(existingRDV -> {
                rdvMapper.partialUpdate(existingRDV, rdvDTO);

                return existingRDV;
            })
            .flatMap(rdvRepository::save)
            .map(rdvMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RDVDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RDVs");
        return rdvRepository.findAllBy(pageable).map(rdvMapper::toDto);
    }

    public Mono<Long> countAll() {
        return rdvRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RDVDTO> findOne(Long id) {
        log.debug("Request to get RDV : {}", id);
        return rdvRepository.findById(id).map(rdvMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete RDV : {}", id);
        return rdvRepository.deleteById(id);
    }

    @Override
    public Flux<RDVDTO> findAllByBadge(Pageable pageable, Long badge_id) {
        return rdvRepository.findAllByBadge(badge_id, pageable).map(rdvMapper::toDto);
    }

    @Override
    public Mono<Long> countAllByBadge(Long badge_id) {
        return rdvRepository.countAllByBadge(badge_id);
    }

    @Override
    public Mono<Long> countAllByBadgeAndStatusIsConfirmed(Long badge_id) {
        return rdvRepository.countAllByBadgeAndStatusIsConfirmed(badge_id);
    }

    @Override
    public Mono<RDV> getRDVBookedToday(Long badge_id) {
        return rdvRepository.findRDVBookedToday(badge_id);
    }

    @Override
    public Flux<RDVDTO> findAllByGym(Pageable pageable, Long gym_id) {
        log.debug("gym id >> {}", gym_id);
        rdvRepository.findAllByGym(gym_id, pageable).subscribe(data -> log.debug("founded rdv {}", data));
        return rdvRepository.findAllByGym(gym_id, pageable).map(rdvMapper::toDto);
    }

    @Override
    public Mono<Long> countAllByGym(Long gym_id) {
        return rdvRepository.countAllByGym(gym_id);
    }

    @Override
    public Flux<RDVDTO> findAllOfCurrentBadgeAccount(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return rdvRepository.findAllOfCurrentBadgeAccount(user.getLogin()).map(rdvMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Flux<RDVDTO> findAllExceptOfCurrentBadgeAccount(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return rdvRepository.findAllExceptOfCurrentBadgeAccount(user.getLogin()).map(rdvMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Flux<RDVDTO> findAllOfCurrentCalendarBadge(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return rdvRepository.findAllOfCalendarBadge(user.getLogin()).map(rdvMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Mono<RDVDTO> createRDV(RDVDTO rdvDTO, Principal principal) throws Exception {
        return profileService
            .findCurrentProfile(principal)
            .flatMap(profileFound ->
                relBadgePackService
                    .findRelBadgePackByBadge(profileFound.getBadgeId())
                    .flatMap(relFound -> {
                        if (relFound.getStatus() == BadgePackStatus.ACTIVE) {
                            return countAllByBadgeAndStatusIsConfirmed(relFound.getBadge().getId())
                                .flatMap(countRDV -> {
                                    if (countRDV < relFound.getPack().getWorkoutSessions()) {
                                        rdvDTO.setRelBadgePackId(relFound.getId());
                                        return rdvRepository
                                            .checkIfThereIsAlreadyBookedVisitAtAGivenDate(relFound.getBadge().getId(), rdvDTO.getFromDate())
                                            .flatMap(checkCount -> {
                                                if (checkCount == 0) return save(rdvDTO); else return Mono.error(
                                                    new RuntimeException(messageSource.getMessage("error.rdv.exist", null, LocaleContextHolder.getLocale()))
                                                );
                                            });
                                    } else return Mono.error(new RuntimeException(messageSource.getMessage("error.rdv.maxReached", null, LocaleContextHolder.getLocale())));
                                });
                        } else return Mono.error(new RuntimeException(messageSource.getMessage("error.rdv.noPack", null, LocaleContextHolder.getLocale())));
                    })
                    .switchIfEmpty(Mono.error(new RuntimeException(messageSource.getMessage("error.rdv.noPack", null, LocaleContextHolder.getLocale()))))
            );
    }
}
