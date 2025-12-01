package com.medic.inventory.controller;

import com.medic.inventory.dto.ml.PicoDemandaResponseDTO;
import com.medic.inventory.dto.ml.RiesgoVencimientoResponseDTO;
import com.medic.inventory.service.MLPredictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/ml")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MLPredictionController {

    private final MLPredictionService mlPredictionService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        boolean healthy = mlPredictionService.isMLServiceHealthy();

        Map<String, Object> response = new HashMap<>();
        response.put("ml_service_status", healthy ? "healthy" : "unhealthy");
        response.put("ml_service_url", "http://localhost:5000");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/predict/picos-demanda")
    public ResponseEntity<?> predecirPicosDemanda() {
        try {
            log.info("Endpoint /api/ml/predict/picos-demanda llamado");

            PicoDemandaResponseDTO response = mlPredictionService.predecirPicosDemanda();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error en predicción de picos de demanda", e);

            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al predecir picos de demanda");
            error.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/predict/riesgo-vencimiento")
    public ResponseEntity<?> predecirRiesgoVencimiento() {
        try {
            log.info("Endpoint /api/ml/predict/riesgo-vencimiento llamado");

            RiesgoVencimientoResponseDTO response = mlPredictionService.predecirRiesgoVencimiento();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error en predicción de riesgo de vencimiento", e);

            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al predecir riesgo de vencimiento");
            error.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/predict/picos-demanda")
    public ResponseEntity<?> predecirPicosDemandaProductos(@RequestBody List<Long> productIds) {
        try {
            log.info("Endpoint POST /api/ml/predict/picos-demanda llamado para {} productos", productIds.size());

            if (productIds == null || productIds.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Lista de IDs de productos vacía");
                return ResponseEntity.badRequest().body(error);
            }

            PicoDemandaResponseDTO response = mlPredictionService.predecirPicosDemandaProductos(productIds);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error en predicción de picos de demanda para productos específicos", e);

            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al predecir picos de demanda");
            error.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/predict/riesgo-vencimiento")
    public ResponseEntity<?> predecirRiesgoVencimientoProductos(@RequestBody List<Long> productIds) {
        try {
            log.info("Endpoint POST /api/ml/predict/riesgo-vencimiento llamado para {} productos", productIds.size());

            if (productIds == null || productIds.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Lista de IDs de productos vacía");
                return ResponseEntity.badRequest().body(error);
            }

            RiesgoVencimientoResponseDTO response = mlPredictionService.predecirRiesgoVencimientoProductos(productIds);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error en predicción de riesgo de vencimiento para productos específicos", e);

            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al predecir riesgo de vencimiento");
            error.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/predict/resumen")
    public ResponseEntity<?> obtenerResumenPredicciones() {
        try {
            log.info("Obteniendo resumen de predicciones ML (ASYNC)");

            // Ejecutar ambas predicciones EN PARALELO sin bloquear threads
            CompletableFuture<PicoDemandaResponseDTO> futurePicosDemanda =
                    mlPredictionService.predecirPicosDemandaAsync();
            CompletableFuture<RiesgoVencimientoResponseDTO> futureRiesgoVencimiento =
                    mlPredictionService.predecirRiesgoVencimientoAsync();

            // Esperar a que AMBAS terminen (se ejecutan en paralelo)
            CompletableFuture.allOf(futurePicosDemanda, futureRiesgoVencimiento).join();

            // Obtener resultados
            PicoDemandaResponseDTO picosDemanda = futurePicosDemanda.get();
            RiesgoVencimientoResponseDTO riesgoVencimiento = futureRiesgoVencimiento.get();

            Map<String, Object> resumen = new HashMap<>();
            resumen.put("picos_demanda", picosDemanda);
            resumen.put("riesgo_vencimiento", riesgoVencimiento);
            resumen.put("timestamp", System.currentTimeMillis());

            log.info("Resumen de predicciones ML completado (ASYNC)");
            return ResponseEntity.ok(resumen);

        } catch (Exception e) {
            log.error("Error al obtener resumen de predicciones", e);

            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al obtener resumen de predicciones");
            error.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
