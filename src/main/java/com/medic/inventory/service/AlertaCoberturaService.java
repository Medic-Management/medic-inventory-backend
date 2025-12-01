package com.medic.inventory.service;

import com.medic.inventory.dto.AlertaResponse;
import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HU-17: Sistema de Cobertura Temporal
 * Servicio para calcular días de cobertura y generar alertas de baja cobertura
 */
@Service
@RequiredArgsConstructor
public class AlertaCoberturaService {

    private final AlertaRepository alertaRepository;
    private final InventarioRepository inventarioRepository;
    private final UmbralStockRepository umbralStockRepository;
    private final MovimientoRepository movimientoRepository;
    private final LoteRepository loteRepository;

    /**
     * Verifica la cobertura de stock y genera alertas cuando los días de cobertura
     * están por debajo del umbral configurado
     */
    @Transactional
    public List<AlertaResponse> verificarCoberturaYGenerarAlertas() {
        List<Alerta> alertasGeneradas = new ArrayList<>();
        List<UmbralStock> umbrales = umbralStockRepository.findAll();

        for (UmbralStock umbral : umbrales) {
            // Solo procesar umbrales que tengan configurado el umbral de cobertura
            if (umbral.getUmbralCoberturaDias() == null || umbral.getUmbralCoberturaDias() <= 0) {
                continue;
            }

            Long sedeId = umbral.getSede().getId();
            Long productoId = umbral.getProducto().getId();

            // Calcular stock actual
            int stockActual = calcularStockActual(sedeId, productoId);

            if (stockActual <= 0) {
                // Si no hay stock, se maneja con las alertas normales de stock bajo
                continue;
            }

            // Calcular consumo promedio diario de los últimos 30 días
            double consumoPromedioDiario = calcularConsumoPromedioDiario(sedeId, productoId, 30);

            if (consumoPromedioDiario <= 0) {
                // Si no hay historial de consumo, no se puede calcular cobertura
                continue;
            }

            // Calcular días de cobertura
            int diasCobertura = (int) Math.floor(stockActual / consumoPromedioDiario);

            // Verificar si existe alerta activa de cobertura
            boolean tieneAlertaCoberturaActiva = alertaRepository.existsBySedeIdAndProductoIdAndTipoAndActiva(
                sedeId, productoId, "COBERTURA_BAJA", 1
            );

            // Generar alerta si la cobertura está por debajo del umbral
            if (diasCobertura < umbral.getUmbralCoberturaDias() && !tieneAlertaCoberturaActiva) {
                Alerta alerta = new Alerta();
                alerta.setSede(umbral.getSede());
                alerta.setProducto(umbral.getProducto());
                alerta.setTipo("COBERTURA_BAJA");

                // Determinar nivel de severidad
                double porcentajeUmbral = (double) diasCobertura / umbral.getUmbralCoberturaDias();
                if (porcentajeUmbral < 0.3) {
                    alerta.setNivel("ALTA");
                } else if (porcentajeUmbral < 0.6) {
                    alerta.setNivel("MEDIA");
                } else {
                    alerta.setNivel("BAJA");
                }

                alerta.setDisparadaEn(LocalDateTime.now());
                alerta.setActiva(1);
                alerta.setNota(String.format("Cobertura: %d días (umbral: %d días, consumo diario: %.1f unidades)",
                    diasCobertura, umbral.getUmbralCoberturaDias(), consumoPromedioDiario));

                alertasGeneradas.add(alertaRepository.save(alerta));
            }

            // Resolver alerta si la cobertura vuelve a estar por encima del umbral
            if (diasCobertura >= umbral.getUmbralCoberturaDias() && tieneAlertaCoberturaActiva) {
                List<Alerta> alertasActivas = alertaRepository.findBySedeIdAndProductoIdAndTipoAndActiva(
                    sedeId, productoId, "COBERTURA_BAJA", 1
                );

                for (Alerta alerta : alertasActivas) {
                    alerta.setActiva(0);
                    alerta.setResueltaEn(LocalDateTime.now());
                    alertaRepository.save(alerta);
                }
            }
        }

        return alertasGeneradas.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las alertas de cobertura activas con información detallada
     */
    @Transactional(readOnly = true)
    public List<AlertaResponse> obtenerAlertasCoberturaActivas() {
        List<Alerta> alertas = alertaRepository.findByTipoAndActivaOrderByNivelDesc("COBERTURA_BAJA", 1);

        return alertas.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Calcula el stock actual de un producto en una sede
     */
    private int calcularStockActual(Long sedeId, Long productoId) {
        List<Inventario> inventarios = inventarioRepository.findBySedeId(sedeId);

        return inventarios.stream()
            .filter(inv -> {
                return loteRepository.findById(inv.getLoteId())
                    .map(lote -> lote.getProducto().getId().equals(productoId))
                    .orElse(false);
            })
            .mapToInt(Inventario::getCantidad)
            .sum();
    }

    /**
     * Calcula el consumo promedio diario de un producto en una sede
     * basándose en los movimientos de salida de los últimos N días
     */
    private double calcularConsumoPromedioDiario(Long sedeId, Long productoId, int diasHistorial) {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(diasHistorial);
        LocalDateTime fechaFin = LocalDateTime.now();

        List<Movimiento> movimientos = movimientoRepository.findMovimientosPorPeriodo(fechaInicio, fechaFin);

        // Filtrar solo movimientos de salida de la sede y producto específico
        int totalSalidas = movimientos.stream()
            .filter(mov -> "SALIDA".equals(mov.getTipo()))
            .filter(mov -> mov.getSedeId().equals(sedeId))
            .filter(mov -> {
                // Verificar que el lote pertenece al producto
                return loteRepository.findById(mov.getLoteId())
                    .map(lote -> lote.getProducto().getId().equals(productoId))
                    .orElse(false);
            })
            .mapToInt(Movimiento::getCantidad)
            .sum();

        if (totalSalidas == 0) {
            return 0.0;
        }

        return (double) totalSalidas / diasHistorial;
    }

    /**
     * Mapea una entidad Alerta a su DTO de respuesta con información adicional
     */
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

        // Calcular información adicional de cobertura
        int stockActual = calcularStockActual(sedeId, productoId);
        response.setStockActual(stockActual);

        double consumoPromedioDiario = calcularConsumoPromedioDiario(sedeId, productoId, 30);
        int diasCobertura = consumoPromedioDiario > 0 ?
            (int) Math.floor(stockActual / consumoPromedioDiario) : 0;

        umbralStockRepository.findBySedeIdAndProductoId(sedeId, productoId)
            .ifPresent(umbral -> {
                response.setNivelAlerta(umbral.getUmbralCoberturaDias());
                response.setSugerencia(String.format(
                    "Stock actual: %d unidades. Cobertura: %d días. Consumo promedio: %.1f unidades/día. " +
                    "Se recomienda reabastecer para mantener %d días de cobertura.",
                    stockActual, diasCobertura, consumoPromedioDiario, umbral.getUmbralCoberturaDias()
                ));
            });

        return response;
    }
}
