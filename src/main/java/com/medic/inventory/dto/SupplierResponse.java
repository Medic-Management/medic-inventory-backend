package com.medic.inventory.dto;

import lombok.Data;

@Data
public class SupplierResponse {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Integer leadTimeDays;
    private Integer moq;
    private Boolean activo;
}
