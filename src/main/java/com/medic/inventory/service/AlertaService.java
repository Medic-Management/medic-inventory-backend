package com.medic.inventory.service;

import com.medic.inventory.dto.AlertaResponse;
import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final InventarioRepository inventarioRepository;
    private final UmbralStockRepository umbralStockRepository;
    private final SedeRepository sedeRepository;
    private final LoteRepository loteRepository;

    @Transactional
    public List<AlertaResponse> verificarStockYGenerarAlertas() {
        List<Alerta> alertasGeneradas = new java.util.ArrayList<>();

        List<UmbralStock> umbrales = umbralStockRepository.findAll();

        for (UmbralStock umbral : umbrales) {
            Long sedeId = umbral.getSede().getId();
            Long productoId = umbral.getProducto().getId();

            List<Inventario> inventarios = inventarioRepository.findBySedeId(sedeId);

            int stockTotal = inventarios.stream()
                .filter(inv -> loteRepository.findById(inv.getLoteId())
                    .map(lote -> lote.getProducto().getId().equals(productoId))
                    .orElse(false))
                .mapToInt(Inventario::getCantidad)
                .sum();

            boolean tieneAlertaActiva = alertaRepository.existsBySedeIdAndProductoIdAndActiva(
                sedeId, productoId, 1
            );

            if (stockTotal <= umbral.getMinimo() && !tieneAlertaActiva) {
                Alerta alerta = new Alerta();
                alerta.setSede(umbral.getSede());
                alerta.setProducto(umbral.getProducto());

                if (stockTotal == 0) {
                    alerta.setTipo("STOCK_AGOTADO");
                    alerta.setNivel("ALTA");
                } else if (stockTotal <= umbral.getMinimo() / 2) {
                    alerta.setTipo("STOCK_CRITICO");
                    alerta.setNivel("ALTA");
                } else {
                    alerta.setTipo("STOCK_BAJO");
                    alerta.setNivel("MEDIA");
                }

                alerta.setDisparadaEn(LocalDateTime.now());
                alerta.setActiva(1);

                alertasGeneradas.add(alertaRepository.save(alerta));
            }

            if (stockTotal > umbral.getMinimo() && tieneAlertaActiva) {
                alertaRepository.findBySedeIdAndProductoIdAndActiva(sedeId, productoId, 1)
                    .forEach(alerta -> {
                        alerta.setActiva(0);
                        alerta.setResueltaEn(LocalDateTime.now());
                        alertaRepository.save(alerta);
                    });
            }
        }

        return alertasGeneradas.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<AlertaResponse> obtenerAlertasActivas() {
        List<Alerta> alertas = alertaRepository.findByActivaOrderByDisparadaEnDesc(1);

        return alertas.stream()
            .filter(alerta -> {
                Long sedeId = alerta.getSede().getId();
                Long productoId = alerta.getProducto().getId();

                List<Inventario> inventarios = inventarioRepository.findBySedeId(sedeId);
                int stockActual = inventarios.stream()
                    .filter(inv -> loteRepository.findById(inv.getLoteId())
                        .map(lote -> lote.getProducto().getId().equals(productoId))
                        .orElse(false))
                    .mapToInt(Inventario::getCantidad)
                    .sum();

                return umbralStockRepository.findBySedeIdAndProductoId(sedeId, productoId)
                    .map(umbral -> {
                        if (stockActual > umbral.getMinimo()) {
                            alerta.setActiva(0);
                            alerta.setResueltaEn(LocalDateTime.now());
                            alertaRepository.save(alerta);
                            return false;
                        }
                        return true;
                    })
                    .orElse(false);
            })
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void resolverAlerta(Long alertaId) {
        Alerta alerta = alertaRepository.findById(alertaId)
            .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        alerta.setActiva(0);
        alerta.setResueltaEn(LocalDateTime.now());
        alertaRepository.save(alerta);
    }

    private AlertaResponse mapToResponse(Alerta alerta) {
        AlertaResponse response = new AlertaResponse();
        response.setId(alerta.getId());

        Long sedeId = alerta.getSede().getId();
        Long productoId = alerta.getProducto().getId();

        response.setSedeId(sedeId);
        response.setProductoId(productoId);
        response.setSedeNombre(alerta.getSede().getNombre());
        response.setProductoNombre(alerta.getProducto().getNombre());
        response.setTipo(alerta.getTipo());
        response.setNivel(alerta.getNivel());
        response.setDisparadaEn(alerta.getDisparadaEn());
        response.setResueltaEn(alerta.getResueltaEn());
        response.setActiva(alerta.getActiva() == 1);

        List<Inventario> inventarios = inventarioRepository.findBySedeId(sedeId);
        int stockActual = inventarios.stream()
            .filter(inv -> loteRepository.findById(inv.getLoteId())
                .map(lote -> lote.getProducto().getId().equals(productoId))
                .orElse(false))
            .mapToInt(Inventario::getCantidad)
            .sum();
        response.setStockActual(stockActual);

        umbralStockRepository.findBySedeIdAndProductoId(sedeId, productoId)
            .ifPresent(umbral -> {
                response.setNivelAlerta(umbral.getMinimo());
                response.setSugerencia("Reabastecer antes de " +
                    LocalDateTime.now().plusDays(7).toLocalDate());
            });

        return response;
    }

    /**
     * HU-03 Escenario 2: Obtener reporte consolidado de alertas
     * Incluye KPIs y lista ordenada por criticidad
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerReporteConsolidado(LocalDateTime desde, LocalDateTime hasta) {
        log.info("Generando reporte consolidado de alertas desde {} hasta {}", desde, hasta);

        // Obtener todas las alertas del período (activas o resueltas)
        List<Alerta> alertasPeriodo;
        if (desde != null && hasta != null) {
            alertasPeriodo = alertaRepository.findByDisparadaEnBetween(desde, hasta);
        } else {
            // Si no se especifica rango, obtener las últimas 30 días
            alertasPeriodo = alertaRepository.findByDisparadaEnBetween(
                LocalDateTime.now().minusDays(30), LocalDateTime.now()
            );
        }

        // Convertir a responses y ordenar por criticidad
        List<AlertaResponse> alertas = alertasPeriodo.stream()
            .map(this::mapToResponse)
            .sorted((a, b) -> {
                // Orden: ALTA > MEDIA > BAJA
                int nivelA = getNivelPrioridad(a.getNivel());
                int nivelB = getNivelPrioridad(b.getNivel());
                return Integer.compare(nivelB, nivelA);
            })
            .collect(Collectors.toList());

        // Calcular KPIs
        long totalAlertas = alertas.size();
        long alertasActivas = alertas.stream().filter(AlertaResponse::isActiva).count();
        long alertasResueltas = totalAlertas - alertasActivas;

        long alertasAltas = alertas.stream()
            .filter(a -> "ALTA".equals(a.getNivel())).count();
        long alertasMedias = alertas.stream()
            .filter(a -> "MEDIA".equals(a.getNivel())).count();
        long alertasBajas = alertas.stream()
            .filter(a -> "BAJA".equals(a.getNivel())).count();

        // Medicamentos más críticos (Top 10)
        List<AlertaResponse> top10Criticos = alertas.stream()
            .filter(AlertaResponse::isActiva)
            .limit(10)
            .collect(Collectors.toList());

        Map<String, Object> reporte = new java.util.HashMap<>();
        reporte.put("periodoDesde", desde != null ? desde : LocalDateTime.now().minusDays(30));
        reporte.put("periodoHasta", hasta != null ? hasta : LocalDateTime.now());
        reporte.put("totalAlertas", totalAlertas);
        reporte.put("alertasActivas", alertasActivas);
        reporte.put("alertasResueltas", alertasResueltas);
        reporte.put("alertasAltas", alertasAltas);
        reporte.put("alertasMedias", alertasMedias);
        reporte.put("alertasBajas", alertasBajas);
        reporte.put("alertas", alertas);
        reporte.put("top10Criticos", top10Criticos);
        reporte.put("generadoEn", LocalDateTime.now());

        log.info("Reporte consolidado generado: {} alertas totales, {} activas",
            totalAlertas, alertasActivas);

        return reporte;
    }

    private int getNivelPrioridad(String nivel) {
        switch (nivel != null ? nivel.toUpperCase() : "") {
            case "ALTA": return 3;
            case "MEDIA": return 2;
            case "BAJA": return 1;
            default: return 0;
        }
    }
}
