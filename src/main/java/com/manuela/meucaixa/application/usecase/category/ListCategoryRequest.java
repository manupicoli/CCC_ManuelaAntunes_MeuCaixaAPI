package com.manuela.meucaixa.application.usecase.category;

import org.springframework.data.domain.Pageable;

public record ListCategoryRequest(String qs,
                                  Pageable pageable) {
}
