package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.home.GetDashboardDetailsRequest;
import com.manuela.meucaixa.application.usecase.home.GetDashboardDetailsResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/v1/home")
public interface HomeControllerApi {

    @GetMapping
    GetDashboardDetailsResponse getDashboardDetails(@Valid GetDashboardDetailsRequest request);
}
