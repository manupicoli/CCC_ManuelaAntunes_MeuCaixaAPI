package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.user.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UserController implements UserControllerApi {

    private final LoginUseCase loginUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final GetUserDetailsUseCase getUserDetailsUseCase;

    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        return loginUseCase.execute(loginRequest);
    }

    @Override
    public void createUser(final CreateUserRequest request) {
        createUserUseCase.execute(request);
    }

    @Override
    public GetUserDetailsResponse getUserDetails() {
        return getUserDetailsUseCase.execute();
    }

    @Override
    public void updateUser(final String id, final UpdateUserRequest request) {
        updateUserUseCase.execute(UUID.fromString(id), request);
    }
}
