package com.medic.inventory.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TrabajoRpaResponse {
    private Long id;
    private Long solicitudId;
    private String orchestratorJobId;
    private String emailMessageId;
    private String estado;
    private LocalDateTime solicitadoEn;
    private LocalDateTime finalizadoEn;
}
