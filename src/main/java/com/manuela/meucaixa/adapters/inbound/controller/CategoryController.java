package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.category.AddEditCategoryRequest;
import com.manuela.meucaixa.application.usecase.category.AddEditCategoryUseCase;
import com.manuela.meucaixa.application.usecase.category.DeleteCategoryUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryController implements CategoryControllerApi {

    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final AddEditCategoryUseCase addEditCategoryUseCase;

    @Override
    public void create(final AddEditCategoryRequest request) {
        addEditCategoryUseCase.execute(null, request);
    }

    @Override
    public void update(final Long id, final AddEditCategoryRequest request) {
        addEditCategoryUseCase.execute(id, request);
    }

    @Override
    public void delete(final Long id) {
        deleteCategoryUseCase.execute(id);
    }
}
