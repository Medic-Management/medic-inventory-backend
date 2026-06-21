package com.medic.inventory.controller;

import com.medic.inventory.dto.BloqueoLoteRequest;
import com.medic.inventory.dto.LoteResponse;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.UserRepository;
import com.medic.inventory.service.LoteService;
import com.medic.inventory.service.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HU-11: Controlador para gestión de lotes con soporte FEFO
 * (First Expired, First Out - Primero en vencer, primero en salir)
 */
@RestController
@RequestMapping("/api/lotes")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class LoteController {

    private final LoteService loteService;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    /**
     * HU-11: Obtener lotes ordenados por fecha de vencimiento (FEFO)
     * para un producto en una sede específica.
     * Solo retorna lotes con stock disponible.
     *
     * @param productoId ID del producto
     * @param sedeId ID de la sede
     * @return Lista de lotes ordenados por fecha de vencimiento ASC
     */
    @GetMapping("/fefo")
    public ResponseEntity<List<LoteResponse>> obtenerLotesFEFO(
            @RequestParam Long productoId,
            @RequestParam Long sedeId) {
        List<LoteResponse> lotes = loteService.obtenerLotesFEFOPorProductoYSede(productoId, sedeId);
        return ResponseEntity.ok(lotes);
    }

    /**
     * HU-11: Obtener todos los lotes de un producto ordenados por FEFO
     * (sin filtrar por sede ni stock)
     *
     * @param productoId ID del producto
     * @return Lista de lotes ordenados por fecha de vencimiento ASC
     */
    @GetMapping("/producto/{productoId}/fefo")
    public ResponseEntity<List<LoteResponse>> obtenerLotesFEFOPorProducto(
            @PathVariable Long productoId) {
        List<LoteResponse> lotes = loteService.obtenerLotesFEFOPorProducto(productoId);
        return ResponseEntity.ok(lotes);
    }

    /**
     * HU-11: Obtener el primer lote según FEFO (el que vence más pronto)
     * para un producto en una sede específica.
     *
     * @param productoId ID del producto
     * @param sedeId ID de la sede
     * @return El lote que vence más pronto con stock disponible
     */
    @GetMapping("/fefo/primero")
    public ResponseEntity<LoteResponse> obtenerPrimerLoteFEFO(
            @RequestParam Long productoId,
            @RequestParam Long sedeId) {
        LoteResponse lote = loteService.obtenerPrimerLoteFEFO(productoId, sedeId);

        if (lote == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lote);
    }

    /**
     * HU-21: Obtener todos los lotes (activos e inactivos)
     *
     * @return Lista de todos los lotes
     */
    @GetMapping
    public ResponseEntity<List<LoteResponse>> obtenerTodosLosLotes() {
        List<LoteResponse> lotes = loteService.obtenerTodosLosLotes();
        return ResponseEntity.ok(lotes);
    }

    /**
     * HU-21: Obtener lotes bloqueados
     *
     * @return Lista de lotes bloqueados (estado = false)
     */
    @GetMapping("/bloqueados")
    public ResponseEntity<List<LoteResponse>> obtenerLotesBloqueados() {
        List<LoteResponse> lotes = loteService.obtenerLotesBloqueados();
        return ResponseEntity.ok(lotes);
    }

    /**
     * HU-21: Bloquear un lote
     * Establece el estado del lote a false para prevenir su dispensación
     *
     * @param loteId ID del lote a bloquear
     * @param request Información del bloqueo (motivo)
     * @return Lote bloqueado
     */
    @PutMapping("/{loteId}/bloquear")
    public ResponseEntity<?> bloquearLote(
            @PathVariable Long loteId,
            @RequestBody BloqueoLoteRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        try {
            LoteResponse lote = loteService.bloquearLote(loteId, request.getMotivo());
            registrarAuditoria(authentication, httpRequest, "LOTE_BLOQUEADO", loteId,
                "Bloqueo de lote " + lote.getCodigoLote() + " - Motivo: " + request.getMotivo());
            return ResponseEntity.ok(lote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * HU-21: Desbloquear un lote
     * Establece el estado del lote a true para permitir su dispensación
     *
     * @param loteId ID del lote a desbloquear
     * @param request Información del desbloqueo (motivo)
     * @return Lote desbloqueado
     */
    @PutMapping("/{loteId}/desbloquear")
    public ResponseEntity<?> desbloquearLote(
            @PathVariable Long loteId,
            @RequestBody BloqueoLoteRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        try {
            LoteResponse lote = loteService.desbloquearLote(loteId, request.getMotivo());
            registrarAuditoria(authentication, httpRequest, "LOTE_DESBLOQUEADO", loteId,
                "Desbloqueo de lote " + lote.getCodigoLote() + " - Motivo: " + request.getMotivo());
            return ResponseEntity.ok(lote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // CP016/HU-16: registra la accion en la bitacora de auditoria
    private void registrarAuditoria(Authentication authentication, HttpServletRequest httpRequest,
                                    String accion, Long entidadId, String descripcion) {
        try {
            if (authentication == null) return;
            User user = userRepository.findByEmail(authentication.getName()).orElse(null);
            if (user == null) return;
            String ip = httpRequest.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty()) ip = httpRequest.getRemoteAddr();
            auditLogService.logAction(user.getId(), user.getNombreCompleto(), accion,
                "Lote", entidadId, descripcion, ip);
        } catch (Exception e) {
            // la auditoria no debe romper la operacion principal
        }
    }

    private static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
