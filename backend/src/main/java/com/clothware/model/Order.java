package com.clothware.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    private String customerPhone;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private String status = "Completed";

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    @Column(columnDefinition = "TEXT")
    private String items; // JSON string of order items

    // ── Constructors
    public Order() {}

    public Order(String customerName, String customerPhone, double total, String items) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.total = total;
        this.items = items;
        this.date = LocalDate.now();
        this.status = "Completed";
    }

    // ── Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
}
