package com.manuela.meucaixa.adapters.outbound.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record CreateKeycloakUserRequest(String username,
                                        String email,
                                        String firstName,
                                        String lastName,
                                        boolean enabled,
                                        boolean emailVerified,
                                        KeycloakUserAttributes attributes,
                                        @Singular List<KeycloakUserCredentials> credentials) {

    @Builder
    public record KeycloakUserAttributes(@Singular("customerCode") @JsonProperty("customer_code") List<String> customerCode,
                                         @Singular("phoneNumber") @JsonProperty("phone_number") List<String> phoneNumber) {
    }

    @Builder
    public record KeycloakUserCredentials(String type,
                                          String value,
                                          boolean temporary) {
    }
}
