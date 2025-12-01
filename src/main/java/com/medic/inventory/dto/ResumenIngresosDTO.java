package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumenIngresosDTO {
    private Long ordenes;
    private Double costoTotal;
    private Long canceladas;
    private Double devueltos;
}
