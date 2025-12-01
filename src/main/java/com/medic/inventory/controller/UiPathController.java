package com.medic.inventory.controller;

import com.medic.inventory.dto.SolicitudCompraResponse;
import com.medic.inventory.dto.TrabajoRpaRequest;
import com.medic.inventory.dto.TrabajoRpaResponse;
import com.medic.inventory.dto.UiPathAlertaDTO;
import com.medic.inventory.service.SolicitudCompraService;
import com.medic.inventory.service.TrabajoRpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uipath")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permitir acceso desde UiPath
public class UiPathController {

    private final TrabajoRpaService trabajoRpaService;
    private final SolicitudCompraService solicitudCompraService;

    @GetMapping("/alertas-pendientes")
    public ResponseEntity<List<UiPathAlertaDTO>> obtenerAlertasPendientes() {
        List<UiPathAlertaDTO> alertas = trabajoRpaService.obtenerAlertasParaEmail();
        return ResponseEntity.ok(alertas);
    }

    @PostMapping("/trabajos")
    public ResponseEntity<TrabajoRpaResponse> registrarTrabajo(@RequestBody TrabajoRpaRequest request) {
        TrabajoRpaResponse response = trabajoRpaService.registrarTrabajoRpa(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/trabajos/{id}/completar")
    public ResponseEntity<Void> completarTrabajo(
            @PathVariable Long id,
            @RequestParam(defaultValue = "COMPLETADO") String estado) {
        trabajoRpaService.marcarTrabajoCompletado(id, estado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/trabajos")
    public ResponseEntity<List<TrabajoRpaResponse>> obtenerTodosLosTrabajos() {
        List<TrabajoRpaResponse> trabajos = trabajoRpaService.obtenerTodosLosTrabajos();
        return ResponseEntity.ok(trabajos);
    }

    @GetMapping("/solicitudes-pendientes")
    public ResponseEntity<List<SolicitudCompraResponse>> obtenerSolicitudesPendientes() {
        List<SolicitudCompraResponse> solicitudes = solicitudCompraService.obtenerSolicitudes();
        List<SolicitudCompraResponse> pendientes = solicitudes.stream()
            .filter(s -> !s.getEmailEnviado())
            .toList();
        return ResponseEntity.ok(pendientes);
    }
}
