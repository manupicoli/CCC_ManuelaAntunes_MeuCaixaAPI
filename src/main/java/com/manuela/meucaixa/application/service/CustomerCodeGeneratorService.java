package com.manuela.meucaixa.application.service;

import com.manuela.meucaixa.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerCodeGeneratorService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 6; // número de dígitos

    private final CustomerRepository customerRepository;

    public String generateUniqueCode() {
        log.info("Starting unique code generation");
        String code;
        do {
            code = generateCode();
        } while (customerRepository.findByCode(code).isPresent());

        log.info("Finished unique code generation, code={}", code);
        return code;
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}
