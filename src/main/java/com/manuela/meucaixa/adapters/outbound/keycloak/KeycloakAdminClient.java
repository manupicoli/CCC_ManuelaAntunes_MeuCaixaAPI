package com.manuela.meucaixa.adapters.outbound.keycloak;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
    name = "keycloak-admin",
    url = "${application.keycloak.base-url}",
    configuration = KeycloakBearerTokenConfig.class
)
public interface KeycloakAdminClient {

    @PostMapping(
        value = "/realms/meucaixa/protocol/openid-connect/token",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @Headers("Content-Type: application/x-www-form-urlencoded")
    KeycloakTokenResponse login(@RequestBody Map<String, ?> form);

    @PostMapping("/admin/realms/meucaixa/users")
    ResponseEntity<Void> createUser(@RequestBody CreateKeycloakUserRequest request);
}
