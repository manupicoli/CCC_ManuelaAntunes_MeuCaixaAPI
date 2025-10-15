package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.user.CreateUserRequest;
import com.manuela.meucaixa.application.usecase.user.CreateUserUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UserController implements UserControllerApi {

    private final CreateUserUseCase createUserUseCase;

    @Override
    public void createUser(final CreateUserRequest request) {
        createUserUseCase.execute(request);
    }
}
