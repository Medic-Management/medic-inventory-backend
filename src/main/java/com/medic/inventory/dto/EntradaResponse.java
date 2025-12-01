package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EntradaResponse {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private Long loteId;
    private String codigoLote;
    private Integer cantidad;
    private String documentoReferencia;
    private LocalDateTime ocurrioEn;
    private Long registradoPor;
    private String registradoPorNombre;
    private Integer stockAnterior;
    private Integer stockNuevo;
    private Long proveedorId;
    private String proveedorNombre;
}
