package com.manuela.meucaixa.application.usecase.home;

import com.manuela.meucaixa.application.service.CalculateDashboardAmountsService;
import com.manuela.meucaixa.application.service.CategorySummaryServiceResponse;
import com.manuela.meucaixa.application.service.MonthlySummaryServiceResponse;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultGetDashboardDetailsUseCase implements GetDashboardDetailsUseCase {

    private final CurrentUser currentUser;
    private final FinancialRecordRepository financialRecordRepository;
    private final CalculateDashboardAmountsService  calculateDashboardAmountsService;

    @Override
    public GetDashboardDetailsResponse execute() {
        final var records = financialRecordRepository.findAllByCustomerCode(currentUser.customerCode());
        final var response = calculateDashboardAmountsService.calculateAmounts(records);
        final var categorySummary = calculateDashboardAmountsService.calculateCategorySummary(records);
        final var monthlySummary = calculateDashboardAmountsService.calculateMonthlySummary(records);

        return GetDashboardDetailsResponse.builder()
            .currentAmount(response.totalAmount())
            .totalIncome(response.currentMonthIncome())
            .totalExpense(response.currentMonthExpense())
            .nextIncomeQuantity(response.totalNextExpense())
            .categorySummary(getCategorySummary(categorySummary))
            .monthlySummary(getMonthlySummary(monthlySummary))
            .build();
    }

    private List<GetDashboardDetailsResponse.MonthlySummaryResponse> getMonthlySummary(final MonthlySummaryServiceResponse monthlySummary) {
        return monthlySummary.content().stream()
            .map(e -> GetDashboardDetailsResponse.MonthlySummaryResponse.builder()
                .month(e.month())
                .totalIncome(e.totalIncome())
                .totalExpense(e.totalExpense())
                .build())
            .toList();
    }

    private List<GetDashboardDetailsResponse.CategorySummaryResponse> getCategorySummary(final CategorySummaryServiceResponse categorySummary) {
        return categorySummary.content().stream()
            .map(e -> GetDashboardDetailsResponse.CategorySummaryResponse.builder()
                .category(e.category())
                .total(e.total())
                .build())
            .toList();
    }
}
