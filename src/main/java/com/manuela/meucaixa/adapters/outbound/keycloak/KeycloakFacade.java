package com.manuela.meucaixa.adapters.outbound.keycloak;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class KeycloakFacade {

    private final KeycloakAdminClient keycloakAdminClient;

    public void createNewUser() {

    }
}
