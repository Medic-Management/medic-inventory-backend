package com.medic.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskService {

    private final AlertaService alertaService;
    private final AlertaVencimientoService alertaVencimientoService;
    private final AlertaCoberturaService alertaCoberturaService;

    @Scheduled(cron = "0 */30 * * * *")
    public void verificarStockAutomaticamente() {
        log.info("Iniciando verificación automática de stock...");
        try {
            alertaService.verificarStockYGenerarAlertas();
            log.info("Verificación automática de stock completada exitosamente");
        } catch (Exception e) {
            log.error("Error en verificación automática de stock: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void verificarLotesProximosAVencer() {
        log.info("Iniciando verificación de lotes próximos a vencer...");
        try {
            var alertas = alertaVencimientoService.verificarLotesProximosAVencer();
            log.info("Verificación de lotes completada: {} alertas de vencimiento generadas", alertas.size());
        } catch (Exception e) {
            log.error("Error en verificación de lotes próximos a vencer: {}", e.getMessage(), e);
        }
    }

    /**
     * HU-17: Verificación automática de cobertura de stock
     * Se ejecuta cada 2 horas para monitorear los días de cobertura
     */
    @Scheduled(cron = "0 0 */2 * * *")
    public void verificarCoberturaDeStock() {
        log.info("Iniciando verificación automática de cobertura de stock...");
        try {
            var alertas = alertaCoberturaService.verificarCoberturaYGenerarAlertas();
            log.info("Verificación de cobertura completada: {} alertas de cobertura generadas", alertas.size());
        } catch (Exception e) {
            log.error("Error en verificación de cobertura de stock: {}", e.getMessage(), e);
        }
    }
}
