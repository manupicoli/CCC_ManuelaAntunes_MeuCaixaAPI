package com.manuela.meucaixa.adapters.outbound.keycloak;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "application.keycloak")
public record KeycloakProperties(@NotBlank @URL String baseUrl,
                                 @NotBlank String masterUsername,
                                 @NotBlank String masterPassword,
                                 @NotBlank String clientId) {

}

