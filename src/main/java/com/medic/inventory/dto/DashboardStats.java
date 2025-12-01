package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    private Long totalCategories;
    private Long totalProducts;
    private Long criticalStock;
    private Long outOfStock;
    private Long activeSuppliers;
    private Long totalOrders;
}
