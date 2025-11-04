package com.manuela.meucaixa.domain.category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findByIdAndCustomerCode(Long id, String customerCode);

    List<Category> findAll();

    void deleteById(Long id);

}
