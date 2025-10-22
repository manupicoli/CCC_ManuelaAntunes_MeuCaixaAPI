package com.manuela.meucaixa.application.usecase.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultRefreshTokenUseCase implements RefreshTokenUseCase {

    @Override
    public RefreshTokenResponse execute(RefreshTokenRequest refreshTokenRequest) {
        //TODO
        return null;
    }
}
