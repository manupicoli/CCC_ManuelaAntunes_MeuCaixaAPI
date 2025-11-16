package com.manuela.meucaixa.application.service;

import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculateDashboardAmountsService {

    public CalculateDashboardAmountsResponse execute(final List<FinancialRecord> financialRecords) {
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

        return CalculateDashboardAmountsResponse.builder()
            .totalAmount(totalIncome.subtract(totalExpense))
            .totalIncome(totalIncome)
            .totalExpense(totalExpense)
            .currentMonthIncome(getTotalValue(currentMonthIncome))
            .currentMonthExpense(getTotalValue(currentMonthExpense))
            .totalNextExpense(nextExpenses)
            .build();
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
