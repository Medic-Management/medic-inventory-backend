package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCTOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", length = 40)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category categoria;

    @ManyToOne
    @JoinColumn(name = "unidad_id")
    private Unidad unidad;

    @Column(name = "requiere_refrigeracion")
    private Integer requiereRefrigeracion;

    @Column(name = "controlado")
    private Integer controlado;

    @Column(name = "notas", length = 255)
    private String notas;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "precio_venta", precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @Transient
    private String name;

    @Transient
    private String batchNumber;

    @Transient
    private LocalDate expirationDate;

    @Transient
    private Integer quantity = 0;

    @Transient
    private Integer alertValue;

    @Transient
    private BigDecimal purchasePrice;

    @Transient
    private BigDecimal salePrice;

    @Transient
    private String observations;

    @Transient
    private String imageUrl;

    @Transient
    private ProductStatus status = ProductStatus.IN_STOCK;

    @Transient
    private Integer initialStock = 0;

    @Transient
    private Integer inTransit = 0;

    @Transient
    private Category category;

    @Transient
    private Supplier supplier;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        syncFromLegacy();
    }

    @PostLoad
    protected void onLoad() {
        syncToLegacy();
    }

    @PreUpdate
    protected void onUpdate() {
        syncFromLegacy();
    }

    private void syncFromLegacy() {
        if (name != null && !name.isEmpty()) {
            nombre = name;
        }
        if (observations != null) {
            notas = observations;
        }
        if (category != null) {
            categoria = category;
        }
    }

    private void syncToLegacy() {
        name = nombre;
        observations = notas;
        category = categoria;
    }

    public String getName() {
        return nombre != null ? nombre : name;
    }

    public void setName(String name) {
        this.name = name;
        this.nombre = name;
    }

    public Category getCategory() {
        return categoria != null ? categoria : category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.categoria = category;
    }

    public String getObservations() {
        return notas != null ? notas : observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
        this.notas = observations;
    }

    public enum ProductStatus {
        IN_STOCK,
        LOW_STOCK,
        CRITICAL,
        OUT_OF_STOCK
    }
}
