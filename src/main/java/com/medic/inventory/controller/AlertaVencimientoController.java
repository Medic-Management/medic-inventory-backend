package com.medic.inventory.controller;

import com.medic.inventory.dto.AlertaVencimientoResponse;
import com.medic.inventory.service.AlertaVencimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas-vencimiento")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class AlertaVencimientoController {

    private final AlertaVencimientoService alertaVencimientoService;

    @GetMapping
    public ResponseEntity<List<AlertaVencimientoResponse>> obtenerAlertasVencimiento() {
        List<AlertaVencimientoResponse> alertas = alertaVencimientoService.verificarLotesProximosAVencer();
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/criticas")
    public ResponseEntity<List<AlertaVencimientoResponse>> obtenerAlertasCriticas() {
        List<AlertaVencimientoResponse> alertas = alertaVencimientoService.obtenerAlertasCriticas();
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<AlertaVencimientoResponse>> obtenerAlertasPorNivel(@PathVariable String nivel) {
        List<AlertaVencimientoResponse> alertas = alertaVencimientoService.obtenerAlertasPorNivel(nivel);
        return ResponseEntity.ok(alertas);
    }
}
