package com.medic.inventory.service;

import com.medic.inventory.dto.LoteResponse;
import com.medic.inventory.entity.Inventario;
import com.medic.inventory.entity.Lote;
import com.medic.inventory.repository.InventarioRepository;
import com.medic.inventory.repository.LoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;
    private final InventarioRepository inventarioRepository;

    /**
     * HU-11: Obtener lotes ordenados por fecha de vencimiento (FEFO)
     * para un producto específico en una sede específica.
     * Solo retorna lotes con stock disponible.
     */
    @Transactional(readOnly = true)
    public List<LoteResponse> obtenerLotesFEFOPorProductoYSede(Long productoId, Long sedeId) {
        List<Lote> lotes = loteRepository.findByProductoIdAndSedeIdWithStockOrderByFechaVencimiento(productoId, sedeId);

        return lotes.stream()
            .map(lote -> {
                Inventario inventario = inventarioRepository.findBySedeIdAndLoteId(sedeId, lote.getId())
                    .orElse(null);

                return mapToResponse(lote, inventario);
            })
            .collect(Collectors.toList());
    }

    /**
     * HU-11: Obtener todos los lotes de un producto ordenados por fecha de vencimiento (FEFO)
     */
    @Transactional(readOnly = true)
    public List<LoteResponse> obtenerLotesFEFOPorProducto(Long productoId) {
        List<Lote> lotes = loteRepository.findByProductoIdOrderByFechaVencimientoAsc(productoId);

        return lotes.stream()
            .map(lote -> mapToResponse(lote, null))
            .collect(Collectors.toList());
    }

    /**
     * HU-11: Obtener el lote con la fecha de vencimiento más cercana para un producto en una sede
     * (primer lote según FEFO)
     */
    @Transactional(readOnly = true)
    public LoteResponse obtenerPrimerLoteFEFO(Long productoId, Long sedeId) {
        List<Lote> lotes = loteRepository.findByProductoIdAndSedeIdWithStockOrderByFechaVencimiento(productoId, sedeId);

        if (lotes.isEmpty()) {
            return null;
        }

        Lote primerLote = lotes.get(0);
        Inventario inventario = inventarioRepository.findBySedeIdAndLoteId(sedeId, primerLote.getId())
            .orElse(null);

        return mapToResponse(primerLote, inventario);
    }

    /**
     * Mapear entidad Lote a DTO LoteResponse
     */
    private LoteResponse mapToResponse(Lote lote, Inventario inventario) {
        LoteResponse response = new LoteResponse();
        response.setId(lote.getId());
        response.setProductoId(lote.getProducto().getId());
        response.setProductoNombre(lote.getProducto().getNombre());
        response.setCodigoLote(lote.getCodigoProductoProv());
        response.setFechaVencimiento(lote.getFechaVencimiento());
        response.setEstado(lote.getEstado());
        response.setPrecioUnitario(lote.getPrecioUnitario());

        if (inventario != null) {
            response.setCantidadDisponible(inventario.getCantidad());
        } else {
            response.setCantidadDisponible(0);
        }

        // Calcular días hasta vencimiento
        if (lote.getFechaVencimiento() != null) {
            long dias = ChronoUnit.DAYS.between(LocalDate.now(), lote.getFechaVencimiento());
            response.setDiasHastaVencimiento((int) dias);
        }

        return response;
    }
}
