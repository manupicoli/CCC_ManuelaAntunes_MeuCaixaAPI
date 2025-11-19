package com.manuela.meucaixa.application.usecase.report;

import com.manuela.meucaixa.infrastructure.UseCase;

import java.nio.file.Path;

public interface ExportReportUseCase extends UseCase<ExportReportRequest, Path> {
}
