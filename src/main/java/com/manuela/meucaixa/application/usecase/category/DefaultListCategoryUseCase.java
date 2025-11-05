package com.manuela.meucaixa.application.usecase.category;

import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.category.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultListCategoryUseCase implements ListCategoryUseCase {

    private final CurrentUser currentUser;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<ListCategoryResponse> execute(final ListCategoryRequest request) {
        return categoryRepository
            .findAllByCustomerCode(currentUser.customerCode(), request.qs(), request.pageable())
            .map(toResponse());
    }

    private Function<Category, ListCategoryResponse> toResponse() {
        return e -> ListCategoryResponse.builder()
            .id(e.getId())
            .title(e.getTitle())
            .description(e.getDescription())
            .isDefault(e.getIsDefault())
            .build();
    }
}
