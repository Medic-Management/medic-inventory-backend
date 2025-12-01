package com.medic.inventory.controller;

import com.medic.inventory.dto.AlertaResponse;
import com.medic.inventory.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class AlertaController {

    private final AlertaService alertaService;

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
}
