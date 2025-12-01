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
public class PicoDemandaResponseDTO {

    private Integer totalProductos;
    private Integer productosConPico;
    private List<PrediccionPicoDemandaDTO> predicciones;
}
