package com.manuela.meucaixa.adapters.outbound.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record UpdateKeycloakUserRequest(String username,
                                        String firstName,
                                        String lastName,
                                        String email,
                                        boolean enabled,
                                        boolean emailVerified,
                                        KeycloakUserAttributes attributes) {

    @Builder
    public record KeycloakUserAttributes(@Singular("customerCode") @JsonProperty("customer_code") List<String> customerCode,
                                         @Singular("phoneNumber") @JsonProperty("phone_number") List<String> phoneNumber) {
    }
}
