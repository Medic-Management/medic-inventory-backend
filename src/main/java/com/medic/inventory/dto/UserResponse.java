package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String nombreCompleto;
    private String email;
    private Long rolId;
    private String rolNombre;
    private Integer activo;
    private LocalDateTime creadoEn;
}
