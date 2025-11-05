package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCategoryRepository extends JpaRepository<JpaCategoryEntity, Long> {

    @Query("""
        SELECT c
        FROM JpaCategoryEntity c
        WHERE c.id = :id
        AND (c.customer.code = :customerCode OR c.customer IS NULL)
        """)
    Optional<JpaCategoryEntity> findByIdAndCustomerCodeOrDefault(Long id, String customerCode);

    @Query("""
        SELECT c
        FROM JpaCategoryEntity c
        WHERE (:qs IS NULL OR
            LOWER(c.title) LIKE LOWER(CONCAT('%', :qs, '%')) OR
            LOWER(c.description) LIKE LOWER(CONCAT('%', :qs, '%')))
            AND (c.customer.code = :customerCode OR c.customer IS NULL)
        """)
    Page<JpaCategoryEntity> findAllByCustomerCodeAndDefault(String customerCode, String qs, Pageable pageable);
}
