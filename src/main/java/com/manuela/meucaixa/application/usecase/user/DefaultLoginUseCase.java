package com.manuela.meucaixa.application.usecase.user;

import com.manuela.meucaixa.adapters.outbound.keycloak.KeycloakFacade;
import com.manuela.meucaixa.application.usecase.DomainException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultLoginUseCase implements LoginUseCase {

    private final KeycloakFacade keycloakFacade;

    @Override
    public LoginResponse execute(final LoginRequest req) {
        try {
            final var res = keycloakFacade.login(req.username(), req.password());
            return LoginResponse.builder()
                .accessToken(res.accessToken())
                .refreshToken(res.refreshToken())
                .expiresIn(res.expiresIn())
                .refreshExpiresIn(res.refreshExpiresIn())
                .build();
        } catch (Exception e) {
            log.error("An error occurred while login", e);
            throw new DomainException("Não foi possível realizar o login.");
        }
    }
}
