package com.medic.inventory.controller;

import com.medic.inventory.dto.SolicitudCompraRequest;
import com.medic.inventory.dto.SolicitudCompraResponse;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.UserRepository;
import com.medic.inventory.service.SolicitudCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-compra")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class SolicitudCompraController {

    private final SolicitudCompraService solicitudCompraService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<SolicitudCompraResponse>> obtenerSolicitudes() {
        List<SolicitudCompraResponse> solicitudes = solicitudCompraService.obtenerSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCompraResponse> obtenerSolicitudPorId(@PathVariable Long id) {
        SolicitudCompraResponse solicitud = solicitudCompraService.obtenerSolicitudPorId(id);
        return ResponseEntity.ok(solicitud);
    }

    @PostMapping
    public ResponseEntity<SolicitudCompraResponse> crearSolicitud(
            @Valid @RequestBody SolicitudCompraRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            SolicitudCompraResponse response = solicitudCompraService.crearSolicitud(request, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        solicitudCompraService.actualizarEstado(id, estado);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/marcar-email-enviado")
    public ResponseEntity<Void> marcarEmailEnviado(@PathVariable Long id) {
        solicitudCompraService.marcarEmailEnviado(id);
        return ResponseEntity.ok().build();
    }
}
