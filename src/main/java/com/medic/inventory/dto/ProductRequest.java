package com.medic.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    // HU-12: Código del producto (opcional, pero debe ser único si se proporciona)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String batchNumber;

    private LocalDate expirationDate;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer quantity;

    private Integer alertValue;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;

    private String observations;

    private String imageUrl;

    private Long categoryId;

    private Long supplierId;
}
