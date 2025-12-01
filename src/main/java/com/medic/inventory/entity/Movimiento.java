package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "MOVIMIENTOS")
@Data
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ocurrio_en")
    private LocalDateTime ocurrioEn;

    @Column(name = "sede_id")
    private Long sedeId;

    @Column(name = "lote_id")
    private Long loteId;

    @Column(name = "tipo", length = 20)
    private String tipo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "motivo", length = 160)
    private String motivo;

    @Column(name = "doc_ref", length = 80)
    private String docRef;

    @Column(name = "creado_por")
    private Integer creadoPor;
}
