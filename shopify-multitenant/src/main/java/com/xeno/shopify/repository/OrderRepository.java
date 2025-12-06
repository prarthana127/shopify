package com.xeno.shopify.repository;

import com.xeno.shopify.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByTenantId(String tenantId);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.tenantId = :tenantId")
    Double sumTotalPriceByTenantId(@Param("tenantId") String tenantId);

    List<Order> findByTenantId(String tenantId);
    List<Order> findByTenantIdOrderByIdDesc(String tenantId);
}
