package com.clothware.config;

import com.clothware.model.*;
import com.clothware.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private OrderRepository orderRepo;
    @Autowired private SettingsRepository settingsRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // ── Default Admin User
        if (userRepo.findByEmail("admin@clothware.com").isEmpty()) {
            userRepo.save(new User("admin@clothware.com", passwordEncoder.encode("admin123"), "ADMIN"));
        }

        // ── Sample Products
        if (productRepo.count() == 0) {
            productRepo.save(new Product("Cotton T-Shirt", "Tops", 19.99, 120));
            productRepo.save(new Product("Slim Jeans", "Bottoms", 49.99, 85));
            productRepo.save(new Product("Summer Dress", "Dresses", 39.99, 42));
            productRepo.save(new Product("Polo Shirt", "Tops", 24.99, 60));
            productRepo.save(new Product("Chino Pants", "Bottoms", 44.99, 35));
            productRepo.save(new Product("Floral Blouse", "Tops", 29.99, 8)); // Low stock
        }

        // ── Sample Customers
        if (customerRepo.count() == 0) {
            customerRepo.save(new Customer("Emma Watson", "emma@example.com", "555-0101"));
            customerRepo.save(new Customer("James Carter", "james@example.com", "555-0102"));
            customerRepo.save(new Customer("Sarah Johnson", "sarah@example.com", "555-0103"));
        }

        // ── Sample Employees
        if (employeeRepo.count() == 0) {
            employeeRepo.save(new Employee("Sophia Lee", "Store Manager", 4500));
            employeeRepo.save(new Employee("Liam Brown", "Sales Associate", 2800));
            employeeRepo.save(new Employee("Mia Williams", "Cashier", 2200));
        }

        // ── Sample Orders
        if (orderRepo.count() == 0) {
            orderRepo.save(new Order("Emma Watson", "555-0101", 79.98,
                    "[{\"name\":\"Cotton T-Shirt\",\"qty\":2,\"price\":19.99},{\"name\":\"Slim Jeans\",\"qty\":1,\"price\":39.99}]"));
            orderRepo.save(new Order("James Carter", "555-0102", 49.99,
                    "[{\"name\":\"Slim Jeans\",\"qty\":1,\"price\":49.99}]"));
            orderRepo.save(new Order("Sarah Johnson", "555-0103", 39.99,
                    "[{\"name\":\"Summer Dress\",\"qty\":1,\"price\":39.99}]"));
        }

        // ── Default Settings
        if (settingsRepo.count() == 0) {
            settingsRepo.save(new Settings());
        }

        System.out.println("✅ ClothWare backend started. Demo data seeded.");
        System.out.println("📧 Login: admin@clothware.com | Password: admin123");
        System.out.println("🌐 API: http://localhost:8080/api");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
    }
}
