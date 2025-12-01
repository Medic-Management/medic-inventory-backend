package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteResponse {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private String codigoLote;
    private LocalDate fechaVencimiento;
    private Boolean estado;
    private BigDecimal precioUnitario;
    private Integer cantidadDisponible;  // Para mostrar stock disponible en sede
    private Integer diasHastaVencimiento;  // Para facilitar lógica FEFO en frontend
}
