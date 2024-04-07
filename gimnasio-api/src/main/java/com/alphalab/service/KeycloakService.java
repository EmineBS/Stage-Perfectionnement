package com.alphalab.service;

import com.alphalab.domain.enumeration.KeycloakRoles;
import com.alphalab.service.dto.UserDTO;
import java.util.List;
import org.keycloak.representations.idm.UserRepresentation;
import reactor.core.publisher.Mono;

public interface KeycloakService {
    void createUser(UserDTO userDTO, KeycloakRoles roleName, String groupName);
    List<UserDTO> getAllUsers();

    Mono<Long> countAllUsers();

    Mono<UserDTO> getUserById(String userId);

    void deleteUser(String userId);
}
