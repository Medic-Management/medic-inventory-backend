package com.medic.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String batchNumber;
    private LocalDate expirationDate;
    private Integer quantity;
    private Integer alertValue;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private String observations;
    private String imageUrl;
    private String status;
    private Integer initialStock;
    private Integer inTransit;
    private String categoryName;
    private String supplierName;
    private String supplierContact;

    // CP021: Campos de bloqueo por retiro sanitario
    private Boolean bloqueado;
    private String motivoBloqueo;
    private LocalDateTime bloqueadoEn;
    private Integer bloqueadoPor;
}
