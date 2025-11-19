package com.manuela.meucaixa.adapters.outbound.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record GetUserResponse(UUID id,
                              String username,
                              String firstName,
                              String lastName,
                              String email,
                              boolean emailVerified,
                              boolean enabled,
                              Attributes attributes) {

    public record Attributes(@JsonProperty("customer_code") List<String> customerCode,
                             @JsonProperty("phone_number") List<String> phoneNumber) {
    }
}
