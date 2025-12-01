package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertaResponse {
    private Long id;
    private Long sedeId;
    private String sedeNombre;
    private Long productoId;
    private String productoNombre;
    private String tipo;
    private String nivel;
    private LocalDateTime disparadaEn;
    private LocalDateTime resueltaEn;
    private Boolean activa;
    private Integer stockActual;
    private Integer nivelAlerta;
    private String sugerencia;
}
