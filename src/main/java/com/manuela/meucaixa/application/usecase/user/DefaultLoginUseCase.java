package com.manuela.meucaixa.application.usecase.user;

import com.manuela.meucaixa.adapters.outbound.keycloak.KeycloakFacade;
import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultLoginUseCase implements LoginUseCase {

    private final KeycloakFacade keycloakFacade;
    private final UserRepository userRepository;

    @Override
    public LoginResponse execute(final LoginRequest req) {
        try {
            final var res = keycloakFacade.login(req.username(), req.password());
            final var user = userRepository.findByEmail(req.username());

            if (user == null) {
                throw new DomainException("Usuário ou senha inválidos.");
            }

            return LoginResponse.builder()
                .id(user.getId())
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
