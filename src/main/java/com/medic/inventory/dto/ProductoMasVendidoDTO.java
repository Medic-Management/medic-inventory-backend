package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMasVendidoDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Long cantidadVendida;
    private Double facturacion;
    private Double porcentajeIncremento;
    private Integer cantidadRestante;
}
