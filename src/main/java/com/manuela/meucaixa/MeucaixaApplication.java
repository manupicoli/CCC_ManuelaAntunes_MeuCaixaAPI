package com.manuela.meucaixa;

import com.manuela.meucaixa.adapters.outbound.keycloak.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableFeignClients
@EnableWebSecurity
@SpringBootApplication
@EnableConfigurationProperties({
		CorsProperties.class,
		KeycloakProperties.class
})
public class MeucaixaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeucaixaApplication.class, args);
	}

}
