package com.medic.inventory.service;

import com.medic.inventory.dto.SugerenciaPedidoResponse;
import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SugerenciaService {

    private final ProductRepository productRepository;
    private final InventarioRepository inventarioRepository;
    private final UmbralStockRepository umbralStockRepository;
    private final SupplierRepository supplierRepository;

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

            // Obtener stock actual
            Optional<Inventario> inventarioOpt = inventarioRepository
                .findBySedeIdAndProductoId(sede.getId(), producto.getId());

            if (!inventarioOpt.isPresent()) {
                continue;
            }

            Inventario inventario = inventarioOpt.get();
            int stockActual = inventario.getCantidad();
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

                sugerencias.add(sugerencia);
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
}
