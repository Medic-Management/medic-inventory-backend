package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaMasVendidaDTO {
    private String nombre;
    private Double facturacion;
    private Double porcentajeIncremento;
    private Long cantidadProductos;
}
