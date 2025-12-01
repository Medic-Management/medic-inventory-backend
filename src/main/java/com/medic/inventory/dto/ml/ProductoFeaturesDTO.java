package com.medic.inventory.dto.ml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoFeaturesDTO {

    private Long productoId;
    private String nombre;
    private Double total;
    private Double desabastecidox;
    private Double substockx;
    private Double dispo;
    private Double sobrestockPct;
    private Double probStockPct;
    private Double probStockPctLag1;
    private Double probStockPctDiff1;
    private Double probStockPctMa3;
    private Integer mes;
}
