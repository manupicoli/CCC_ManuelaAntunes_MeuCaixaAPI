package com.manuela.meucaixa.application.usecase.home;

import com.manuela.meucaixa.application.service.CalculateDashboardAmountsService;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultGetDashboardDetailsUseCase implements GetDashboardDetailsUseCase {

    private final CurrentUser currentUser;
    private final FinancialRecordRepository financialRecordRepository;
    private final CalculateDashboardAmountsService  calculateDashboardAmountsService;

    @Override
    public GetDashboardDetailsResponse execute() {
        final var records = financialRecordRepository.findAllByCustomerCode(currentUser.customerCode());
        final var response = calculateDashboardAmountsService.execute(records);

        return GetDashboardDetailsResponse.builder()
            .currentAmount(response.totalAmount())
            .totalIncome(response.currentMonthIncome())
            .totalExpense(response.currentMonthExpense())
            .nextIncomeQuantity(response.totalNextExpense())
            .build();
    }
}
