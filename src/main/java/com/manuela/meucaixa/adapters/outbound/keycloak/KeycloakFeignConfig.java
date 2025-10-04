package com.manuela.meucaixa.adapters.outbound.keycloak;

import feign.form.spring.SpringFormEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class KeycloakFeignConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    feign.codec.Encoder feignFormEncoder() { return new SpringFormEncoder(new SpringEncoder(messageConverters)); }
}
