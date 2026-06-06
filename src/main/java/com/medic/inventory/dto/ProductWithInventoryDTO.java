package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    // CP021: Campos de bloqueo por retiro sanitario
    private Boolean bloqueado;
    private String motivoBloqueo;
    private LocalDateTime bloqueadoEn;
    private Integer bloqueadoPor;
}
