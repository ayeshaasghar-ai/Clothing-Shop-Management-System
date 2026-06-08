package com.clothware.controller;

import com.clothware.model.Order;
import com.clothware.model.Product;
import com.clothware.repository.OrderRepository;
import com.clothware.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired private OrderRepository orderRepo;
    @Autowired private ProductRepository productRepo;

    @GetMapping
    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return orderRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recent")
    public List<Order> getRecent() {
        return orderRepo.findTop5ByOrderByIdDesc();
    }

    /**
     * POST /api/orders/generate-bill
     * Body: { customerName, customerPhone, items: [{productId, qty}] }
     */
    @PostMapping("/generate-bill")
    public ResponseEntity<?> generateBill(@RequestBody Map<String, Object> body) {
        String customerName = (String) body.getOrDefault("customerName", "Walk-in Customer");
        String customerPhone = (String) body.getOrDefault("customerPhone", "");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");

        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No items provided"));
        }

        double total = 0;
        StringBuilder itemsJson = new StringBuilder("[");

        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = items.get(i);
            Long productId = Long.valueOf(item.get("productId").toString());
            int qty = Integer.parseInt(item.get("qty").toString());

            Product product = productRepo.findById(productId).orElse(null);
            if (product == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Product not found: " + productId));
            }
            if (product.getStock() < qty) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Insufficient stock for: " + product.getName()));
            }

            // Deduct stock
            product.setStock(product.getStock() - qty);
            productRepo.save(product);

            double lineTotal = product.getPrice() * qty;
            total += lineTotal;

            if (i > 0) itemsJson.append(",");
            itemsJson.append(String.format(
                "{\"name\":\"%s\",\"qty\":%d,\"price\":%.2f,\"total\":%.2f}",
                product.getName(), qty, product.getPrice(), lineTotal
            ));
        }
        itemsJson.append("]");

        Order order = new Order(customerName, customerPhone, total, itemsJson.toString());
        order = orderRepo.save(order);

        return ResponseEntity.ok(Map.of(
            "orderId", order.getId(),
            "customerName", customerName,
            "total", total,
            "items", itemsJson.toString(),
            "date", order.getDate().toString(),
            "message", "Bill generated successfully"
        ));
    }

    /** PUT /api/orders/{id}/status */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestBody Map<String, String> body) {
        return orderRepo.findById(id).map(o -> {
            o.setStatus(body.get("status"));
            return ResponseEntity.ok(orderRepo.save(o));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        if (!orderRepo.existsById(id)) return ResponseEntity.notFound().build();
        orderRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Order deleted"));
    }
}
