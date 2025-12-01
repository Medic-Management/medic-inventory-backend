package com.medic.inventory.dto.ml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrediccionPicoDemandaDTO {

    private Long productoId;
    private String nombre;
    private Boolean tienePicoDemanda;
    private Double probabilidad;
    private String nivelRiesgo;
    private String recomendacion;
}
