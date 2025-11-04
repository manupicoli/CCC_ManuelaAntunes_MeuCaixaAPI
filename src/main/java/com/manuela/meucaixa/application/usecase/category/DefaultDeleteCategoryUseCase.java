package com.manuela.meucaixa.application.usecase.category;

import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.application.usecase.NotFoundException;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.category.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultDeleteCategoryUseCase implements DeleteCategoryUseCase {
    
    private final CurrentUser currentUser;
    private final CategoryRepository categoryRepository;
    
    @Override
    public void execute(final Long id) {
        final var category = categoryRepository.findByIdAndCustomerCode(id, currentUser.customerCode())
            .orElseThrow(NotFoundException::new);

        validateDefaultCategory(category);

        categoryRepository.deleteById(category.getId());
        log.info("Category={} has been deleted successfully", category.getId());
    }

    private void validateDefaultCategory(final Category category) {
        if (category.getIsDefault()) {
            throw new DomainException("Essa categoria não pode ser removida, pois é uma categoria padrão.");
        }
    }
}
