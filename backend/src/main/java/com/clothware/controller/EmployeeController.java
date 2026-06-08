package com.clothware.controller;

import com.clothware.model.Employee;
import com.clothware.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @GetMapping
    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return employeeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeRepo.save(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id,
                                           @Valid @RequestBody Employee updated) {
        return employeeRepo.findById(id).map(e -> {
            e.setName(updated.getName());
            e.setRole(updated.getRole());
            e.setSalary(updated.getSalary());
            return ResponseEntity.ok(employeeRepo.save(e));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        if (!employeeRepo.existsById(id)) return ResponseEntity.notFound().build();
        employeeRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Employee deleted"));
    }
}
