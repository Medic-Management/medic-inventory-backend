package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "settings")
@Data
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String language = "es";
    private String timezone = "America/Lima";
    private String dateFormat = "dd/mm/yyyy";

    private Boolean notifyLowStock = true;
    private Boolean notifyExpiring = true;
    private Boolean notifyNewOrders = true;
    private Boolean notifyByEmail = false;

    private Integer alertValue = 10;
    private Integer expirationDays = 30;
    private Boolean autoUpdate = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
