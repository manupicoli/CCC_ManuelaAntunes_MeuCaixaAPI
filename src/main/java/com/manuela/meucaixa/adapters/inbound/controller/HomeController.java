package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.home.GetDashboardDetailsRequest;
import com.manuela.meucaixa.application.usecase.home.GetDashboardDetailsResponse;
import com.manuela.meucaixa.application.usecase.home.GetDashboardDetailsUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class HomeController implements HomeControllerApi {

    private final GetDashboardDetailsUseCase  getDashboardDetailsUseCase;

    @Override
    public GetDashboardDetailsResponse getDashboardDetails(GetDashboardDetailsRequest request) {
        return getDashboardDetailsUseCase.execute(request);
    }
}
