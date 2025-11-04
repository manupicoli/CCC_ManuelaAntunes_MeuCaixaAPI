package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCategoryEntity;
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
    Optional<JpaCategoryEntity> findByIdAndCustomerCodeOrDefault(Long id, String customerCode);}
