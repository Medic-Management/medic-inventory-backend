package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CATEGORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(name = "Column3")
    private String column3;

    @Column(name = "Column4")
    private String column4;

    @Column(name = "Column5")
    private String column5;

    @Column(name = "Column7")
    private String column7;

    @Transient
    private String name;

    @Transient
    private String description;

    @Transient
    private List<Product> products;

    @PostLoad
    protected void onLoad() {
        name = nombre;
    }

    @PrePersist
    protected void onCreate() {
        if (name != null && !name.isEmpty()) {
            nombre = name;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (name != null && !name.isEmpty()) {
            nombre = name;
        }
    }

    public String getName() {
        return nombre != null ? nombre : name;
    }

    public void setName(String name) {
        this.name = name;
        this.nombre = name;
    }
}
