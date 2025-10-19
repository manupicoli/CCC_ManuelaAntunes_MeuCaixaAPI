package com.manuela.meucaixa.application.usecase.home;

import com.manuela.meucaixa.auth.CurrentUser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultGetDashboardDetailsUseCase implements GetDashboardDetailsUseCase {

    private final CurrentUser currentUser;

    @Override
    public GetDashboardDetailsResponse execute(final GetDashboardDetailsRequest req) {
        return null;
    }
}
