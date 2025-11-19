package com.manuela.meucaixa.application.usecase.category;

import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.application.usecase.NotFoundException;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.category.CategoryRepository;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultDeleteCategoryUseCase implements DeleteCategoryUseCase {

    public static final Long DEFAULT_CATEGORY = 1L;
    
    private final CurrentUser currentUser;
    private final CategoryRepository categoryRepository;
    private final FinancialRecordRepository financialRecordRepository;

    @Override
    public void execute(final Long id) {
        final var category = categoryRepository.findByIdAndCustomerCode(id, currentUser.customerCode())
            .orElseThrow(NotFoundException::new);

        validateDefaultCategory(category);

        final var records = financialRecordRepository
            .findByCategoryIdAndCode(category.getId(), currentUser.customerCode());

        if (!records.isEmpty()) {
            final var defaultCategory = categoryRepository.findById(DEFAULT_CATEGORY)
                    .orElseThrow(NotFoundException::new);

            for (var record : records) {
                record.setCategory(defaultCategory);
                financialRecordRepository.save(record);
            }

            log.info("Reatribuídos {} registros para a categoria padrão.", records.size());
        }

        categoryRepository.deleteById(category.getId());
        log.info("Category={} has been deleted successfully", category.getId());
    }

    private void validateDefaultCategory(final Category category) {
        if (category.getIsDefault()) {
            throw new DomainException("Essa categoria não pode ser removida, pois é uma categoria padrão.");
        }
    }
}
