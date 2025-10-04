package com.manuela.meucaixa.adapters.outbound.keycloak;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(
    name = "keycloak-auth",
    url = "${application.keycloak.base-url}",
    configuration = KeycloakFeignConfig.class
)
public interface KeycloakTokenClient {

    @PostMapping(
        value = "/realms/master/protocol/openid-connect/token",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    AccessTokenResponse getAccessToken(Map<String, ?> formData);

    default AccessTokenResponse getAccessToken(final String username,
                                               final String password,
                                               final String clientId) {
        final var form = Map.of(
                "grant_type", "password",
                "client_id", clientId,
                "username", username,
                "password", password
        );

        return getAccessToken(form);
    }
}

