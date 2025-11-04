package com.manuela.meucaixa.application.usecase.category;

import com.manuela.meucaixa.infrastructure.UseCase;
import org.springframework.data.domain.Page;

public interface ListCategoryUseCase extends UseCase<ListCategoryRequest, Page<ListCategoryResponse>> {
}
