package com.manuela.meucaixa.adapters.outbound.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(@JsonProperty("access_token") String accessToken,
                                  @JsonProperty("expires_in") long expiresIn
) {
}
