package com.medic.inventory.dto;

import lombok.Data;

@Data
public class TrabajoRpaRequest {
    private Long alertaId;
    private String orchestratorJobId;
    private String emailMessageId;
}
