package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "PRONOSTICO")
@Data
public class Pronostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sede_id")
    private Long sedeId;

    @Column(name = "producto_id")
    private Long productoId;

    @Column(name = "periodo_inicio")
    private LocalDate periodoInicio;

    @Column(name = "periodo_fin")
    private LocalDate periodoFin;

    @Column(name = "metodo", length = 40)
    private String metodo;

    @Column(name = "demanda_prevista")
    private Integer demandaPrevista;

    @Column(name = "stock_seguridad")
    private Integer stockSeguridad;
}
