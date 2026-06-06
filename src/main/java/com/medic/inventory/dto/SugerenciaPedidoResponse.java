package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDate;

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

    // CP007: Información complementaria para análisis operativo
    private Integer consumoPromedioMensual;
    private String tendencia; // CRECIENTE, ESTABLE, DECRECIENTE
    private Integer diasParaAgotamiento; // Días hasta agotamiento del stock
    private LocalDate fechaEstimadaReposicion; // Fecha estimada de llegada del pedido
}
