package com.xeno.shopify.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customers_tenant_id", columnList = "tenantId")
})
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String shopifyCustomerId;

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Integer ordersCount;
    private Double totalSpent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
