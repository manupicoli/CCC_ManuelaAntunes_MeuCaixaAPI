package com.manuela.meucaixa.application.usecase.user;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record LoginResponse(UUID id,
                            String customerCode,
                            String accessToken,
                            String refreshToken,
                            BigDecimal expiresIn,
                            BigDecimal refreshExpiresIn) {
}
