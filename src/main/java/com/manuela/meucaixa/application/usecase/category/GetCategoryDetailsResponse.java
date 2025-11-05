package com.manuela.meucaixa.application.usecase.category;

import lombok.Builder;

@Builder
public record GetCategoryDetailsResponse(String title,
                                         String description,
                                         Boolean isDefault) {
}
