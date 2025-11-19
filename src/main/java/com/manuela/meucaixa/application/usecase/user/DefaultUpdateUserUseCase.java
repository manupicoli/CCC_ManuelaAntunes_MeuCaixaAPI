package com.manuela.meucaixa.application.usecase.user;

import com.manuela.meucaixa.adapters.outbound.keycloak.KeycloakFacade;
import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.application.usecase.NotFoundException;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.customer.CustomerRepository;
import com.manuela.meucaixa.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultUpdateUserUseCase implements UpdateUserUseCase {

    private final CurrentUser currentUser;
    private final KeycloakFacade keycloakFacade;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void execute(final UUID id, final UpdateUserRequest req) {
        try {
            keycloakFacade.updateUser(id, req);

            final var customer = customerRepository.findByCode(currentUser.customerCode())
                .orElseThrow(NotFoundException::new);

            customer.setName(req.companyName());

            final var user = userRepository.findByEmail(currentUser.username())
                .orElseThrow(NotFoundException::new);

            user.setName(req.firstName() + " " + req.lastName());
            user.setPhone(req.phone());

            userRepository.save(user);
            customerRepository.save(customer);

            log.info("User with id={} has been updated", id);
        } catch (final Exception e) {
            log.error("An error occurred while updating user", e);
            throw new DomainException("Não foi possível editar os dados do usuário. Tente novamente mais tarde.");
        }
    }
}
