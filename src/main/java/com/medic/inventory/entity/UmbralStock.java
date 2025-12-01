package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "UMBRAL_STOCK")
@Data
public class UmbralStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Product producto;

    @Column(name = "minimo")
    private Integer minimo;

    @Column(name = "punto_pedido")
    private Integer puntoPedido;

    @Column(name = "stock_seguridad")
    private Integer stockSeguridad;

    // HU-17: Umbral de cobertura en días (stock/consumo_diario)
    @Column(name = "umbral_cobertura_dias")
    private Integer umbralCoberturaDias = 15;

    @Column(name = "Column5")
    private String column5;

    @Column(name = "Column7")
    private String column7;
}
