package com.clothware.repository;
import com.clothware.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(String status);
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status = 'Completed'")
    Double totalRevenue();
    List<Order> findTop5ByOrderByIdDesc();
}
