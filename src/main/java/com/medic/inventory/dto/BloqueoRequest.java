package com.medic.inventory.dto;

import lombok.Data;

/**
 * CP021: Request DTO para bloquear/desbloquear productos o lotes
 */
@Data
public class BloqueoRequest {
    private String motivoBloqueo;
    private Integer usuarioId;
}
