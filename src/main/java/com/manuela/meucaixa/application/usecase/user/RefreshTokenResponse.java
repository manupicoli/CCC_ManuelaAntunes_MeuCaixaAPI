package com.manuela.meucaixa.application.usecase.user;

import java.math.BigDecimal;

public record RefreshTokenResponse(String accessToken,
                                   String refreshToken,
                                   BigDecimal expiresIn,
                                   BigDecimal refreshExpiresIn) {
}
