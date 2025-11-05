package com.manuela.meucaixa.domain.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findByIdAndCustomerCode(Long id, String customerCode);

    Page<Category> findAllByCustomerCode(String customerCode, String qs, Pageable pageable);

    void deleteById(Long id);

}
