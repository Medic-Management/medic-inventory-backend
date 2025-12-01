package com.medic.inventory.service;

import com.medic.inventory.dto.AlertaVencimientoResponse;
import com.medic.inventory.entity.Inventario;
import com.medic.inventory.entity.Lote;
import com.medic.inventory.repository.InventarioRepository;
import com.medic.inventory.repository.LoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaVencimientoService {

    private final LoteRepository loteRepository;
    private final InventarioRepository inventarioRepository;

    public List<AlertaVencimientoResponse> verificarLotesProximosAVencer() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(90);

        List<Lote> lotes = loteRepository.findLotesProximosAVencer(fechaLimite);
        List<AlertaVencimientoResponse> alertas = new ArrayList<>();

        for (Lote lote : lotes) {
            long diasRestantes = ChronoUnit.DAYS.between(hoy, lote.getFechaVencimiento());

            if (diasRestantes < 0) {
                continue;
            }

            List<Inventario> inventarios = inventarioRepository.findAll();
            int cantidadTotal = inventarios.stream()
                .filter(inv -> inv.getLoteId().equals(lote.getId()))
                .mapToInt(Inventario::getCantidad)
                .sum();

            if (cantidadTotal > 0) {
                AlertaVencimientoResponse alerta = new AlertaVencimientoResponse();
                alerta.setLoteId(lote.getId());
                alerta.setCodigoLote(lote.getCodigoProductoProv());
                alerta.setProductoId(lote.getProducto().getId());
                alerta.setProductoNombre(lote.getProducto().getNombre());
                alerta.setFechaVencimiento(lote.getFechaVencimiento());
                alerta.setDiasRestantes((int) diasRestantes);
                alerta.setCantidadTotal(cantidadTotal);

                if (diasRestantes < 30) {
                    alerta.setNivel("CRITICO");
                } else if (diasRestantes < 60) {
                    alerta.setNivel("ALERTA");
                } else {
                    alerta.setNivel("ADVERTENCIA");
                }

                alertas.add(alerta);
            }
        }

        log.info("Verificación de vencimientos completada: {} lotes próximos a vencer", alertas.size());
        return alertas;
    }

    public List<AlertaVencimientoResponse> obtenerAlertasPorNivel(String nivel) {
        return verificarLotesProximosAVencer().stream()
            .filter(alerta -> alerta.getNivel().equals(nivel))
            .collect(Collectors.toList());
    }

    public List<AlertaVencimientoResponse> obtenerAlertasCriticas() {
        return obtenerAlertasPorNivel("CRITICO");
    }
}
