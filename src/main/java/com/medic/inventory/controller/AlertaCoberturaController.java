package com.medic.inventory.controller;

import com.medic.inventory.dto.AlertaResponse;
import com.medic.inventory.service.AlertaCoberturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HU-17: Sistema de Cobertura Temporal
 * Controller para gestionar alertas de cobertura de stock
 */
@RestController
@RequestMapping("/api/alertas-cobertura")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlertaCoberturaController {

    private final AlertaCoberturaService alertaCoberturaService;

    /**
     * Obtiene todas las alertas de cobertura activas
     * @return Lista de alertas de cobertura baja activas
     */
    @GetMapping("/activas")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_JEFE_FARMACIA', 'ROLE_FARMACEUTICO')")
    public ResponseEntity<List<AlertaResponse>> obtenerAlertasCoberturaActivas() {
        try {
            List<AlertaResponse> alertas = alertaCoberturaService.obtenerAlertasCoberturaActivas();
            return ResponseEntity.ok(alertas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Ejecuta el proceso de verificación de cobertura y generación de alertas
     * @return Lista de alertas generadas en esta ejecución
     */
    @PostMapping("/verificar")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_JEFE_FARMACIA')")
    public ResponseEntity<List<AlertaResponse>> verificarCoberturaYGenerarAlertas() {
        try {
            List<AlertaResponse> alertasGeneradas = alertaCoberturaService.verificarCoberturaYGenerarAlertas();
            return ResponseEntity.ok(alertasGeneradas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para resumen de cobertura (estadísticas generales)
     * @return Información resumida de las alertas de cobertura
     */
    @GetMapping("/resumen")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_JEFE_FARMACIA', 'ROLE_FARMACEUTICO')")
    public ResponseEntity<?> obtenerResumenCobertura() {
        try {
            List<AlertaResponse> alertas = alertaCoberturaService.obtenerAlertasCoberturaActivas();

            long alertasAltas = alertas.stream()
                .filter(a -> "ALTA".equals(a.getNivel()))
                .count();

            long alertasMedias = alertas.stream()
                .filter(a -> "MEDIA".equals(a.getNivel()))
                .count();

            long alertasBajas = alertas.stream()
                .filter(a -> "BAJA".equals(a.getNivel()))
                .count();

            return ResponseEntity.ok(new ResumenCobertura(
                alertas.size(),
                alertasAltas,
                alertasMedias,
                alertasBajas
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DTO interno para el resumen de cobertura
     */
    private record ResumenCobertura(
        long totalAlertas,
        long alertasAltas,
        long alertasMedias,
        long alertasBajas
    ) {}
}
