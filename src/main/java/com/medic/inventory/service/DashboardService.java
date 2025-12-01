package com.medic.inventory.service;

import com.medic.inventory.dto.DashboardStats;
import com.medic.inventory.entity.Product;
import com.medic.inventory.repository.CategoryRepository;
import com.medic.inventory.repository.OrderRepository;
import com.medic.inventory.repository.ProductRepository;
import com.medic.inventory.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        stats.setTotalCategories(categoryRepository.count());
        stats.setTotalProducts(productRepository.count());

        stats.setCriticalStock(0L);
        stats.setOutOfStock(0L);
        stats.setActiveSuppliers(5L);

        stats.setTotalOrders(orderRepository.count());

        return stats;
    }
}
