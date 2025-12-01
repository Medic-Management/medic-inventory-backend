package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumenDespachosDTO {
    private Long despachados;
    private Long entregadas;
    private Double ganancia;
    private Double costoTotal;
}
