package com.manuela.meucaixa.auth;

import com.manuela.meucaixa.application.usecase.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.stripToNull;
import static org.apache.commons.lang3.StringUtils.substringBefore;

@Slf4j
@Configuration
class CurrentUserConfig {

    @Bean
    @Scope(
        value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.INTERFACES
    )
    public CurrentUser currentUser(final HttpServletRequest req) {
        log.info("Trying to create CurrentUser");

        if (getAuthentication() instanceof JwtAuthenticationToken jwtAuth) {
            if (StringUtils.isBlank(jwtAuth.getToken().getClaim("customer_code"))) {
                log.warn("There are no customer_code to user {}", jwtAuth.getName());
                throw new DomainException("Não foi possível localizar um cliente vinculado ao cadastro.");
            }

            final var user = CurrentUserImpl.builder()
                .customerCode(jwtAuth.getToken().getClaim("customer_code"))
                .subject(jwtAuth.getToken().getSubject())
                .name(jwtAuth.getToken().getClaim("name"))
                .email(jwtAuth.getToken().getClaim("email"))
                .username(jwtAuth.getToken().getClaim("preferred_username"))
                .userAgent(req.getHeader("User-Agent"))
                .remoteAddress(toRemoteAddress(req))
                .build();

            log.info("User {} has been connected as customer={}", user.username(), user.customerCode());

            return user;
        }

        log.warn("User is not authenticated");
        throw new SecurityException();
    }

    private String toRemoteAddress(final HttpServletRequest req) {
        return Optional.ofNullable(stripToNull(req.getHeader("X-Real-IP")))
            .or(() -> Optional.ofNullable(stripToNull(req.getHeader("X-Forwarded-For"))))
            .map(s -> stripToNull(substringBefore(s, ",")))
            .orElseGet(req::getRemoteAddr);
    }

    Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
