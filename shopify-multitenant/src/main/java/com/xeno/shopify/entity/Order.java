package com.xeno.shopify.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_orders_tenant_id", columnList = "tenantId"),
        @Index(name = "idx_customer_id", columnList = "customerId")
})
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String shopifyOrderId;

    private String customerId;
    private String customerEmail;
    private String orderNumber;
    private Double totalPrice;
    private String currency;
    private String financialStatus;
    private String fulfillmentStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
