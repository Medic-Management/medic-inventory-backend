package com.medic.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudCompraRequest {
    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El proveedor es obligatorio")
    private Long proveedorId;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidadSolicitada;

    private String notas;
}
