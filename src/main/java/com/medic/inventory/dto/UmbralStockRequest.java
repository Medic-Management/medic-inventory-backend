package com.medic.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UmbralStockRequest {
    @NotNull(message = "La sede es obligatoria")
    private Long sedeId;

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El mínimo es obligatorio")
    private Integer minimo;

    private Integer puntoPedido;
    private Integer stockSeguridad;
}
