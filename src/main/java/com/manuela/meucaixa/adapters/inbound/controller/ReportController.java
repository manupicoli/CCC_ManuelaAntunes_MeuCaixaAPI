package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.report.ExportReportRequest;
import com.manuela.meucaixa.application.usecase.report.ExportReportUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ReportController implements ReportControllerApi {

    private final ExportReportUseCase exportReportUseCase;

    @Override
    @SneakyThrows
    public ResponseEntity<Resource> exportReport(final LocalDate customStart, final LocalDate customEnd) {
        final var pdf = exportReportUseCase.execute(new ExportReportRequest(customStart, customEnd));

        final FileSystemResource file = new FileSystemResource(pdf);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf");

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.contentLength())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(file);
    }
}
