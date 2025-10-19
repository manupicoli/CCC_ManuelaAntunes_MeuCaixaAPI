package com.manuela.meucaixa.auth;

import lombok.Builder;

@Builder
record CurrentUserImpl(String customerCode,
                       String subject,
                       String email,
                       String name,
                       String username,
                       String remoteAddress,
                       String userAgent) implements CurrentUser {
}

