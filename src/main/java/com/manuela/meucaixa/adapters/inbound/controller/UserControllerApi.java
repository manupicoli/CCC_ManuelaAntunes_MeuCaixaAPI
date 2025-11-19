package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.user.*;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/v1/user")
public interface UserControllerApi {

    @PostMapping("/login")
    LoginResponse login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/create")
    void createUser(@Valid @RequestBody CreateUserRequest request);

    @GetMapping
    GetUserDetailsResponse getUserDetails();

    @PutMapping("/{id}")
    void updateUser(@PathVariable String id,
                    @Valid @RequestBody UpdateUserRequest request);
}
