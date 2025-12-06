package com.xeno.shopify.service;

import com.xeno.shopify.entity.Customer;
import com.xeno.shopify.entity.Order;
import com.xeno.shopify.entity.Product;
import com.xeno.shopify.repository.CustomerRepository;
import com.xeno.shopify.repository.OrderRepository;
import com.xeno.shopify.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public DashboardService(CustomerRepository customerRepository,
                            OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // 1. Get Summary Stats
    public Map<String, Object> getSummary(String tenantId) {
        long totalCustomers = customerRepository.countByTenantId(tenantId);
        long totalOrders = orderRepository.countByTenantId(tenantId);
        long totalProducts = productRepository.countByTenantId(tenantId);

        Double totalRevenue = orderRepository.sumTotalPriceByTenantId(tenantId);
        if (totalRevenue == null) totalRevenue = 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalCustomers", totalCustomers);
        summary.put("totalOrders", totalOrders);
        summary.put("totalProducts", totalProducts);
        summary.put("totalRevenue", totalRevenue);

        return summary;
    }

    // 2. Get Recent Orders
    public Map<String, Object> getRecentOrders(String tenantId, int limit) {
        List<Order> orders = orderRepository.findByTenantIdOrderByIdDesc(tenantId)
                .stream()
                .limit(limit)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orders);
        response.put("count", orders.size());

        return response;
    }

    // 3. Get Top Customers (by number of orders)
    public Map<String, Object> getTopCustomers(String tenantId, int limit) {
        List<Order> orders = orderRepository.findByTenantId(tenantId);

        Map<String, Long> customerOrderCount = orders.stream()
                .filter(o -> o.getCustomerEmail() != null)
                .collect(Collectors.groupingBy(Order::getCustomerEmail, Collectors.counting()));

        List<Map<String, Object>> topCustomers = customerOrderCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    Map<String, Object> customer = new HashMap<>();
                    customer.put("email", entry.getKey());
                    customer.put("orderCount", entry.getValue());
                    return customer;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("customers", topCustomers);
        response.put("count", topCustomers.size());

        return response;
    }
}
