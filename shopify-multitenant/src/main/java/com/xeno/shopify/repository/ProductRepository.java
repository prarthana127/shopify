package com.xeno.shopify.repository;

import com.xeno.shopify.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    long countByTenantId(String tenantId);
}
