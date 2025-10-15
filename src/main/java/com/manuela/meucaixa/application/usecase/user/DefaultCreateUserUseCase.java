package com.manuela.meucaixa.application.usecase.user;

import com.manuela.meucaixa.adapters.outbound.keycloak.KeycloakFacade;
import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.customer.CustomerRepository;
import com.manuela.meucaixa.domain.user.Users;
import com.manuela.meucaixa.domain.user.UserRepository;
import com.manuela.meucaixa.domain.user.UserRole;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultCreateUserUseCase implements CreateUserUseCase {

    private final KeycloakFacade keycloakFacade;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void execute(final CreateUserRequest req) {
        try {
            final var customerCode = "TODO";
            final var userId = keycloakFacade.createNewUser(req.email(),
                    req.firstName(),
                    req.lastName(),
                    customerCode,
                    req.phone(),
                    req.password());

            log.info("Created new user id={}, customer={}", userId, customerCode);

            final var newCustomer = Customer.builder()
                    .code(customerCode)
                    .name(req.companyName())
                    .build();

            final var savedCustomer = customerRepository.save(newCustomer);

            final var newUser = Users.builder()
                    .id(userId)
                    .role(UserRole.ADMIN)
                    .name(req.firstName() + " " + req.lastName())
                    .phone(req.phone())
                    .email(req.email())
                    .customer(savedCustomer)
                    .build();

            final var saved = userRepository.save(newUser);
            log.info("User={} successfully saved", saved.getId());
        } catch (final Exception e) {
            log.error("An error occurred while creating user", e);
            throw new DomainException("Não foi possível criar o usuário. Tente novamente mais tarde.");
        }

    }
}
