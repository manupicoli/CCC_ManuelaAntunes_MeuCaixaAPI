package com.manuela.meucaixa.domain.category;

import java.util.List;

public interface CategoryRepository {

    Category save(Category category);

    Category findById(Long id);

    List<Category> findAll();

    void deleteById(Long id);

}
