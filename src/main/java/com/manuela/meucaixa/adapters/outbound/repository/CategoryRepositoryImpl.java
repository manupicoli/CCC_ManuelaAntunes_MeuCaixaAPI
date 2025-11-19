package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCategoryEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.category.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;

    @Transactional
    @Override
    public Category save(Category category) {
        final var categoryEntity = getCategoryEntity(category);
        final var saved = jpaCategoryRepository.save(categoryEntity);

        return getCategory(saved);
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        final var categoryEntity = jpaCategoryRepository.findById(categoryId);
        return categoryEntity.map(CategoryRepositoryImpl::getCategory);
    }

    @Transactional
    @Override
    public Optional<Category> findByIdAndCustomerCode(Long id, String customerCode) {
        final var categoryEntity = jpaCategoryRepository.findByIdAndCustomerCode(id, customerCode);
        return categoryEntity.map(CategoryRepositoryImpl::getCategory);
    }

    @Transactional
    @Override
    public Page<Category> findAllByCustomerCode(String customerCode, String qs, Pageable pageable) {
       return jpaCategoryRepository.findAllByCustomerCodeOrDefault(customerCode, qs, pageable)
           .map(CategoryRepositoryImpl::getCategory);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        jpaCategoryRepository.deleteById(id);
    }

    private JpaCategoryEntity getCategoryEntity(final Category category) {
        return JpaCategoryEntity.builder()
            .id(category.getId())
            .title(category.getTitle())
            .description(category.getDescription())
            .isDefault(category.getIsDefault())
            .customer(getCustomerEntityId(category))
            .build();
    }

    private JpaCustomerEntity getCustomerEntityId(final Category category) {
        if (category.getCustomer() == null) return null;

        return JpaCustomerEntity.builder()
            .id(category.getCustomer().getId())
            .build();
    }

    private static Category getCategory(final JpaCategoryEntity e) {
        return Category.builder()
            .id(e.getId())
            .title(e.getTitle())
            .description(e.getDescription())
            .isDefault(e.getIsDefault())
            .customer(e.getCustomer() != null
                ? Customer.builder()
                    .id(e.getCustomer().getId())
                    .name(e.getCustomer().getName())
                    .code(e.getCustomer().getCode())
                    .build()
                : null)
            .build();
    }
}
