package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithInventoryDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String categoria;
    private Integer cantidad;
    private Integer alertValue;
    private BigDecimal precio;
    private String fechaVencimiento;
    private String status;
    private String loteNumero;
    private Boolean requiereRefrigeracion;
    private Boolean controlado;
}
