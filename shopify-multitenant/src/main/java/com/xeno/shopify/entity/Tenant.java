package com.xeno.shopify.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tenants")
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String shopifyStoreName;

    @Column(nullable = false)
    private String shopifyAccessToken;

    @Column(nullable = false)
    private String shopifyApiKey;

    private String email;
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime lastSyncedAt;
}