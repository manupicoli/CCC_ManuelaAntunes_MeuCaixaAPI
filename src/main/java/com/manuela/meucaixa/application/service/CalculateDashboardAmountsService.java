package com.manuela.meucaixa.application.service;

import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateDashboardAmountsService {

    public DashboardAmountsResponse calculateAmounts(final List<FinancialRecord> financialRecords) {
        final var today = LocalDate.now();
        final var startOfMonth = today.withDayOfMonth(1);
        final var endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        final var incomeRecords = new ArrayList<FinancialRecord>();
        final var expenseRecords = new ArrayList<FinancialRecord>();

        getIncomesAndExpenses(financialRecords, incomeRecords, expenseRecords);

        final var totalIncome = getTotalValue(incomeRecords.stream()
                .filter(f -> !f.getPaymentDate().toLocalDate().isAfter(today))
                .toList());

        final var totalExpense = getTotalValue(expenseRecords.stream()
                .filter(f -> !f.getDueDate().toLocalDate().isAfter(today))
                .toList());

        final var currentMonthIncome = incomeRecords.stream()
            .filter(f -> isWithinMonth(f.getPaymentDate().toLocalDate(), startOfMonth, endOfMonth))
            .toList();

        final var currentMonthExpense = expenseRecords.stream()
            .filter(f -> isWithinMonth(f.getDueDate().toLocalDate(), startOfMonth, endOfMonth))
            .toList();

        final var nextExpenses = expenseRecords.stream().filter(f -> isWithinNextDays(f.getDueDate().toLocalDate(), today, 15)).toList().size();

        return DashboardAmountsResponse.builder()
            .totalAmount(totalIncome.subtract(totalExpense))
            .totalIncome(totalIncome)
            .totalExpense(totalExpense)
            .currentMonthIncome(getTotalValue(currentMonthIncome))
            .currentMonthExpense(getTotalValue(currentMonthExpense))
            .totalNextExpense(nextExpenses)
            .build();
    }

    public CategorySummaryServiceResponse calculateCategorySummary(final List<FinancialRecord> financialRecords) {
        return CategorySummaryServiceResponse.builder()
            .content(getCategorySummaryContent(financialRecords))
            .build();
    }

    public MonthlySummaryServiceResponse calculateMonthlySummary(final List<FinancialRecord> financialRecords) {
        return MonthlySummaryServiceResponse.builder()
            .content(getMonthlyContent(financialRecords))
            .build();
    }

    private List<MonthlySummaryServiceResponse.MonthlySummaryContent> getMonthlyContent(List<FinancialRecord> financialRecords) {
        return financialRecords.stream()
            .collect(Collectors.groupingBy(
                    r -> YearMonth.from(getFirstDateNotNull(r)),
                    Collectors.toList()
            ))
            .entrySet().stream()
            .map(entry -> {
                final var month = entry.getKey();
                final var list = entry.getValue();

                final var income = getRecords(list, FinancialRecordType.INCOME);
                final var expense = getRecords(list, FinancialRecordType.EXPENSE);

                return new MonthlySummaryServiceResponse.MonthlySummaryContent(month, income, expense);
            })
            .sorted(Comparator.comparing(MonthlySummaryServiceResponse.MonthlySummaryContent::month))
            .toList();
    }

    private BigDecimal getRecords(List<FinancialRecord> list, FinancialRecordType income) {
        return list.stream()
            .filter(r -> r.getType() == income)
            .map(FinancialRecord::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private LocalDate getFirstDateNotNull(final FinancialRecord r) {
        return r.getDueDate() != null ? r.getDueDate().toLocalDate() : r.getPaymentDate().toLocalDate();
    }

    private List<CategorySummaryServiceResponse.CategorySummaryContent> getCategorySummaryContent(final List<FinancialRecord> financialRecords) {
        return financialRecords.stream()
            .collect(Collectors.groupingBy(
                    r -> r.getCategory().getTitle(),
                    Collectors.mapping(FinancialRecord::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
            ))
            .entrySet().stream()
            .map(e -> CategorySummaryServiceResponse.CategorySummaryContent.builder()
                    .category(e.getKey())
                    .total(e.getValue())
                    .build())
            .toList();
    }

    private BigDecimal getTotalValue(final List<FinancialRecord> incomeRecords) {
        return incomeRecords.stream()
            .map(FinancialRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void getIncomesAndExpenses(final List<FinancialRecord> financialRecords,
                                              final List<FinancialRecord> incomeRecords,
                                              final List<FinancialRecord> expenseRecords) {
        financialRecords.forEach(record -> {
            if (record.getType().equals(FinancialRecordType.INCOME)) {
                incomeRecords.add(record);
            } else {
                expenseRecords.add(record);
            }
        });
    }

    private boolean isWithinMonth(final LocalDate date, final LocalDate start, final LocalDate end) {
        return date != null && (!date.isBefore(start) && !date.isAfter(end));
    }

    private boolean isWithinNextDays(final LocalDate date, final LocalDate today, final int days) {
        if (date == null) return false;
        return !date.isBefore(today) && !date.isAfter(today.plusDays(days));
    }
}
