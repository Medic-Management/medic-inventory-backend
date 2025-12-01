package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTAS")
@Data
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id")
    private Sede sede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Product producto;

    @Column(name = "tipo", length = 20)
    private String tipo;

    @Column(name = "nivel", length = 20)
    private String nivel;

    @Column(name = "disparada_en")
    private LocalDateTime disparadaEn;

    @Column(name = "resuelta_en")
    private LocalDateTime resueltaEn;

    @Column(name = "activa")
    private Integer activa;

    @Column(name = "nota", length = 255)
    private String nota;
}
