package com.alphalab.service.impl;

import com.alphalab.domain.Gym;
import com.alphalab.domain.User;
import com.alphalab.domain.enumeration.ExtBadgeDesignationStatus;
import com.alphalab.repository.FeatureRepository;
import com.alphalab.repository.GymRepository;
import com.alphalab.service.*;
import com.alphalab.service.dto.*;
import com.alphalab.service.mapper.FeatureMapper;
import com.alphalab.service.mapper.GymMapper;
import java.security.Principal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
 * Service Implementation for managing {@link Gym}.
 */
@Service
@Transactional
public class GymServiceImpl implements GymService {

    private final Logger log = LoggerFactory.getLogger(GymServiceImpl.class);

    private final GymRepository gymRepository;

    private final GymMapper gymMapper;

    private final FeatureMapper featureMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    private final RelGymFeatureService relGymFeatureService;

    private final FeatureRepository featureRepository;
    private final CalendarService calendarService;

    private final ExtBadgeDesignationService extBadgeDesignationService;

    private MessageSource messageSource;

    public GymServiceImpl(
        GymRepository gymRepository,
        GymMapper gymMapper,
        FeatureMapper featureMapper,
        BadgeService badgeService,
        UserService userService,
        RelGymFeatureService relGymFeatureService,
        FeatureRepository featureRepository,
        CalendarService calendarService,
        ExtBadgeDesignationService extBadgeDesignationService,
        MessageSource messageSource) {
        this.gymRepository = gymRepository;
        this.gymMapper = gymMapper;
        this.featureMapper = featureMapper;
        this.badgeService = badgeService;
        this.userService = userService;
        this.relGymFeatureService = relGymFeatureService;
        this.featureRepository = featureRepository;
        this.calendarService = calendarService;
        this.extBadgeDesignationService = extBadgeDesignationService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<GymDTO> save(GymDTO gymDTO) {
        log.debug("Request to save Gym 133 : {}", gymDTO);
        return gymRepository.save(gymMapper.toEntity(gymDTO)).map(gymMapper::toDto);
    }

    @Override
    public Mono<GymDTO> update(GymDTO gymDTO) {
        log.debug("Request to update Gym : {}", gymDTO);
        return gymRepository.save(gymMapper.toEntity(gymDTO)).map(gymMapper::toDto);
    }

    @Override
    public Mono<GymDTO> partialUpdate(GymDTO gymDTO) {
        log.debug("Request to partially update Gym : {}", gymDTO);

        return gymRepository
            .findById(gymDTO.getId())
            .map(existingGym -> {
                gymMapper.partialUpdate(existingGym, gymDTO);

                return existingGym;
            })
            .flatMap(gymRepository::save)
            .map(gymMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<GymDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gyms");
        return gymRepository.findAllBy(pageable).map(gymMapper::toDto);
    }

    public Mono<Long> countAll() {
        return gymRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<GymDTO> findOne(Long id) {
        log.debug("Request to get Gym and it's features: {}", id);
        return gymRepository
            .findById(id)
            .flatMap(gymFounded ->
                featureRepository
                    .findAllByGymId(gymFounded.getId())
                    .collect(Collectors.toSet())
                    .flatMap(features -> {
                        gymFounded.setFeatures(features);
                        return Mono.just(gymFounded);
                    })
                    .map(gymMapper::toDto)
            );
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Gym : {}", id);
        return gymRepository.deleteById(id);
    }

    @Override
    public Mono<GymDTO> createGym(GymDTO gymDTO) {
        log.debug("Request to createGym Gym : {}", gymDTO);
        return save(gymDTO)
            .flatMap(savedGym -> {
                createBadge(savedGym.getBadgeAmount(), savedGym.getId(), gymDTO.getBadgeDesignation());
                if (gymDTO.getCalendar()) {
                    CalendarDTO calendar = new CalendarDTO();
                    int hourStart = 8;
                    int minute = 0;
                    int hourEnd = 20;
                    LocalTime startHour = LocalTime.of(hourStart, minute);
                    LocalTime endHour = LocalTime.of(hourEnd, minute);
                    List<Integer> offDays = new ArrayList<>(Arrays.asList(0));
                    calendar.setGymId(savedGym.getId());
                    calendar.setStartHour(startHour);
                    calendar.setEndHour(endHour);
                    calendar.setUnit(30);
                    calendar.setOffDays(offDays);
                    calendar.setEnabled(true);
                    calendarService
                        .save(calendar)
                        .subscribe(savedCalendar -> {
                            log.debug("saved calendar {}", savedCalendar);
                            RelGymFeatureDTO relGymFeature = new RelGymFeatureDTO();
                            relGymFeature.setFeatureId(333L);
                            relGymFeature.setGymId(savedGym.getId());
                            relGymFeatureService
                                .save(relGymFeature)
                                .subscribe(savedRel -> log.debug("saved relation feature calendar{}", savedRel));
                        });
                }
                if (gymDTO.getBadgeDesignation()) {
                    RelGymFeatureDTO relGymFeature = new RelGymFeatureDTO();
                    relGymFeature.setFeatureId(222L);
                    relGymFeature.setGymId(savedGym.getId());
                    relGymFeatureService.save(relGymFeature).subscribe(savedRel -> log.debug("saved relation designation{}", savedRel));
                }
                if (gymDTO.getProgressMonitoring()) {
                    RelGymFeatureDTO relGymFeature = new RelGymFeatureDTO();
                    relGymFeature.setFeatureId(444L);
                    relGymFeature.setGymId(savedGym.getId());
                    relGymFeatureService
                        .save(relGymFeature)
                        .subscribe(savedRel -> log.debug("saved relation Progress Monitoring {}", savedRel));
                }

                return Mono.just(savedGym);
            });
    }

    public void createBadge(int badgeAmount, Long gymId, Boolean badgeDesig) {
        for (int i = 0; i < badgeAmount; i++) {
            BadgeDTO badgeDTO = new BadgeDTO();
            UUID uuid = UUID.randomUUID();
            log.debug("generated uid : {}", uuid);
            badgeDTO.setUid(uuid.toString());
            badgeDTO.setGymId(gymId);
            badgeService
                .save(badgeDTO)
                .subscribe(data -> {
                    log.debug("saved new badge : {}", data);
                    if (badgeDesig) {
                        ExtBadgeDesignationDTO extBadgeDesignation = new ExtBadgeDesignationDTO();
                        extBadgeDesignation.setBadgeId(data.getId());
                        extBadgeDesignation.setStatus(ExtBadgeDesignationStatus.NOTASSIGNED);
                        extBadgeDesignationService.save(extBadgeDesignation).subscribe(extData -> log.debug("saved extended: {}", extData));
                    }
                });
        }
    }

    @Override
    public Flux<GymDTO> findAllGymsByUser(Pageable pageable, Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return gymRepository
                .findAllGymsByUser(user.getLogin(), pageable.getPageNumber(), pageable.getPageSize())
                .flatMap(data -> {
                    log.debug("returned data {}", data);
                    return Mono.just(data);
                })
                .map(gymMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Mono<Long> countAllGymsByUser(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return gymRepository.countAllGymsByUser(user.getLogin());
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Mono<GymDTO> findGymByBadgeUID(String uid) {
        return gymRepository.findGymByBadgeUID(uid).map(gymMapper::toDto);
    }

    @Override
    public Mono<GymDTO> findGymOfCurrentBadgeAccount(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return gymRepository.findGymByCurrentBadgeAccount(user.getLogin()).map(gymMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }
}
