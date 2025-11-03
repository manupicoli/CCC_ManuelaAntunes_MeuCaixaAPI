package com.manuela.meucaixa;

import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.application.usecase.NotFoundException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ApiError handle(final DomainException e) {
        log.error("An unexpected error occurred", e);

        return ApiError.builder()
            .timestamp(ZonedDateTime.now())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError handle(final NotFoundException e) {
        log.error("An unexpected error occurred", e);

        return ApiError.builder()
            .timestamp(ZonedDateTime.now())
            .message(e.getMessage())
            .build();
    }

    @Builder
    public record ApiError(ZonedDateTime timestamp,String message) {
    }
}
