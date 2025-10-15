package com.manuela.meucaixa.adapters.outbound.keycloak;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "keycloak-admin",
    url = "${application.keycloak.base-url}",
    configuration = KeycloakBearerTokenConfig.class
)
public interface KeycloakAdminClient {

    @PostMapping("/admin/realms/meucaixa/users")
    ResponseEntity<Void> createUser(@RequestBody CreateKeycloakUserRequest request);
}
