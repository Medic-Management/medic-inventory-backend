package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ROLES")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;
}
