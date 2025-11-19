package com.manuela.meucaixa.adapters.inbound.controller;

import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Validated
@RequestMapping("/v1/report")
public interface ReportControllerApi {

    @PostMapping
    ResponseEntity<Resource> exportReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate customStart,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate customEnd);
}
