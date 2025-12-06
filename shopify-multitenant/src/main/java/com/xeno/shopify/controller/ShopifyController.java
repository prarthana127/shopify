package com.xeno.shopify.controller;

import com.xeno.shopify.service.ShopifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopify")
public class ShopifyController {

    @Autowired
    private ShopifyService shopifyService;

    @PostMapping("/sync/customers")
    public ResponseEntity<String> syncCustomers(
            @RequestParam String tenantId,
            @RequestParam String shopifyAccessToken,
            @RequestParam String shopUrl) {
        shopifyService.syncCustomers(tenantId, shopifyAccessToken, shopUrl);
        return ResponseEntity.ok("Customers synced successfully");
    }

    @PostMapping("/sync/orders")
    public ResponseEntity<String> syncOrders(
            @RequestParam String tenantId,
            @RequestParam String shopifyAccessToken,
            @RequestParam String shopUrl) {
        shopifyService.syncOrders(tenantId, shopifyAccessToken, shopUrl);
        return ResponseEntity.ok("Orders synced successfully");
    }

    @PostMapping("/sync/products")
    public ResponseEntity<String> syncProducts(
            @RequestParam String tenantId,
            @RequestParam String shopifyAccessToken,
            @RequestParam String shopUrl) {
        shopifyService.syncProducts(tenantId, shopifyAccessToken, shopUrl);
        return ResponseEntity.ok("Products synced successfully");
    }
}
