package com.xeno.shopify.controller;

import com.xeno.shopify.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary(
            @RequestParam String tenantId) {
        Map<String, Object> summary = dashboardService.getSummary(tenantId);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/recent-orders")
    public ResponseEntity<Map<String, Object>> getRecentOrders(
            @RequestParam String tenantId,
            @RequestParam(defaultValue = "10") int limit) {
        Map<String, Object> orders = dashboardService.getRecentOrders(tenantId, limit);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/top-customers")
    public ResponseEntity<Map<String, Object>> getTopCustomers(
            @RequestParam String tenantId,
            @RequestParam(defaultValue = "5") int limit) {
        Map<String, Object> customers = dashboardService.getTopCustomers(tenantId, limit);
        return ResponseEntity.ok(customers);
    }
}
