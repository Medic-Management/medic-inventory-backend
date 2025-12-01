package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRABAJOS_RPA")
@Data
public class TrabajoRpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitud_id")
    private Long solicitudId;

    @Column(name = "orchestrator_job_id", length = 80)
    private String orchestratorJobId;

    @Column(name = "email_message_id", length = 120)
    private String emailMessageId;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "solicitado_en")
    private LocalDateTime solicitadoEn;

    @Column(name = "finalizado_en")
    private LocalDateTime finalizadoEn;
}
