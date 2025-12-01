package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PROVEEDORES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telefono", length = 40)
    private String telefono;

    @Column(name = "Column5")
    private String column5;

    @Column(name = "Column6")
    private String column6;

    @Column(name = "Column7")
    private String column7;

    @Transient
    private String name;

    @Transient
    private String contactNumber;

    @Transient
    private String address;

    @Column(name = "activo")
    private Boolean isActive = true;

    @Transient
    private List<Product> products;

    @PostLoad
    protected void onLoad() {
        name = nombre;
        contactNumber = telefono;
    }

    @PrePersist
    protected void onCreate() {
        if (name != null && !name.isEmpty()) {
            nombre = name;
        }
        if (contactNumber != null && !contactNumber.isEmpty()) {
            telefono = contactNumber;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (name != null && !name.isEmpty()) {
            nombre = name;
        }
        if (contactNumber != null && !contactNumber.isEmpty()) {
            telefono = contactNumber;
        }
    }

    public String getName() {
        return nombre != null ? nombre : name;
    }

    public void setName(String name) {
        this.name = name;
        this.nombre = name;
    }

    public String getContactNumber() {
        return telefono != null ? telefono : contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        this.telefono = contactNumber;
    }
}
