package com.manuela.meucaixa.adapters.outbound.keycloak;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@FeignClient(
    name = "keycloak-admin",
    url = "${application.keycloak.base-url}",
    configuration = KeycloakBearerTokenConfig.class
)
public interface KeycloakAdminClient {

    @PostMapping("/admin/realms/meucaixa/users")
    ResponseEntity<Void> createUser(@RequestBody CreateKeycloakUserRequest request);

    @GetMapping("/admin/realms/meucaixa/users/{id}")
    Optional<GetUserResponse> getUser(@PathVariable UUID id);

    @PutMapping("/admin/realms/meucaixa/users/{id}")
    void updateUser(@PathVariable UUID id, @RequestBody UpdateKeycloakUserRequest req);
}
