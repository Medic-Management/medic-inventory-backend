package com.medic.inventory.repository;

import com.medic.inventory.entity.Order;
import com.medic.inventory.entity.Order.OrderStatus;
import com.medic.inventory.entity.Order.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByType(OrderType type);
    List<Order> findBySupplierId(Long supplierId);

    @Query("SELECT o FROM Order o WHERE o.status IN ('PENDING', 'CONFIRMED', 'IN_TRANSIT') ORDER BY o.orderDate DESC")
    List<Order> findActiveOrders();
}
