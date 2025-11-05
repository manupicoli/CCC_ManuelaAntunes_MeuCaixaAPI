package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.category.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryController implements CategoryControllerApi {

    private final ListCategoryUseCase listCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final AddEditCategoryUseCase addEditCategoryUseCase;
    private final GetCategoryDetailsUseCase getCategoryDetailsUseCase;

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

    @Override
    public GetCategoryDetailsResponse get(final Long id) {
        return getCategoryDetailsUseCase.execute(id);
    }

    @Override
    public Page<ListCategoryResponse> list(final String qs, final Pageable page) {
        return listCategoryUseCase.execute(new ListCategoryRequest(qs, page));
    }
}
