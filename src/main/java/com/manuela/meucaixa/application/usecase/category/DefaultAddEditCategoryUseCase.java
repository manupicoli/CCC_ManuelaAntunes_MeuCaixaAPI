package com.manuela.meucaixa.application.usecase.category;

import com.manuela.meucaixa.application.usecase.NotFoundException;
import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.category.CategoryRepository;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.customer.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultAddEditCategoryUseCase implements AddEditCategoryUseCase {

    private final CurrentUser currentUser;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void execute(final Long id, final AddEditCategoryRequest request) {
        final var category = (id == null) ? createNewOne() : findRequired(id);

        category.setTitle(request.title());
        category.setDescription(request.description());
        category.setIsDefault(false);
        category.setCustomer(getCustomer(currentUser.customerCode()));

        final var saved = categoryRepository.save(category);

        log.info("Category={} has been saved successfully", saved.getId());
    }

    private Category createNewOne() {
        return Category.builder()
            .build();
    }

    private Category findRequired(final Long id) {
        return categoryRepository.findByIdAndCustomerCode(id, currentUser.customerCode())
            .orElseThrow(NotFoundException::new);
    }

    private Customer getCustomer(final String code) {
        return customerRepository.findByCode(code)
            .orElseThrow(NotFoundException::new);
    }
}
