package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricasDashboardDTO {
    private Long totalProductos;
    private Long productosStockCritico;
    private Long productosStockBajo;
    private Long cantidadTotalStock;
    private Long proveedoresActivos;
    private Long totalCategorias;
    private Long enTransito;

    private ResumenDespachosDTO resumenDespachos;
    private ResumenIngresosDTO resumenIngresos;
}
