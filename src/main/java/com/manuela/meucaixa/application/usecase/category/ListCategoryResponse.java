package com.manuela.meucaixa.application.usecase.category;

import lombok.Builder;

@Builder
public record ListCategoryResponse(Long id,
                                   String title,
                                   String description,
                                   Boolean isDefault) {
}
