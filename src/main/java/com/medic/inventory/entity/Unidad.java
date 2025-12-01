package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "UNIDADES")
@Data
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @Column(name = "Column4")
    private String column4;

    @Column(name = "Column5")
    private String column5;

    @Column(name = "Column7")
    private String column7;
}
