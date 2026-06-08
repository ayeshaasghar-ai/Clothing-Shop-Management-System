package com.clothware.controller;

import com.clothware.model.Product;
import com.clothware.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    /** GET /api/products - list all */
    @GetMapping
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    /** GET /api/products/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/products/low-stock - items with stock < 10 */
    @GetMapping("/low-stock")
    public List<Product> getLowStock() {
        return productRepo.findByStockLessThan(10);
    }

    /** POST /api/products - create */
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productRepo.save(product));
    }

    /** PUT /api/products/{id} - update */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @Valid @RequestBody Product updated) {
        return productRepo.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setCategory(updated.getCategory());
            p.setPrice(updated.getPrice());
            p.setStock(updated.getStock());
            return ResponseEntity.ok(productRepo.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/products/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        if (!productRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Product deleted"));
    }
}
