package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AlertaVencimientoResponse {
    private Long loteId;
    private String codigoLote;
    private Long productoId;
    private String productoNombre;
    private LocalDate fechaVencimiento;
    private Integer diasRestantes;
    private String nivel;
    private Integer cantidadTotal;
}
