package com.manuela.meucaixa.adapters.outbound.keycloak;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import feign.RequestInterceptor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class KeycloakBearerTokenConfig {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String CACHE_KEY = "keycloak-token";
    private static final Duration CACHE_TTL = Duration.ofSeconds(50);

    private final KeycloakTokenClient tokenClient;
    private final KeycloakProperties props;

    private final LoadingCache<String, String> cache = Caffeine.newBuilder()
        .expireAfterWrite(CACHE_TTL)
        .build(new CacheLoader<>() {
            @Override
            public String load(final String key) {
                return tokenClient.getAccessToken(props.masterUsername(), props.masterPassword(), props.clientId())
                        .accessToken();
            }
        });

    @Bean
    RequestInterceptor bearerTokenInterceptor() {
        return template -> {
            var token = cache.get(CACHE_KEY);
            template.header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token);
        };
    }
}
