package com.alphalab.service.impl;

import com.alphalab.domain.Calendar;
import com.alphalab.domain.User;
import com.alphalab.repository.CalendarRepository;
import com.alphalab.service.BadgeService;
import com.alphalab.service.CalendarService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.CalendarDTO;
import com.alphalab.service.mapper.CalendarMapper;
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
 * Service Implementation for managing {@link Calendar}.
 */
@Service
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);

    private final CalendarRepository calendarRepository;

    private final CalendarMapper calendarMapper;

    private final BadgeService badgeService;
    private final UserService userService;

    private MessageSource messageSource;

    public CalendarServiceImpl(
        CalendarRepository calendarRepository,
        CalendarMapper calendarMapper,
        BadgeService badgeService,
        UserService userService,
        MessageSource messageSource) {
        this.calendarRepository = calendarRepository;
        this.calendarMapper = calendarMapper;
        this.badgeService = badgeService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<CalendarDTO> save(CalendarDTO calendarDTO) {
        log.debug("Request to save Calendar 133 : {}", calendarDTO);
        return calendarRepository.save(calendarMapper.toEntity(calendarDTO)).map(calendarMapper::toDto);
    }

    @Override
    public Mono<CalendarDTO> update(CalendarDTO calendarDTO) {
        log.debug("Request to update Calendar : {}", calendarDTO);
        return calendarRepository.save(calendarMapper.toEntity(calendarDTO)).map(calendarMapper::toDto);
    }

    @Override
    public Mono<CalendarDTO> partialUpdate(CalendarDTO calendarDTO) {
        log.debug("Request to partially update Calendar : {}", calendarDTO);

        return calendarRepository
            .findById(calendarDTO.getId())
            .map(existingCalendar -> {
                calendarMapper.partialUpdate(existingCalendar, calendarDTO);

                return existingCalendar;
            })
            .flatMap(calendarRepository::save)
            .map(calendarMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CalendarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Calendars");
        return calendarRepository.findAllBy(pageable).map(calendarMapper::toDto);
    }

    public Mono<Long> countAll() {
        return calendarRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CalendarDTO> findOne(Long id) {
        log.debug("Request to get Calendar : {}", id);
        return calendarRepository.findById(id).map(calendarMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Calendar : {}", id);
        return calendarRepository.deleteById(id);
    }

    @Override
    public Mono<CalendarDTO> findCalendarByGym(Long gym_id) {
        return calendarRepository.findCalendarByGym(gym_id).map(calendarMapper::toDto);
    }

    @Override
    public Mono<CalendarDTO> findOneOfCurrentBadge(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return calendarRepository
                .findOneOfCurrentBadgeSession(user.getLogin())
                .map(calendarMapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException(messageSource.getMessage("error.calendar.exist", null, LocaleContextHolder.getLocale()))));
        } else {
            throw new Exception("Could not found user");
        }
    }
}
