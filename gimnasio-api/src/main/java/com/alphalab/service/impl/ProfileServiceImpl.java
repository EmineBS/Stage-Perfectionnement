package com.alphalab.service.impl;

import com.alphalab.domain.Profile;
import com.alphalab.domain.User;
import com.alphalab.domain.enumeration.ExtBadgeDesignationStatus;
import com.alphalab.domain.enumeration.KeycloakRoles;
import com.alphalab.repository.ProfileRepository;
import com.alphalab.service.ExtBadgeDesignationService;
import com.alphalab.service.KeycloakService;
import com.alphalab.service.ProfileService;
import com.alphalab.service.UserService;
import com.alphalab.service.dto.ProfileDTO;
import com.alphalab.service.dto.UserDTO;
import com.alphalab.service.mapper.ProfileMapper;
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
 * Service Implementation for managing {@link Profile}.
 */
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    private final KeycloakService keycloakService;

    private final UserService userService;

    private final ExtBadgeDesignationService extBadgeDesignationService;

    private MessageSource messageSource;

    public ProfileServiceImpl(
        ProfileRepository profileRepository,
        ProfileMapper profileMapper,
        KeycloakService keycloakService,
        UserService userService,
        ExtBadgeDesignationService extBadgeDesignationService,
        MessageSource messageSource) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.keycloakService = keycloakService;
        this.userService = userService;
        this.extBadgeDesignationService = extBadgeDesignationService;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<ProfileDTO> save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile 133 : {}", profileDTO);
        profileDTO.setEmail(profileDTO.getEmail().toLowerCase());
        return profileRepository
            .save(profileMapper.toEntity(profileDTO))
            .flatMap(userSaved -> {
                UserDTO user = new UserDTO();
                user.setLogin(userSaved.getEmail());
                user.setEmail(userSaved.getEmail());
                user.setPassword("0000");
                keycloakService.createUser(user, KeycloakRoles.ROLE_BADGE, "Badges");
                extBadgeDesignationService
                    .findExtBadgeDesignationByBadge(userSaved.getBadgeId())
                    .subscribe(extFound -> {
                        if (extFound != null) {
                            log.debug("founded ext to update: {}", extFound);
                            extFound.setStatus(ExtBadgeDesignationStatus.ASSIGNED);
                            extFound.setUserId(userSaved.getEmail());
                            extBadgeDesignationService
                                .update(extFound)
                                .subscribe(updatedData -> log.debug("ext have been updated: {}", updatedData));
                        } else Mono.error(new RuntimeException(messageSource.getMessage("error.checkin.relBadgePack", null, LocaleContextHolder.getLocale())));
                    });
                return Mono.just(userSaved);
            })
            .map(profileMapper::toDto);
    }

    @Override
    public Mono<ProfileDTO> update(ProfileDTO profileDTO) {
        log.debug("Request to update Profile : {}", profileDTO);
        return profileRepository.save(profileMapper.toEntity(profileDTO)).map(profileMapper::toDto);
    }

    @Override
    public Mono<ProfileDTO> partialUpdate(ProfileDTO profileDTO) {
        log.debug("Request to partially update Profile : {}", profileDTO);

        return profileRepository
            .findById(profileDTO.getId())
            .map(existingProfile -> {
                profileMapper.partialUpdate(existingProfile, profileDTO);

                return existingProfile;
            })
            .flatMap(profileRepository::save)
            .map(profileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAllBy(pageable).map(profileMapper::toDto);
    }

    public Mono<Long> countAll() {
        return profileRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findById(id).map(profileMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        return profileRepository.deleteById(id);
    }

    @Override
    public Mono<ProfileDTO> findCurrentProfile(Principal principal) throws Exception {
        if (principal instanceof AbstractAuthenticationToken) {
            User user = userService.getAuthUser((AbstractAuthenticationToken) principal);
            return profileRepository.findCurrentProfile(user.getLogin()).map(profileMapper::toDto);
        } else {
            throw new Exception(messageSource.getMessage("userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Mono<ProfileDTO> findOneByBadge(Long badge_id) {
        return profileRepository.findByBadge(badge_id).map(profileMapper::toDto);
    }
}
