package com.manuela.meucaixa.application.usecase.user;

import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultGetUserDetailsUseCase implements GetUserDetailsUseCase{

    private final CurrentUser currentUser;
    private final UserRepository userRepository;

    @Override
    public GetUserDetailsResponse execute() {
        final var user = userRepository.findById(UUID.fromString(currentUser.subject()));

        if (user == null) {
            throw new DomainException("Usuário não encontrado");
        }

        return GetUserDetailsResponse.builder()
            .name(user.getName())
            .companyName(user.getCustomer().getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole())
            .build();
    }
}
