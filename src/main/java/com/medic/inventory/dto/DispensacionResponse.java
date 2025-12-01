package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DispensacionResponse {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private Long loteId;
    private String codigoLote;
    private Integer cantidad;
    private String motivo;
    private String documentoReferencia;
    private LocalDateTime ocurrioEn;
    private Long dispensadoPor;
    private String dispensadoPorNombre;
}
