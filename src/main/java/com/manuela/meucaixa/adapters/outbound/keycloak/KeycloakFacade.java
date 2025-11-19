package com.manuela.meucaixa.adapters.outbound.keycloak;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.substringAfterLast;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class KeycloakFacade {

    private static final String PATH_SEPARATOR = "/";
    private static final String TYPE_PASSWORD = "password";

    private final KeycloakProperties props;
    private final KeycloakClient keycloakClient;
    private final KeycloakAdminClient keycloakAdminClient;

    public KeycloakTokenResponse login(final String username, final String password) {
        Map<String, String> form = new HashMap<>();
        form.put("grant_type", TYPE_PASSWORD);
        form.put("client_id", props.webClientId());
        form.put("client_secret", props.webClientSecret());
        form.put("username", username);
        form.put("password", password);
        form.put("scope", props.scope());

        return keycloakClient.login(form);
    }

    public UUID createNewUser(final String username,
                              final String firstName,
                              final String lastName,
                              final String customerCode,
                              final String phone,
                              final String password) {

        log.info("Creating user with username={}", username);

        final var req = CreateKeycloakUserRequest.builder()
            .email(username)
            .username(username)
            .firstName(firstName)
            .lastName(lastName)
            .enabled(true)
            .emailVerified(true)
            .attributes(CreateKeycloakUserRequest.KeycloakUserAttributes.builder()
                    .customerCode(customerCode)
                    .phoneNumber(phone)
                    .build())
            .credential(CreateKeycloakUserRequest.KeycloakUserCredentials.builder()
                    .type(TYPE_PASSWORD)
                    .value(password)
                    .temporary(false)
                    .build())
            .build();

        final var res = keycloakAdminClient.createUser(req);
        log.info("User successfully created on Keycloak");

        final var location = res.getHeaders().getLocation();
        return extractUserId(location);
    }

    private static UUID extractUserId(final URI location) {
        final var newUserId = substringAfterLast(location.getPath(), PATH_SEPARATOR);
        return UUID.fromString(newUserId);
    }
}
