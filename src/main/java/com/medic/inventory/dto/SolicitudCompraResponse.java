package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudCompraResponse {
    private Long id;
    private Long sedeId;
    private String sedeNombre;
    private Long productoId;
    private String productoNombre;
    private Long proveedorId;
    private String proveedorNombre;
    private Integer cantidadSolicitada;
    private String estado;
    private String notas;
    private Boolean emailEnviado;
    private Long solicitadoPorId;
    private String solicitadoPorNombre;
    private LocalDateTime creadaEn;
}
