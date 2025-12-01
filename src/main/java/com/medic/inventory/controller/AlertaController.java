package com.medic.inventory.controller;

import com.medic.inventory.dto.AlertaResponse;
import com.medic.inventory.service.AlertaService;
import com.medic.inventory.service.ComprobanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class AlertaController {

    private final AlertaService alertaService;
    private final ComprobanteService comprobanteService;

    @GetMapping("/activas")
    public ResponseEntity<List<AlertaResponse>> obtenerAlertasActivas() {
        List<AlertaResponse> alertas = alertaService.obtenerAlertasActivas();
        return ResponseEntity.ok(alertas);
    }

    @PostMapping("/verificar-stock")
    public ResponseEntity<List<AlertaResponse>> verificarStockYGenerarAlertas() {
        List<AlertaResponse> alertasGeneradas = alertaService.verificarStockYGenerarAlertas();
        return ResponseEntity.ok(alertasGeneradas);
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<Void> resolverAlerta(@PathVariable Long id) {
        alertaService.resolverAlerta(id);
        return ResponseEntity.ok().build();
    }

    /**
     * HU-03 Escenario 2: Descargar reporte consolidado de alertas en PDF
     */
    @GetMapping("/reporte-consolidado")
    public ResponseEntity<byte[]> descargarReporteConsolidado(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta) {

        LocalDateTime fechaDesde = null;
        LocalDateTime fechaHasta = null;

        if (desde != null && hasta != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            fechaDesde = LocalDateTime.parse(desde, formatter);
            fechaHasta = LocalDateTime.parse(hasta, formatter);
        }

        Map<String, Object> reporteData = alertaService.obtenerReporteConsolidado(fechaDesde, fechaHasta);
        byte[] pdfBytes = comprobanteService.generarReporteAlertas(reporteData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte-alertas-" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
