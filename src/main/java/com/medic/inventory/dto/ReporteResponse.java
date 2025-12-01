package com.medic.inventory.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReporteResponse {
    private Long totalProductos;
    private Long totalMovimientos;
    private Long totalAlertas;
    private Long totalSolicitudes;
    private Long stockTotal;
    private Long productosStockCritico;
    private Long lotesProximosVencer;

    private Double gananciaTotal;
    private Double ingresosTotal;
    private Double ventasTotal;
    private Double valorNetoCompras;
    private Double valorNetoVentas;
    private Double gananciaMensual;
    private Double gananciaAnual;

    private List<ProductoMasVendidoDTO> productosMasVendidos;
    private List<CategoriaMasVendidaDTO> categoriasMasVendidas;
}
