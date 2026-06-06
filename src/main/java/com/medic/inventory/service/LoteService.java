package com.medic.inventory.service;

import com.medic.inventory.dto.LoteResponse;
import com.medic.inventory.entity.Inventario;
import com.medic.inventory.entity.Lote;
import com.medic.inventory.repository.InventarioRepository;
import com.medic.inventory.repository.LoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
     * Suma las cantidades de todas las sedes para cada lote
     */
    @Transactional(readOnly = true)
    public List<LoteResponse> obtenerLotesFEFOPorProducto(Long productoId) {
        List<Lote> lotes = loteRepository.findByProductoIdOrderByFechaVencimientoAsc(productoId);

        return lotes.stream()
            .map(lote -> {
                // Obtener todos los inventarios de este lote en todas las sedes
                List<Inventario> inventarios = inventarioRepository.findByLoteId(lote.getId());

                // Sumar las cantidades de todas las sedes
                int cantidadTotal = inventarios.stream()
                    .mapToInt(Inventario::getCantidad)
                    .sum();

                // Crear un inventario temporal con la cantidad total
                Inventario inventarioTotal = null;
                if (cantidadTotal > 0) {
                    inventarioTotal = new Inventario();
                    inventarioTotal.setCantidad(cantidadTotal);
                }

                return mapToResponse(lote, inventarioTotal);
            })
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
     * HU-21: Obtener todos los lotes (activos e inactivos)
     */
    @Transactional(readOnly = true)
    public List<LoteResponse> obtenerTodosLosLotes() {
        List<Lote> lotes = loteRepository.findAllLotesWithInventario();

        return lotes.stream()
            .map(lote -> {
                // Buscar inventario para todas las sedes
                List<Inventario> inventarios = inventarioRepository.findByLoteId(lote.getId());
                int cantidadTotal = inventarios.stream()
                    .mapToInt(Inventario::getCantidad)
                    .sum();

                LoteResponse response = mapToResponse(lote, null);
                response.setCantidadDisponible(cantidadTotal);
                return response;
            })
            .collect(Collectors.toList());
    }

    /**
     * HU-21: Obtener lotes bloqueados
     */
    @Transactional(readOnly = true)
    public List<LoteResponse> obtenerLotesBloqueados() {
        List<Lote> lotes = loteRepository.findLotesBloqueados();

        return lotes.stream()
            .map(lote -> {
                List<Inventario> inventarios = inventarioRepository.findByLoteId(lote.getId());
                int cantidadTotal = inventarios.stream()
                    .mapToInt(Inventario::getCantidad)
                    .sum();

                LoteResponse response = mapToResponse(lote, null);
                response.setCantidadDisponible(cantidadTotal);
                return response;
            })
            .collect(Collectors.toList());
    }

    /**
     * CP021: Bloquear un lote (establecer estado = false y campos de bloqueo)
     * Esto previene que el lote sea dispensado o usado
     */
    @Transactional
    public LoteResponse bloquearLote(Long loteId, String motivo) {
        Lote lote = loteRepository.findById(loteId)
            .orElseThrow(() -> new RuntimeException("Lote no encontrado con ID: " + loteId));

        if (!lote.getEstado() || Boolean.TRUE.equals(lote.getBloqueado())) {
            throw new RuntimeException("El lote ya está bloqueado");
        }

        if (motivo == null || motivo.trim().isEmpty()) {
            throw new RuntimeException("Debe especificar un motivo de bloqueo");
        }

        lote.setEstado(false);
        lote.setBloqueado(true);
        lote.setMotivoBloqueo(motivo);
        lote.setBloqueadoEn(java.time.LocalDate.now());
        loteRepository.save(lote);

        log.info("Lote bloqueado: ID={}, Motivo={}", loteId, motivo);

        return mapToResponse(lote, null);
    }

    /**
     * CP021: Desbloquear un lote (establecer estado = true y limpiar campos de bloqueo)
     * Permite que el lote vuelva a estar disponible para dispensación
     */
    @Transactional
    public LoteResponse desbloquearLote(Long loteId, String motivo) {
        Lote lote = loteRepository.findById(loteId)
            .orElseThrow(() -> new RuntimeException("Lote no encontrado con ID: " + loteId));

        if (lote.getEstado() && !Boolean.TRUE.equals(lote.getBloqueado())) {
            throw new RuntimeException("El lote ya está activo");
        }

        lote.setEstado(true);
        lote.setBloqueado(false);
        lote.setMotivoBloqueo(null);
        lote.setBloqueadoEn(null);
        lote.setBloqueadoPor(null);
        loteRepository.save(lote);

        log.info("Lote desbloqueado: ID={}, Motivo={}", loteId, motivo);

        return mapToResponse(lote, null);
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

        // CP021: Mapear campos de bloqueo
        response.setBloqueado(lote.getBloqueado());
        response.setMotivoBloqueo(lote.getMotivoBloqueo());
        response.setBloqueadoEn(lote.getBloqueadoEn());
        response.setBloqueadoPor(lote.getBloqueadoPor());

        return response;
    }
}
