package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.user.CreateUserUseCase;
import com.manuela.meucaixa.application.usecase.user.LoginRequest;
import com.manuela.meucaixa.application.usecase.user.LoginResponse;
import com.manuela.meucaixa.application.usecase.user.LoginUseCase;
import com.manuela.meucaixa.application.usecase.user.CreateUserRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UserController implements UserControllerApi {

    private final CreateUserUseCase createUserUseCase;
    private final LoginUseCase loginUseCase;

    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        return loginUseCase.execute(loginRequest);
    }

    @Override
    public void createUser(final CreateUserRequest request) {
        createUserUseCase.execute(request);
    }
}
