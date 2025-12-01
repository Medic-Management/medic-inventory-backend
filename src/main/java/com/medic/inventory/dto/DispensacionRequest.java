package com.medic.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DispensacionRequest {
    @NotNull(message = "El ID del producto es requerido")
    private Long productoId;

    @NotNull(message = "El ID del lote es requerido")
    private Long loteId;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    private String motivo;
    private String documentoReferencia;
}
