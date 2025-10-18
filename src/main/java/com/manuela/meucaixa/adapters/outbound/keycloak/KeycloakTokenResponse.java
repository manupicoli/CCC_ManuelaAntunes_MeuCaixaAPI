package com.manuela.meucaixa.adapters.outbound.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record KeycloakTokenResponse(@JsonProperty("access_token") String accessToken,
                                    @JsonProperty("refresh_token") String refreshToken,
                                    @JsonProperty("expires_in") BigDecimal expiresIn,
                                    @JsonProperty("refresh_expires_in") BigDecimal refreshExpiresIn) {
}
