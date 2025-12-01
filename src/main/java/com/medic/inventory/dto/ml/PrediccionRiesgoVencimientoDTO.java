package com.medic.inventory.dto.ml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrediccionRiesgoVencimientoDTO {

    private Long productoId;
    private String nombre;
    private Boolean tieneRiesgo;
    private Double probabilidad;
    private String nivelRiesgo;
    private String recomendacion;
}
