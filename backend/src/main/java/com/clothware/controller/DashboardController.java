package com.clothware.controller;

import com.clothware.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired private OrderRepository orderRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private EmployeeRepository employeeRepo;

    /** GET /api/dashboard/stats - summary stats for dashboard */
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Double revenue = orderRepo.totalRevenue();
        long lowStockCount = productRepo.findByStockLessThan(10).size();

        return Map.of(
            "totalSales",     revenue != null ? revenue : 0.0,
            "totalOrders",    orderRepo.count(),
            "totalProducts",  productRepo.count(),
            "totalCustomers", customerRepo.count(),
            "totalEmployees", employeeRepo.count(),
            "lowStockCount",  lowStockCount,
            "recentOrders",   orderRepo.findTop5ByOrderByIdDesc()
        );
    }

    /** GET /api/dashboard/report */
    @GetMapping("/report")
    public Map<String, Object> getReport() {
        Double revenue = orderRepo.totalRevenue();
        long completedOrders = orderRepo.findByStatus("Completed").size();
        long lowStock = productRepo.findByStockLessThan(10).size();

        return Map.of(
            "totalRevenue",    revenue != null ? revenue : 0.0,
            "totalOrders",     orderRepo.count(),
            "completedOrders", completedOrders,
            "pendingOrders",   orderRepo.findByStatus("Pending").size(),
            "totalProducts",   productRepo.count(),
            "lowStockItems",   lowStock,
            "totalCustomers",  customerRepo.count(),
            "totalEmployees",  employeeRepo.count()
        );
    }
}
