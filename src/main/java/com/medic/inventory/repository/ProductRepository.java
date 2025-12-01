package com.medic.inventory.repository;

import com.medic.inventory.entity.Product;
import com.medic.inventory.entity.Product.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // @Query("SELECT p FROM Product p WHERE p.status = 'CRITICAL' OR p.status = 'OUT_OF_STOCK'")
    // List<Product> findCriticalProducts();
}
