package com.medic.inventory.scheduler;

import com.medic.inventory.dto.SolicitudCompraResponse;
import com.medic.inventory.entity.Alerta;
import com.medic.inventory.entity.Product;
import com.medic.inventory.entity.Sede;
import com.medic.inventory.repository.AlertaRepository;
import com.medic.inventory.repository.ProductRepository;
import com.medic.inventory.repository.SedeRepository;
import com.medic.inventory.service.SolicitudCompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CP010: Scheduler que ejecuta cada hora para detectar pedidos sin acuse de recibo > 48 horas
 * HU-10 Escenario 2: Notificar al jefe si no hay acuse en 48 horas
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ConfirmacionScheduler {

    private final SolicitudCompraService solicitudCompraService;
    private final AlertaRepository alertaRepository;
    private final ProductRepository productRepository;
    private final SedeRepository sedeRepository;

    /**
     * Ejecuta cada 1 hora (cron: 0 0 * * * *) = segundo 0, minuto 0 de cada hora
     * Para testing manual cambiar a cada 5 minutos
     */
    @Scheduled(cron = "0 0 * * * *")
    public void verificarPedidosSinAcuse() {
        log.info("CP010: Ejecutando verificación de pedidos sin acuse de recibo (>48h)...");

        try {
            List<SolicitudCompraResponse> pedidosSinConfirmar =
                solicitudCompraService.obtenerSolicitudesSinConfirmar();

            if (pedidosSinConfirmar.isEmpty()) {
                log.info("CP010: No hay pedidos sin confirmar > 48 horas");
                return;
            }

            log.warn("CP010: Se encontraron {} pedidos sin confirmar > 48 horas",
                pedidosSinConfirmar.size());

            for (SolicitudCompraResponse pedido : pedidosSinConfirmar) {
                crearAlertaPedidoSinAcuse(pedido);
            }

            log.info("CP010: Verificación completada. Alertas creadas: {}",
                pedidosSinConfirmar.size());

        } catch (Exception e) {
            log.error("CP010: Error al verificar pedidos sin acuse", e);
        }
    }

    /**
     * Crea una alerta en la base de datos para que:
     * 1. Aparezca en el dashboard del jefe de farmacia
     * 2. UiPath la lea y envíe correo de notificación
     */
    private void crearAlertaPedidoSinAcuse(SolicitudCompraResponse pedido) {
        try {
            // Verificar si ya existe una alerta activa para este pedido
            boolean yaExisteAlerta = alertaRepository.findByActivaOrderByDisparadaEnDesc(1)
                .stream()
                .anyMatch(a -> a.getNota() != null &&
                    a.getNota().contains("Pedido #" + pedido.getId()));

            if (yaExisteAlerta) {
                log.debug("CP010: Ya existe alerta para pedido #{}", pedido.getId());
                return;
            }

            Product producto = productRepository.findById(pedido.getProductoId())
                .orElse(null);

            Sede sede = sedeRepository.findById(1L)
                .orElse(null);

            if (producto == null || sede == null) {
                log.warn("CP010: No se pudo crear alerta - producto o sede no encontrado");
                return;
            }

            Alerta alerta = new Alerta();
            alerta.setProducto(producto);
            alerta.setSede(sede);
            alerta.setTipo("PEDIDO_SIN_ACUSE");
            alerta.setNivel("ALTA");
            alerta.setNota(String.format(
                "CP010: Pedido #%d sin acuse de recibo. Proveedor: %s, Producto: %s. " +
                "Enviado: %s. Requiere seguimiento inmediato.",
                pedido.getId(),
                pedido.getProveedorNombre(),
                pedido.getProductoNombre(),
                pedido.getCreadaEn() != null ? pedido.getCreadaEn().toString() : "N/A"
            ));
            alerta.setDisparadaEn(LocalDateTime.now());
            alerta.setActiva(1);

            alertaRepository.save(alerta);

            log.info("CP010: Alerta creada para pedido #{} - {}",
                pedido.getId(), pedido.getProductoNombre());

        } catch (Exception e) {
            log.error("CP010: Error al crear alerta para pedido #{}", pedido.getId(), e);
        }
    }
}
