package com.alphalab.service.impl;

import com.alphalab.domain.enumeration.KeycloakRoles;
import com.alphalab.service.KeycloakService;
import com.alphalab.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.admin.realm:jhipster}")
    String adminRealm;

    public void createUser(UserDTO userDTO, KeycloakRoles roleName, String groupName) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(true);
        credential.setValue(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getLogin().toLowerCase());
        user.setEnabled(true);
        user.setGroups(Arrays.asList(groupName));
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setEmailVerified(true);
        user.setCredentials(Arrays.asList(credential));

        Response response = keycloak.realm(adminRealm).users().create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo());
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        RoleRepresentation role = keycloak.realm(adminRealm).roles().get(roleName.toString()).toRepresentation();
        keycloak.realm(adminRealm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> listUsers = new ArrayList<UserDTO>();
        List<UserRepresentation> users = keycloak.realm(adminRealm).users().list();
        for (UserRepresentation user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setLogin(user.getUsername());
            userDTO.setEmail(user.getEmail());
            // Extract other user properties as needed

            log.debug("user: " + userDTO);
            listUsers.add(userDTO);
        }
        return listUsers;
    }

    @Override
    public Mono<Long> countAllUsers() {
        return Mono.just(Long.valueOf(keycloak.realm(adminRealm).users().list().size()));
    }

    @Override
    public Mono<UserDTO> getUserById(String userId) {
        UserRepresentation userResource = keycloak.realm(adminRealm).users().get(userId).toRepresentation();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userResource.getId());
        userDTO.setLogin(userResource.getUsername());
        userDTO.setEmail(userResource.getEmail());
        return Mono.just(userDTO);
    }

    @Override
    public void deleteUser(String userId) {
        this.keycloak.realm(adminRealm).users().get(userId).remove();
    }
}
