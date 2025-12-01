package com.medic.inventory.controller;

import com.medic.inventory.dto.DispensacionRequest;
import com.medic.inventory.dto.DispensacionResponse;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.UserRepository;
import com.medic.inventory.service.DispensacionService;
import com.medic.inventory.service.ComprobanteService;
import com.medic.inventory.service.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dispensaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class DispensacionController {

    private final DispensacionService dispensacionService;
    private final UserRepository userRepository;
    private final ComprobanteService comprobanteService;
    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<DispensacionResponse> registrarDispensacion(
            @Valid @RequestBody DispensacionRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            DispensacionResponse response = dispensacionService.registrarDispensacion(request, user.getId());

            String ipAddress = getClientIpAddress(httpRequest);
            auditLogService.logAction(
                user.getId(),
                user.getNombreCompleto(),
                "DISPENSACION_CREADA",
                "Dispensacion",
                response.getId(),
                String.format("Dispensación de %d unidades de %s", response.getCantidad(), response.getProductoNombre()),
                ipAddress
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }

    @GetMapping("/mis-dispensaciones")
    public ResponseEntity<List<DispensacionResponse>> obtenerMisDispensaciones(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<DispensacionResponse> dispensaciones = dispensacionService.obtenerMisDispensaciones(user.getId());
        return ResponseEntity.ok(dispensaciones);
    }

    @GetMapping("/{id}/comprobante")
    public ResponseEntity<byte[]> descargarComprobante(@PathVariable Long id) {
        try {
            DispensacionResponse dispensacion = dispensacionService.obtenerDispensacionPorId(id);

            byte[] pdfBytes = comprobanteService.generarComprobanteDispensacion(
                dispensacion.getId(),
                dispensacion.getProductoNombre(),
                dispensacion.getCantidad(),
                "LOTE-" + id,
                dispensacion.getDispensadoPorNombre(),
                dispensacion.getOcurrioEn()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "comprobante-dispensacion-" + id + ".pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
