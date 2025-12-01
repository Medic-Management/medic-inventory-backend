package com.medic.inventory.dto;

import lombok.Data;

@Data
public class UmbralStockResponse {
    private Long id;
    private Long sedeId;
    private String sedeNombre;
    private Long productoId;
    private String productoNombre;
    private Integer minimo;
    private Integer puntoPedido;
    private Integer stockSeguridad;

    // HU-17: Umbral de cobertura en días
    private Integer umbralCoberturaDias;
}
