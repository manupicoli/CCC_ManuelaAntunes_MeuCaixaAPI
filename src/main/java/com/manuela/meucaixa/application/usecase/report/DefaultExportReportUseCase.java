package com.manuela.meucaixa.application.usecase.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordType;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultExportReportUseCase implements ExportReportUseCase {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final CurrentUser currentUser;
    private final FinancialRecordRepository financialRecordRepository;

    @Override
    public Path execute(final ExportReportRequest req) {
        log.info("Generating report");

        final var records = financialRecordRepository.filterByPeriod(req.customStart(), req.customEnd(), currentUser.customerCode());

        final var incomes = records.stream()
            .filter(r -> r.getType().equals(FinancialRecordType.INCOME))
            .map(FinancialRecord::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_EVEN);

        final var expenses = records.stream()
            .filter(r -> r.getType().equals(FinancialRecordType.EXPENSE))
            .map(FinancialRecord::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_EVEN);

        final var total = incomes.subtract(expenses);

        try {
            Path temp = Files.createTempFile("report_", ".pdf");

            PdfWriter writer = new PdfWriter(temp.toFile());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Relatório Financeiro")
                .setFontSize(20)
                .setBold());

            document.add(new Paragraph(
                    "Período: " + req.customStart().format(DATE_FMT) + " até " + req.customEnd().format(DATE_FMT)
            ).setMarginBottom(20));

            document.add(new Paragraph("Entradas: R$ " + incomes));
            document.add(new Paragraph("Saídas: R$ " + expenses));
            document.add(new Paragraph("Saldo Final: R$ " + total).setBold());
            document.add(new Paragraph("\n"));

            Table table = new Table(new float[]{3, 3, 3, 6});
            table.addHeaderCell("Data");
            table.addHeaderCell("Categoria");
            table.addHeaderCell("Tipo");
            table.addHeaderCell("Descrição / Valor");

            for (var r : records) {
                table.addCell(getDate(r));
                table.addCell(r.getCategory().getTitle());
                table.addCell(r.getType().getValue());
                table.addCell(getDescription(r));
            }

            document.add(table);
            document.close();

            log.info("Generated report successfully, path={}", temp.toAbsolutePath());
            return temp;
        } catch (Exception e) {
            log.error("Erro ao gerar PDF", e);
            throw new RuntimeException("Erro ao gerar PDF");
        }
    }

    private static String getDescription(FinancialRecord r) {
        return StringUtils.isEmpty(r.getDescription())
            ? "R$ " + r.getAmount().setScale(2, RoundingMode.HALF_EVEN)
            : r.getDescription() + " — R$ " + r.getAmount().setScale(2, RoundingMode.HALF_EVEN);
    }

    private static String getDate(FinancialRecord r) {
        return r.getDueDate() == null
            ? r.getPaymentDate().toLocalDate().format(DATE_FMT)
            : r.getDueDate().toLocalDate().format(DATE_FMT);
    }
}
