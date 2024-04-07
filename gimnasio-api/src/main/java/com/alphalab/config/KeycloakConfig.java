package com.alphalab.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.admin.url:http://localhost:9080/}")
    String serverUrl;

    @Value("${keycloak.admin.master:master}")
    String adminMaster;

    @Value("${keycloak.admin.clientid:admin-cli}")
    String clientId;

    @Value("${keycloak.admin.username:admin}")
    String adminUsername;

    @Value("${keycloak.admin.password:admin}")
    String adminPassword;

    public KeycloakConfig() {}

    @Bean
    public Keycloak keycloak() {
        Keycloak keycloak = KeycloakBuilder
            .builder() //
            .serverUrl(serverUrl) //
            .realm(adminMaster) //
            .username(adminUsername)
            .password(adminPassword)
            .clientId(clientId) //
            .build();
        return keycloak;
    }
}
