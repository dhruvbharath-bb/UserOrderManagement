package com.example.UserOrderManagement.Repository;

import com.example.UserOrderManagement.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    // Fetches all orders that belong to a specific user
    List<Order> findByUserId(Long userId);

    // Fetches a specific order belonging to a user
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
