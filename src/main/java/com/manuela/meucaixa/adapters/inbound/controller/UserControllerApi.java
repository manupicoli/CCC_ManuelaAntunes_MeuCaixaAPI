package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.user.CreateUserRequest;
import com.manuela.meucaixa.application.usecase.user.GetUserDetailsResponse;
import com.manuela.meucaixa.application.usecase.user.LoginRequest;
import com.manuela.meucaixa.application.usecase.user.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/v1/user")
public interface UserControllerApi {

    @PostMapping("/login")
    LoginResponse login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/create")
    void createUser(@Valid @RequestBody CreateUserRequest request);

    @GetMapping
    GetUserDetailsResponse getUserDetails();
}
