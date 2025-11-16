package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaFinancialRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFinancialRecordRepository extends JpaRepository<JpaFinancialRecordEntity, Long> {

    Optional<JpaFinancialRecordEntity> findByIdAndCustomerCode(Long id, String customerCode);

    @Query("""
        SELECT f FROM JpaFinancialRecordEntity f
        WHERE (:qs IS NULL OR
            LOWER(f.description) LIKE LOWER(CONCAT('%', :qs, '%')) OR
            LOWER(f.category.title) LIKE LOWER(CONCAT('%', :qs, '%')) OR
            LOWER(CAST(f.type AS string)) LIKE LOWER(CONCAT('%', :qs, '%')) OR
            (CAST(f.amount AS string) LIKE CONCAT('%', :qs, '%')))
            AND f.customer.code = :customerCode
    """)
    Page<JpaFinancialRecordEntity> search(String customerCode, String qs, Pageable pageable);

    List<JpaFinancialRecordEntity> findAllByCustomerCode(String customerCode);
}
