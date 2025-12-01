package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", length = 120)
    private String nombreCompleto;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Role rol;

    @Column(name = "activo")
    private Integer activo;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Transient
    private String password;

    @Transient
    private String role;

    @Transient
    private String fullName;

    @Transient
    private String rememberToken;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        if (activo == null) {
            activo = 1;
        }
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
        if (password != null && !password.isEmpty()) {
            passwordHash = password;
        }
        if (fullName != null && !fullName.isEmpty()) {
            nombreCompleto = fullName;
        }
        if (role != null && !role.isEmpty() && rol == null) {
        }
    }

    private void syncToLegacy() {
        password = passwordHash;
        fullName = nombreCompleto;
        if (rol != null) {
            role = rol.getNombre();
        }
    }

    public void setPassword(String password) {
        this.password = password;
        this.passwordHash = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        this.nombreCompleto = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return passwordHash != null ? passwordHash : password;
    }

    public String getFullName() {
        return nombreCompleto != null ? nombreCompleto : fullName;
    }

    public String getRole() {
        if (rol != null) {
            return rol.getNombre();
        }
        return role;
    }

    public void setRememberToken(String token) {
        this.rememberToken = token;
    }

    public String getRememberToken() {
        return rememberToken;
    }
}
