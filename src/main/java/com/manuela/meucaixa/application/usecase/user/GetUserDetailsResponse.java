package com.manuela.meucaixa.application.usecase.user;

import com.manuela.meucaixa.domain.user.UserRole;
import lombok.Builder;

@Builder
public record GetUserDetailsResponse(String name,
                                     String companyName,
                                     String email,
                                     String phone,
                                     UserRole role) {
}
