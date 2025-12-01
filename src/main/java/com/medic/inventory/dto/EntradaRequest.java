package com.medic.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EntradaRequest {
    @NotNull(message = "El ID del producto es requerido")
    private Long productoId;

    @NotNull(message = "El código de lote es requerido")
    private String codigoLote;

    @NotNull(message = "La fecha de vencimiento es requerida")
    private String fechaVencimiento;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    private Long proveedorId;
    private String documentoReferencia;
    private String observaciones;

    // HU-01 Escenario 2: Campo para confirmar registro de lote próximo a vencer
    private Boolean confirmarVencimientoCercano = false;
}
