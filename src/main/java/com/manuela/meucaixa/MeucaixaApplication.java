package com.manuela.meucaixa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
@EnableConfigurationProperties({CorsProperties.class})
public class MeucaixaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeucaixaApplication.class, args);
	}

}
