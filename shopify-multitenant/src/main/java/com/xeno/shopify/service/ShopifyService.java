package com.xeno.shopify.service;

import com.xeno.shopify.entity.Customer;
import com.xeno.shopify.entity.Order;
import com.xeno.shopify.entity.Product;
import com.xeno.shopify.repository.CustomerRepository;
import com.xeno.shopify.repository.OrderRepository;
import com.xeno.shopify.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ShopifyService {

    private static final Logger logger = LoggerFactory.getLogger(ShopifyService.class);
    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ShopifyService(RestTemplate restTemplate,
                          CustomerRepository customerRepository,
                          OrderRepository orderRepository,
                          ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // ------------------ SYNC CUSTOMERS ------------------
    public void syncCustomers(String tenantId, String accessToken, String shopUrl) {
        try {
            String url = String.format("https://%s/admin/api/2024-01/customers.json", shopUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Shopify-Access-Token", accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List<Map<String, Object>> customers = (List<Map<String, Object>>) response.getBody().get("customers");

            for (Map<String, Object> data : customers) {
                Customer c = new Customer();
                c.setTenantId(tenantId);
                c.setShopifyCustomerId(String.valueOf(data.get("id")));
                c.setEmail((String) data.get("email"));
                c.setFirstName((String) data.get("first_name"));
                c.setLastName((String) data.get("last_name"));
                c.setPhone((String) data.get("phone"));
                c.setOrdersCount(parseInteger(data.get("orders_count")));
                c.setTotalSpent(parseDouble(data.get("total_spent")));
                c.setCreatedAt(parseDateTime((String) data.get("created_at")));
                customerRepository.save(c);
            }
            logger.info("Synced {} customers for tenant {}", customers.size(), tenantId);
        } catch (Exception e) {
            logger.error("Error syncing customers: {}", e.getMessage());
            throw new RuntimeException("Failed to sync customers", e);
        }
    }

    // ------------------ SYNC ORDERS ------------------
    public void syncOrders(String tenantId, String accessToken, String shopUrl) {
        try {
            String url = String.format("https://%s/admin/api/2024-01/orders.json", shopUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Shopify-Access-Token", accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List<Map<String, Object>> orders = (List<Map<String, Object>>) response.getBody().get("orders");

            for (Map<String, Object> data : orders) {
                Order order = new Order();
                order.setTenantId(tenantId);
                order.setShopifyOrderId(String.valueOf(data.get("id")));
                order.setOrderNumber(String.valueOf(data.get("order_number")));
                order.setCustomerEmail((String) data.get("email"));
                order.setTotalPrice(parseDouble(data.get("total_price")));
                order.setFinancialStatus((String) data.get("financial_status"));
                order.setFulfillmentStatus((String) data.get("fulfillment_status"));
                order.setCurrency((String) data.get("currency"));
                order.setCreatedAt(parseDateTime((String) data.get("created_at")));
                order.setUpdatedAt(parseDateTime((String) data.get("updated_at")));

                // Extract customer_id if exists
                Map<String, Object> customer = (Map<String, Object>) data.get("customer");
                if (customer != null) {
                    order.setCustomerId(String.valueOf(customer.get("id")));
                }

                orderRepository.save(order);
            }
            logger.info("Synced {} orders for tenant {}", orders.size(), tenantId);
        } catch (Exception e) {
            logger.error("Error syncing orders: {}", e.getMessage());
            throw new RuntimeException("Failed to sync orders", e);
        }
    }

    // ------------------ SYNC PRODUCTS ------------------
    public void syncProducts(String tenantId, String accessToken, String shopUrl) {
        try {
            String url = String.format("https://%s/admin/api/2024-01/products.json", shopUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Shopify-Access-Token", accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List<Map<String, Object>> products = (List<Map<String, Object>>) response.getBody().get("products");

            for (Map<String, Object> data : products) {
                Product p = new Product();
                p.setTenantId(tenantId);
                p.setShopifyProductId(String.valueOf(data.get("id")));
                p.setTitle((String) data.get("title"));
                p.setDescription((String) data.get("body_html"));
                p.setProductType((String) data.get("product_type"));
                p.setStatus((String) data.get("status"));
                p.setCreatedAt(parseDateTime((String) data.get("created_at")));
                productRepository.save(p);
            }
            logger.info("Synced {} products for tenant {}", products.size(), tenantId);
        } catch (Exception e) {
            logger.error("Error syncing products: {}", e.getMessage());
            throw new RuntimeException("Failed to sync products", e);
        }
    }

    // ------------------ HELPER METHODS ------------------
    private LocalDateTime parseDateTime(String dateString) {
        if (dateString == null || dateString.isEmpty()) return null;
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            logger.warn("Failed to parse date: {}", dateString);
            return null;
        }
    }

    private Double parseDouble(Object value) {
        if (value == null) return 0.0;
        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private Integer parseInteger(Object value) {
        if (value == null) return 0;
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (Exception e) {
            return 0;
        }
    }
}
