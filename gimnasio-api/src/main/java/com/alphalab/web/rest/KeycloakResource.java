package com.alphalab.web.rest;

import com.alphalab.domain.enumeration.KeycloakRoles;
import com.alphalab.service.KeycloakService;
import com.alphalab.service.dto.CreateUserRequestDTO;
import com.alphalab.service.dto.GymDTO;
import com.alphalab.service.dto.UserDTO;
import java.util.List;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for checking the status of the system.
 */
@RestController
@RequestMapping("/api")
public class KeycloakResource {

    private final Logger log = LoggerFactory.getLogger(GymResource.class);

    @Autowired
    private KeycloakService keycloakService;

    public KeycloakResource(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/admin/users")
    public void createUser(@RequestBody UserDTO userDTO) {
        keycloakService.createUser(userDTO, KeycloakRoles.ROLE_USER, "Users");
    }

    /**
     * {@code GET  /admin/users} : get all the users.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gyms in body.
     */
    @GetMapping("/admin/users")
    public List<UserDTO> getAllUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable, ServerHttpRequest request) {
        log.debug("REST request to get a page of users");
        return this.keycloakService.getAllUsers();
    }

    /**
     * {@code GET  /admin/users/:userId} : get the "userId" gym.
     *
     * @param userId the id of the gymDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gymDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin/users/{userId}")
    public Mono<ResponseEntity<UserDTO>> getUser(@PathVariable String userId) {
        log.debug("REST request to get user : {}", userId);
        Mono<UserDTO> user = keycloakService.getUserById(userId);
        return ResponseUtil.wrapOrNotFound(user);
    }

    /**
     * {@code DELETE  /admin/users/:userId} : delete the "userId" user.
     *
     * @param userId the id of the userDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/users/{userId}")
    public void deleteUser(@PathVariable String userId) {
        log.debug("REST request to delete User : {}", userId);
        keycloakService.deleteUser(userId);
    }
}
