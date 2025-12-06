package com.xeno.shopify.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_products_tenant_id", columnList = "tenantId")
})
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String shopifyProductId;

    private String title;
    private String vendor;
    private String productType;
    private String status;

    @Column(length = 2000)
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
