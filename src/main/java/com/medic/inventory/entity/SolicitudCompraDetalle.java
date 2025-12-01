package com.medic.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SOLICITUDES_COMPRA_DET")
@Data
public class SolicitudCompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", nullable = false)
    private RestockRequest solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Product producto;

    @Column(name = "cantidad_sugerida")
    private Integer cantidadSugerida;

    @Column(name = "motivo", length = 160)
    private String motivo;

    @Column(name = "Column6")
    private String column6;

    @Column(name = "Column7")
    private String column7;
}
