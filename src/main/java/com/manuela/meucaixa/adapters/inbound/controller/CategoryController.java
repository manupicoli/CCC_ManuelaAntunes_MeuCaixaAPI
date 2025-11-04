package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.category.AddEditCategoryRequest;
import com.manuela.meucaixa.application.usecase.category.AddEditCategoryUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryController implements CategoryControllerApi {

    private final AddEditCategoryUseCase addEditCategoryUseCase;

    @Override
    public void create(AddEditCategoryRequest request) {
        addEditCategoryUseCase.execute(null, request);
    }

    @Override
    public void update(Long id, AddEditCategoryRequest request) {
        addEditCategoryUseCase.execute(id, request);
    }
}
