package com.manuela.meucaixa.adapters.outbound.keycloak;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "keycloak",
        url = "${application.keycloak.base-url}",
        configuration = KeycloakFeignConfig.class
)
public interface KeycloakClient {

    @PostMapping(
            value = "/realms/meucaixa/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    KeycloakTokenResponse login(@RequestBody Map<String, ?> form);
}
