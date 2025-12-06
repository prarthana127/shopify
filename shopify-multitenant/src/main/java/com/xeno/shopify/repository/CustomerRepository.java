package com.xeno.shopify.repository;

import com.xeno.shopify.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    long countByTenantId(String tenantId);
}
