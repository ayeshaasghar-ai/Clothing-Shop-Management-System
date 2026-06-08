package com.clothware.controller;

import com.clothware.model.Customer;
import com.clothware.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return customerRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerRepo.save(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id,
                                           @Valid @RequestBody Customer updated) {
        return customerRepo.findById(id).map(c -> {
            c.setName(updated.getName());
            c.setEmail(updated.getEmail());
            c.setPhone(updated.getPhone());
            return ResponseEntity.ok(customerRepo.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        if (!customerRepo.existsById(id)) return ResponseEntity.notFound().build();
        customerRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Customer deleted"));
    }
}
