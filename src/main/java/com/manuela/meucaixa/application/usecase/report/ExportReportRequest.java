package com.manuela.meucaixa.application.usecase.report;

import java.time.LocalDate;

public record ExportReportRequest(LocalDate customStart,
                                  LocalDate customEnd) {
}
