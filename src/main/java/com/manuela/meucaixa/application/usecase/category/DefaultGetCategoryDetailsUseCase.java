package com.manuela.meucaixa.application.usecase.category;

import com.manuela.meucaixa.application.usecase.NotFoundException;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.category.CategoryRepository;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultGetCategoryDetailsUseCase implements GetCategoryDetailsUseCase {

    private final CurrentUser currentUser;
    private final CategoryRepository categoryRepository;
    private final FinancialRecordRepository financialRecordRepository;

    @Override
    public GetCategoryDetailsResponse execute(final Long id) {
        final var category = categoryRepository.findByIdAndCustomerCode(id, currentUser.customerCode())
            .orElseThrow(NotFoundException::new);

        final var hasRecords = financialRecordRepository
            .existsByCategoryIdAndCustomerCode(category.getId(), currentUser.customerCode());

        return GetCategoryDetailsResponse.builder()
            .title(category.getTitle())
            .description(category.getDescription())
            .isDefault(category.getIsDefault())
            .hasRecords(hasRecords)
            .build();
    }
}
