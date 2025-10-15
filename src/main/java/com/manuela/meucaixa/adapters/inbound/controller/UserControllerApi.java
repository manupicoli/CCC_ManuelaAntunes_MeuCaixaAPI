package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.user.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/v1/user")
public interface UserControllerApi {

    @PostMapping("/create")
    void createUser(@Valid @RequestBody CreateUserRequest request);
}
