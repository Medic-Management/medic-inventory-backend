package com.medic.inventory.dto;

import lombok.Data;

@Data
public class SugerenciaPedidoResponse {
    private Long productoId;
    private String productoNombre;
    private Long proveedorId;
    private String proveedorNombre;
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer cantidadSugerida;
    private String justificacion;
    private String criticidad; // ALTA, MEDIA, BAJA
}
