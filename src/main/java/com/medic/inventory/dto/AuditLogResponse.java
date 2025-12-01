package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private String accion;
    private String entidadTipo;
    private Long entidadId;
    private String descripcion;
    private String ipAddress;
    private LocalDateTime fechaHora;
}
