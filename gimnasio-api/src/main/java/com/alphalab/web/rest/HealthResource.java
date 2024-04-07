package com.alphalab.web.rest;

import com.alphalab.service.KeycloakService;
import com.alphalab.service.dto.CreateUserRequestDTO;
import com.alphalab.service.dto.GymDTO;
import com.alphalab.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for checking the status of the system.
 */
@RestController
@RequestMapping("/health")
public class HealthResource {

    private final Logger log = LoggerFactory.getLogger(HealthResource.class);
    private KeycloakService keycloakService;

    public HealthResource(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    /**
     * {@code GET  /} : echo given input.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/")
    public BodyBuilder health() throws Exception {
        log.debug("REST request to get check the health of the system: {}");
        return ResponseEntity.ok();
    }

    /**
     * {@code GET  /echo/:value} : echo given input.
     *
     * @param value the value to echo.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with the input param.
     */
    @GetMapping("/echo/{value}")
    public ResponseEntity<String> echo(@PathVariable String value) throws Exception {
        log.debug("REST request to get check the status of he system by echoing: {}", value);
        return ResponseEntity.ok(value);
    }
}
