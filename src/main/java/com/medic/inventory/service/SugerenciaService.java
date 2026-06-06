package com.medic.inventory.service;

import com.medic.inventory.dto.SugerenciaPedidoResponse;
import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SugerenciaService {

    private final ProductRepository productRepository;
    private final InventarioRepository inventarioRepository;
    private final LoteRepository loteRepository;
    private final UmbralStockRepository umbralStockRepository;
    private final SupplierRepository supplierRepository;
    private final RestockRequestRepository restockRequestRepository;
    private final MovimientoRepository movimientoRepository;

    /**
     * HU-07 Escenario 1: Obtener sugerencias de pedido basadas en stock y umbrales
     */
    @Transactional(readOnly = true)
    public List<SugerenciaPedidoResponse> obtenerSugerenciasPedido() {
        log.info("Calculando sugerencias de pedido...");

        List<SugerenciaPedidoResponse> sugerencias = new ArrayList<>();

        // Obtener todos los umbrales configurados
        List<UmbralStock> umbrales = umbralStockRepository.findAll();

        for (UmbralStock umbral : umbrales) {
            Product producto = umbral.getProducto();
            Sede sede = umbral.getSede();

            // Obtener stock actual sumando todos los lotes del producto en la sede
            List<Lote> lotes = loteRepository.findByProductoId(producto.getId());
            int stockActual = 0;

            for (Lote lote : lotes) {
                Optional<Inventario> invOpt = inventarioRepository.findBySedeIdAndLoteId(sede.getId(), lote.getId());
                if (invOpt.isPresent()) {
                    stockActual += invOpt.get().getCantidad();
                }
            }

            Integer stockMinimo = umbral.getMinimo();

            // Solo sugerir si stock actual < mínimo
            if (stockMinimo != null && stockActual < stockMinimo) {
                SugerenciaPedidoResponse sugerencia = new SugerenciaPedidoResponse();
                sugerencia.setProductoId(producto.getId());
                sugerencia.setProductoNombre(producto.getNombre());
                sugerencia.setStockActual(stockActual);
                sugerencia.setStockMinimo(stockMinimo);

                // Calcular cantidad sugerida: llevar al punto de pedido o al stock mínimo + seguridad
                int cantidadSugerida;
                if (umbral.getPuntoPedido() != null && umbral.getPuntoPedido() > 0) {
                    cantidadSugerida = umbral.getPuntoPedido() - stockActual;
                } else {
                    // Si no hay punto de pedido, sugerir llevar al mínimo + 20%
                    cantidadSugerida = (int) Math.ceil((stockMinimo * 1.2) - stockActual);
                }

                // Asegurar que la cantidad sea positiva
                if (cantidadSugerida <= 0) {
                    cantidadSugerida = stockMinimo - stockActual;
                }

                sugerencia.setCantidadSugerida(cantidadSugerida);

                // Determinar criticidad
                double porcentajeDeficit = ((double) (stockMinimo - stockActual) / stockMinimo) * 100;
                if (porcentajeDeficit >= 70) {
                    sugerencia.setCriticidad("ALTA");
                } else if (porcentajeDeficit >= 40) {
                    sugerencia.setCriticidad("MEDIA");
                } else {
                    sugerencia.setCriticidad("BAJA");
                }

                // Justificación
                String justificacion = String.format(
                    "Stock actual (%d) está %d unidades por debajo del mínimo (%d). Déficit: %.0f%%",
                    stockActual, (stockMinimo - stockActual), stockMinimo, porcentajeDeficit
                );
                sugerencia.setJustificacion(justificacion);

                // Obtener proveedor principal del producto (el primero disponible)
                List<Supplier> proveedores = supplierRepository.findAll();
                if (!proveedores.isEmpty()) {
                    Supplier proveedorPrincipal = proveedores.get(0);
                    sugerencia.setProveedorId(proveedorPrincipal.getId());
                    sugerencia.setProveedorNombre(proveedorPrincipal.getNombre());
                }

                // CP007: Calcular información complementaria para análisis operativo
                calcularDatosComplementarios(sugerencia, producto.getId(), sede.getId(), stockActual);

                // Solo agregar la sugerencia si NO existe un pedido pendiente para este producto
                if (!existePedidoPendiente(producto.getId())) {
                    sugerencias.add(sugerencia);
                } else {
                    log.debug("Sugerencia omitida para producto {} - ya existe pedido pendiente", producto.getNombre());
                }
            }
        }

        // Ordenar por criticidad: ALTA > MEDIA > BAJA
        sugerencias.sort((a, b) -> {
            int prioridadA = getPrioridad(a.getCriticidad());
            int prioridadB = getPrioridad(b.getCriticidad());
            return Integer.compare(prioridadB, prioridadA);
        });

        log.info("Generadas {} sugerencias de pedido", sugerencias.size());
        return sugerencias.stream().limit(5).collect(Collectors.toList()); // Top 5
    }

    private int getPrioridad(String criticidad) {
        switch (criticidad) {
            case "ALTA": return 3;
            case "MEDIA": return 2;
            case "BAJA": return 1;
            default: return 0;
        }
    }

    /**
     * Verifica si existe un pedido pendiente para un producto
     * Estados pendientes: DRAFT, PENDING, SENT
     */
    private boolean existePedidoPendiente(Long productoId) {
        return restockRequestRepository.findAll().stream()
            .filter(r -> r.getProduct() != null && r.getProduct().getId().equals(productoId))
            .anyMatch(r -> r.getStatus() == RestockRequest.RestockStatus.DRAFT ||
                          r.getStatus() == RestockRequest.RestockStatus.PENDING ||
                          r.getStatus() == RestockRequest.RestockStatus.SENT);
    }

    /**
     * CP007: Calcular datos complementarios para análisis operativo de sugerencias
     */
    private void calcularDatosComplementarios(SugerenciaPedidoResponse sugerencia, Long productoId, Long sedeId, int stockActual) {
        // 1. Calcular consumo promedio mensual (últimos 3 meses)
        LocalDateTime hace3Meses = LocalDateTime.now().minusMonths(3);

        // Primero obtener todos los loteIds del producto
        List<Long> loteIdsDelProducto = loteRepository.findByProductoId(productoId).stream()
            .map(Lote::getId)
            .collect(Collectors.toList());

        // Filtrar movimientos de salida de esos lotes
        List<Movimiento> movimientosSalida = movimientoRepository.findAll().stream()
            .filter(m -> m.getTipo().equals("SALIDA"))
            .filter(m -> m.getLoteId() != null && loteIdsDelProducto.contains(m.getLoteId()))
            .filter(m -> m.getOcurrioEn() != null && m.getOcurrioEn().isAfter(hace3Meses))
            .collect(Collectors.toList());

        int totalConsumo = movimientosSalida.stream()
            .mapToInt(Movimiento::getCantidad)
            .sum();

        int consumoPromedioMensual = totalConsumo / 3; // Promedio de 3 meses
        sugerencia.setConsumoPromedioMensual(consumoPromedioMensual);

        // 2. Calcular tendencia (comparar último mes vs promedio de 2 meses anteriores)
        LocalDateTime haceUnMes = LocalDateTime.now().minusMonths(1);
        int consumoUltimoMes = movimientosSalida.stream()
            .filter(m -> m.getOcurrioEn().isAfter(haceUnMes))
            .mapToInt(Movimiento::getCantidad)
            .sum();

        int consumo2MesesAnteriores = totalConsumo - consumoUltimoMes;
        int promedioMesesAnteriores = consumo2MesesAnteriores / 2;

        String tendencia;
        if (consumoUltimoMes > promedioMesesAnteriores * 1.2) {
            tendencia = "CRECIENTE";
        } else if (consumoUltimoMes < promedioMesesAnteriores * 0.8) {
            tendencia = "DECRECIENTE";
        } else {
            tendencia = "ESTABLE";
        }
        sugerencia.setTendencia(tendencia);

        // 3. Calcular días para agotamiento
        int diasParaAgotamiento = 0;
        if (consumoPromedioMensual > 0) {
            double consumoDiario = consumoPromedioMensual / 30.0;
            if (consumoDiario > 0) {
                diasParaAgotamiento = (int) Math.ceil(stockActual / consumoDiario);
            }
        }
        sugerencia.setDiasParaAgotamiento(diasParaAgotamiento);

        // 4. Calcular fecha estimada de reposición (7 días de lead time por defecto)
        int leadTimeDias = 7; // Puede venir del proveedor en el futuro
        LocalDate fechaEstimada = LocalDate.now().plusDays(leadTimeDias);
        sugerencia.setFechaEstimadaReposicion(fechaEstimada);

        log.debug("Datos complementarios calculados para producto {}: consumo={}/mes, tendencia={}, días agotamiento={}",
            productoId, consumoPromedioMensual, tendencia, diasParaAgotamiento);
    }
}
