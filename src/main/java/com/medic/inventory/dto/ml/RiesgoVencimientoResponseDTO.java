package com.medic.inventory.dto.ml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiesgoVencimientoResponseDTO {

    private Integer totalProductos;
    private Integer productosEnRiesgo;
    private List<PrediccionRiesgoVencimientoDTO> predicciones;
}
