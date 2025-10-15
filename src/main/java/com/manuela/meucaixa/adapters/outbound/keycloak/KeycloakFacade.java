package com.manuela.meucaixa.adapters.outbound.keycloak;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.substringAfterLast;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class KeycloakFacade {

    private static final String PATH_SEPARATOR = "/";
    private static final String TYPE_PASSWORD = "password";

    private final KeycloakAdminClient keycloakAdminClient;

    public UUID createNewUser(final String username,
                              final String firstName,
                              final String lastName,
                              final String customerCode,
                              final String phone,
                              final String password) {

        final var req = CreateKeycloakUserRequest.builder()
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
        final var location = res.getHeaders().getLocation();

        return extractUserId(location);
    }

    private static UUID extractUserId(final URI location) {
        final var newUserId = substringAfterLast(location.getPath(), PATH_SEPARATOR);
        return UUID.fromString(newUserId);
    }
}
