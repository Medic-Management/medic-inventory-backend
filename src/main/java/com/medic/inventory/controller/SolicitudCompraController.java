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
    private final com.medic.inventory.service.AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<SolicitudCompraResponse>> obtenerSolicitudes() {
        List<SolicitudCompraResponse> solicitudes = solicitudCompraService.obtenerSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    // CP011: Obtener pedidos aprobados para recepción de mercadería
    @GetMapping("/aprobados")
    public ResponseEntity<List<SolicitudCompraResponse>> obtenerPedidosAprobados() {
        List<SolicitudCompraResponse> pedidos = solicitudCompraService.obtenerPedidosAprobados();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCompraResponse> obtenerSolicitudPorId(@PathVariable Long id) {
        SolicitudCompraResponse solicitud = solicitudCompraService.obtenerSolicitudPorId(id);
        return ResponseEntity.ok(solicitud);
    }

    @PostMapping
    public ResponseEntity<?> crearSolicitud(
            @Valid @RequestBody SolicitudCompraRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            SolicitudCompraResponse response = solicitudCompraService.crearSolicitud(request, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
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

    // HU-08: Editar borrador
    @PutMapping("/{id}/editar")
    public ResponseEntity<SolicitudCompraResponse> editarBorrador(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudCompraRequest request) {
        try {
            SolicitudCompraResponse response = solicitudCompraService.editarBorrador(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // HU-08: Aprobar borrador
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudCompraResponse> aprobarBorrador(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            SolicitudCompraResponse response = solicitudCompraService.aprobarBorrador(id, user.getId());
            // CP016/HU-16: registrar la aprobación en la bitácora
            try {
                auditLogService.logAction(user.getId(), user.getNombreCompleto(),
                    "SOLICITUD_APROBADA", "SolicitudCompra", id,
                    "Aprobación de la solicitud #" + id, null);
            } catch (Exception ignore) { }
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Log para no tener un 400 ciego (antes no se veia el motivo en backend)
            System.err.println("[aprobarBorrador] Error aprobando solicitud " + id + ": " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // HU-09: Registrar fallo de envío
    @PutMapping("/{id}/registrar-fallo")
    public ResponseEntity<Void> registrarFalloEnvio(
            @PathVariable Long id,
            @RequestParam String motivo) {
        solicitudCompraService.registrarFalloEnvio(id, motivo);
        return ResponseEntity.ok().build();
    }

    // HU-09/HU-19: Reenviar pedido con fallo
    @PutMapping("/{id}/reenviar")
    public ResponseEntity<SolicitudCompraResponse> reenviarPedido(@PathVariable Long id) {
        try {
            SolicitudCompraResponse response = solicitudCompraService.reenviarPedido(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // HU-04: Generar pedido automático
    @PostMapping("/generar-automatico")
    public ResponseEntity<SolicitudCompraResponse> generarPedidoAutomatico(
            @RequestParam Long productoId,
            @RequestParam Long proveedorId,
            @RequestParam Integer cantidad,
            @RequestParam Integer stockActual,
            @RequestParam Integer nivelAlerta) {
        try {
            SolicitudCompraResponse response = solicitudCompraService.generarPedidoAutomatico(
                productoId, proveedorId, cantidad, stockActual, nivelAlerta);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // CP010: Confirmar acuse de recibo manualmente
    @PostMapping("/{id}/confirmar-acuse")
    public ResponseEntity<?> confirmarAcuseRecibo(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            SolicitudCompraResponse response = solicitudCompraService.confirmarAcuseRecibo(id, user.getId());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    // CP010: Obtener solicitudes sin confirmar (> 48 horas)
    @GetMapping("/sin-confirmar")
    public ResponseEntity<List<SolicitudCompraResponse>> obtenerSolicitudesSinConfirmar() {
        List<SolicitudCompraResponse> solicitudes = solicitudCompraService.obtenerSolicitudesSinConfirmar();
        return ResponseEntity.ok(solicitudes);
    }

    // CP010: Obtener solicitudes enviadas (estado SENT)
    @GetMapping("/enviadas")
    public ResponseEntity<List<SolicitudCompraResponse>> obtenerSolicitudesEnviadas() {
        List<SolicitudCompraResponse> solicitudes = solicitudCompraService.obtenerSolicitudesEnviadas();
        return ResponseEntity.ok(solicitudes);
    }

    // CP009: Obtener solicitudes APPROVED (aprobadas pendientes de envío)
    @GetMapping("/aprobadas")
    public ResponseEntity<List<SolicitudCompraResponse>> obtenerSolicitudesAprobadas() {
        List<SolicitudCompraResponse> solicitudes = solicitudCompraService.obtenerSolicitudesAprobadas();
        return ResponseEntity.ok(solicitudes);
    }

    // CP009: Enviar pedido individual al proveedor mediante RPA
    @PostMapping("/{id}/enviar-al-proveedor")
    public ResponseEntity<?> enviarAlProveedor(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            SolicitudCompraResponse response = solicitudCompraService.enviarAlProveedor(id, user.getId());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    // CP009: Enviar TODAS las solicitudes aprobadas al proveedor
    @PostMapping("/enviar-todas-al-proveedor")
    public ResponseEntity<?> enviarTodasAlProveedor(Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            int enviadas = solicitudCompraService.enviarTodasAlProveedor(user.getId());
            return ResponseEntity.ok(java.util.Map.of(
                "message", "Pedidos enviados exitosamente (CP009)",
                "cantidad", enviadas
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }
}
