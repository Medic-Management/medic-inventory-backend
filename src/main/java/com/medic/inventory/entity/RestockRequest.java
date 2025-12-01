package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "SOLICITUDES_COMPRA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestockRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id")
    private Sede sede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitado_por")
    private User solicitadoPor;

    @Column(name = "aprobada")
    private Boolean aprobada;

    @Column(name = "origen")
    private Integer origen;

    @Column(name = "notas", length = 255)
    private String notas;

    @Column(name = "creada_en")
    private LocalDateTime creadaEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "requested_quantity")
    private Integer requestedQuantity;

    @Column(name = "current_stock")
    private Integer currentStock;

    @Column(name = "alert_level")
    private Integer alertLevel;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RestockStatus status = RestockStatus.PENDING;

    @Column(name = "request_date")
    private LocalDateTime requestDate = LocalDateTime.now();

    @Transient
    private LocalDateTime expectedDelivery;

    @Transient
    private LocalDateTime actualDelivery;

    @Transient
    private String notes;

    @Column(name = "email_sent", nullable = false)
    private Boolean emailSent = false;

    @Column(name = "email_subject", length = 500)
    private String emailSubject;

    @Column(name = "email_body", columnDefinition = "TEXT")
    private String emailBody;


    @PrePersist
    protected void onCreate() {
        creadaEn = LocalDateTime.now();
    }

    public enum RestockStatus {
        PENDING,
        SENT,
        CONFIRMED,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED
    }
}
