package com.medic.inventory.service;

import com.medic.inventory.dto.ml.*;
import com.medic.inventory.entity.Product;
import com.medic.inventory.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MLPredictionService {

    private final RestTemplate restTemplate;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    @Value("${ml.service.url:http://localhost:5000}")
    private String mlServiceUrl;

    private Integer calcularStockTotal(Long productoId) {
        log.debug("Calculando stock total para producto ID: {}", productoId);

        String sql = "SELECT COALESCE(SUM(i.cantidad), 0) as total " +
                    "FROM lotes l " +
                    "JOIN inventario i ON l.id = i.lote_id " +
                    "WHERE l.producto_id = ?1";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, productoId);

        query.setHint("jakarta.persistence.cache.retrieveMode", "BYPASS");
        query.setHint("jakarta.persistence.cache.storeMode", "BYPASS");

        Object result = query.getSingleResult();
        log.debug("Resultado de query para producto ID {}: {} (tipo: {})",
                 productoId, result, result != null ? result.getClass().getName() : "null");

        Integer total = 0;
        if (result instanceof java.math.BigDecimal) {
            total = ((java.math.BigDecimal) result).intValue();
        } else if (result instanceof BigInteger) {
            total = ((BigInteger) result).intValue();
        } else if (result instanceof Long) {
            total = ((Long) result).intValue();
        } else if (result instanceof Integer) {
            total = (Integer) result;
        } else if (result != null) {
            total = Integer.valueOf(result.toString());
        }

        log.debug("Stock total calculado para producto ID {}: {}", productoId, total);
        return total;
    }

    private ProductoFeaturesDTO prepararFeatures(Product product) {
        int mesActual = LocalDate.now().getMonthValue();

        double total = calcularStockTotal(product.getId()).doubleValue();
        double minStock = product.getAlertValue() != null ? product.getAlertValue() : Math.max(total * 0.3, 50.0);

        double seed = (product.getId() % 100) / 100.0;

        double maxStock = total * (1.5 + seed * 0.5);

        double demandaSimulada = total * (0.3 + seed * 0.7);

        double desabastecidox = total <= 0 ? 1.0 : 0.0;
        double substockx = total < minStock ? 1.0 : 0.0;
        double dispo = total;

        double probStockPct;
        if (minStock > 0) {
            probStockPct = Math.min(total / minStock, 2.0);
        } else if (total > 0) {
            probStockPct = 1.2 + seed * 0.3;
        } else {
            probStockPct = 0.0;
        }

        double sobrestockPct = 0.0;
        if (maxStock > 0 && total > maxStock) {
            sobrestockPct = (total - maxStock) / maxStock;
        }

        double tendencia = (seed < 0.33) ? -0.15 : (seed < 0.66) ? 0.0 : 0.15;

        double probStockPctLag1 = Math.max(0.0, probStockPct - tendencia - (seed * 0.1));
        double probStockPctDiff1 = probStockPct - probStockPctLag1;

        double probStockPctMa3 = (probStockPct + probStockPctLag1 + Math.max(0.0, probStockPctLag1 - tendencia)) / 3.0;

        if (total < minStock && tendencia < 0) {
            probStockPct = Math.max(0.0, probStockPct * 0.7);
            substockx = 1.0;
        }

        if (total > maxStock && tendencia <= 0) {
            sobrestockPct = Math.max(sobrestockPct, 0.5);
        }

        // Log para debug
        log.debug("Features para producto {}: total={}, probStockPct={}, tendencia={}, sobrestockPct={}",
                product.getNombre(), total, probStockPct, tendencia, sobrestockPct);

        return ProductoFeaturesDTO.builder()
                .productoId(product.getId())
                .nombre(product.getNombre())
                .total(total)
                .desabastecidox(desabastecidox)
                .substockx(substockx)
                .dispo(dispo)
                .sobrestockPct(sobrestockPct)
                .probStockPct(probStockPct)
                .probStockPctLag1(probStockPctLag1)
                .probStockPctDiff1(probStockPctDiff1)
                .probStockPctMa3(probStockPctMa3)
                .mes(mesActual)
                .build();
    }

    public PicoDemandaResponseDTO predecirPicosDemanda() {
        log.info("Solicitando predicción de picos de demanda al servicio ML");

        try {
            List<Product> productos = productRepository.findAll();

            if (productos.isEmpty()) {
                log.warn("No hay productos en la base de datos");
                return PicoDemandaResponseDTO.builder()
                        .totalProductos(0)
                        .productosConPico(0)
                        .predicciones(new ArrayList<>())
                        .build();
            }

            log.info("Preparando predicciones para {} productos", productos.size());

            List<ProductoFeaturesDTO> features = productos.stream()
                    .map(this::prepararFeatures)
                    .collect(Collectors.toList());

            PicoDemandaRequestDTO request = PicoDemandaRequestDTO.builder()
                    .productos(features)
                    .build();

            String url = mlServiceUrl + "/predict/picos-demanda";

            PicoDemandaResponseDTO response = restTemplate.postForObject(
                    url,
                    request,
                    PicoDemandaResponseDTO.class
            );

            log.info("Predicción de picos de demanda completada: {} productos, {} con pico",
                    response.getTotalProductos(), response.getProductosConPico());

            return response;

        } catch (Exception e) {
            log.error("Error al predecir picos de demanda: {}", e.getMessage(), e);
            throw new RuntimeException("Error al comunicarse con el servicio ML: " + e.getMessage());
        }
    }

    public RiesgoVencimientoResponseDTO predecirRiesgoVencimiento() {
        log.info("Solicitando predicción de riesgo de vencimiento al servicio ML");

        try {
            List<Product> productos = productRepository.findAll();

            if (productos.isEmpty()) {
                log.warn("No hay productos en la base de datos");
                return RiesgoVencimientoResponseDTO.builder()
                        .totalProductos(0)
                        .productosEnRiesgo(0)
                        .predicciones(new ArrayList<>())
                        .build();
            }

            log.info("Preparando predicciones de riesgo para {} productos", productos.size());

            List<ProductoFeaturesDTO> features = productos.stream()
                    .map(this::prepararFeatures)
                    .collect(Collectors.toList());

            RiesgoVencimientoRequestDTO request = RiesgoVencimientoRequestDTO.builder()
                    .productos(features)
                    .build();

            String url = mlServiceUrl + "/predict/riesgo-vencimiento";

            RiesgoVencimientoResponseDTO response = restTemplate.postForObject(
                    url,
                    request,
                    RiesgoVencimientoResponseDTO.class
            );

            log.info("Predicción de riesgo de vencimiento completada: {} productos, {} en riesgo",
                    response.getTotalProductos(), response.getProductosEnRiesgo());

            return response;

        } catch (Exception e) {
            log.error("Error al predecir riesgo de vencimiento: {}", e.getMessage(), e);
            throw new RuntimeException("Error al comunicarse con el servicio ML: " + e.getMessage());
        }
    }

    public PicoDemandaResponseDTO predecirPicosDemandaProductos(List<Long> productIds) {
        log.info("Prediciendo picos de demanda para {} productos específicos", productIds.size());

        try {
            List<Product> productos = productRepository.findAllById(productIds);

            if (productos.isEmpty()) {
                return PicoDemandaResponseDTO.builder()
                        .totalProductos(0)
                        .productosConPico(0)
                        .predicciones(new ArrayList<>())
                        .build();
            }

            List<ProductoFeaturesDTO> features = productos.stream()
                    .map(this::prepararFeatures)
                    .collect(Collectors.toList());

            PicoDemandaRequestDTO request = PicoDemandaRequestDTO.builder()
                    .productos(features)
                    .build();

            String url = mlServiceUrl + "/predict/picos-demanda";

            return restTemplate.postForObject(url, request, PicoDemandaResponseDTO.class);

        } catch (Exception e) {
            log.error("Error al predecir picos de demanda: {}", e.getMessage(), e);
            throw new RuntimeException("Error al comunicarse con el servicio ML: " + e.getMessage());
        }
    }

    public RiesgoVencimientoResponseDTO predecirRiesgoVencimientoProductos(List<Long> productIds) {
        log.info("Prediciendo riesgo de vencimiento para {} productos específicos", productIds.size());

        try {
            List<Product> productos = productRepository.findAllById(productIds);

            if (productos.isEmpty()) {
                return RiesgoVencimientoResponseDTO.builder()
                        .totalProductos(0)
                        .productosEnRiesgo(0)
                        .predicciones(new ArrayList<>())
                        .build();
            }

            List<ProductoFeaturesDTO> features = productos.stream()
                    .map(this::prepararFeatures)
                    .collect(Collectors.toList());

            RiesgoVencimientoRequestDTO request = RiesgoVencimientoRequestDTO.builder()
                    .productos(features)
                    .build();

            String url = mlServiceUrl + "/predict/riesgo-vencimiento";

            return restTemplate.postForObject(url, request, RiesgoVencimientoResponseDTO.class);

        } catch (Exception e) {
            log.error("Error al predecir riesgo de vencimiento: {}", e.getMessage(), e);
            throw new RuntimeException("Error al comunicarse con el servicio ML: " + e.getMessage());
        }
    }

    @Async
    public CompletableFuture<PicoDemandaResponseDTO> predecirPicosDemandaAsync() {
        log.info("[ASYNC] Solicitando predicción de picos de demanda al servicio ML");
        try {
            PicoDemandaResponseDTO response = predecirPicosDemanda();
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            log.error("[ASYNC] Error al predecir picos de demanda: {}", e.getMessage(), e);
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<RiesgoVencimientoResponseDTO> predecirRiesgoVencimientoAsync() {
        log.info("[ASYNC] Solicitando predicción de riesgo de vencimiento al servicio ML");
        try {
            RiesgoVencimientoResponseDTO response = predecirRiesgoVencimiento();
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            log.error("[ASYNC] Error al predecir riesgo de vencimiento: {}", e.getMessage(), e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public boolean isMLServiceHealthy() {
        try {
            String url = mlServiceUrl + "/health";
            var response = restTemplate.getForObject(url, Object.class);
            log.info("Servicio ML está saludable");
            return true;
        } catch (Exception e) {
            log.error("Servicio ML no está disponible: {}", e.getMessage());
            return false;
        }
    }
}
