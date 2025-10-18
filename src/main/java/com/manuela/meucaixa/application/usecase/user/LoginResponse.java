package com.manuela.meucaixa.application.usecase.user;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LoginResponse(String accessToken,
                            String refreshToken,
                            BigDecimal expiresIn,
                            BigDecimal refreshExpiresIn) {
}
