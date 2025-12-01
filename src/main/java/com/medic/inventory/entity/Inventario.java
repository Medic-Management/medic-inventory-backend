package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "INVENTARIO")
@IdClass(InventarioId.class)
@Data
public class Inventario {

    @Id
    @Column(name = "sede_id")
    private Long sedeId;

    @Id
    @Column(name = "lote_id")
    private Long loteId;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "Column4")
    private String column4;

    @Column(name = "Column5")
    private String column5;

    @Column(name = "Column6")
    private String column6;

    @Column(name = "Column7")
    private String column7;
}
