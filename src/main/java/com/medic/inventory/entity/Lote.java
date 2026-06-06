package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "LOTES")
@Data
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Product producto;

    @Column(name = "codigo_producto_prov", length = 80)
    private String codigoProductoProv;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // CP021: Bloqueo por retiro sanitario
    @Column(name = "bloqueado")
    private Boolean bloqueado = false;

    @Column(name = "motivo_bloqueo", length = 255)
    private String motivoBloqueo;

    @Column(name = "bloqueado_en")
    private LocalDate bloqueadoEn;

    @Column(name = "bloqueado_por")
    private Integer bloqueadoPor;
}
