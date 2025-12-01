package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatoGraficoMensualDTO {
    private String mes;
    private Integer anio;
    private Long ingresos;
    private Long despachos;
}
