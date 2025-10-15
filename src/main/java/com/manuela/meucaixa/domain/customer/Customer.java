package com.manuela.meucaixa.domain.customer;

import com.manuela.meucaixa.domain.user.Users;
import com.manuela.meucaixa.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;

    private String name;

    private String code;

    private List<Category> categories = new ArrayList<>();

    private List<Users> users = new ArrayList<>();
}
