package com.medic.inventory.controller;

import com.medic.inventory.dto.EntradaRequest;
import com.medic.inventory.dto.EntradaResponse;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.UserRepository;
import com.medic.inventory.service.ComprobanteService;
import com.medic.inventory.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class EntradaController {

    private final EntradaService entradaService;
    private final UserRepository userRepository;
    private final ComprobanteService comprobanteService;

    @PostMapping
    public ResponseEntity<?> registrarEntrada(
            @Valid @RequestBody EntradaRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            EntradaResponse response = entradaService.registrarEntrada(request, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // HU-01: Devolver mensaje de error para manejo en frontend
            return ResponseEntity.badRequest()
                .body(java.util.Map.of("message", e.getMessage()));
        }
    }

    /**
     * HU-01: Descargar comprobante PDF de entrada
     */
    @GetMapping("/{id}/comprobante")
    public ResponseEntity<byte[]> descargarComprobante(@PathVariable Long id) {
        try {
            EntradaResponse entrada = entradaService.obtenerEntradaPorId(id);

            byte[] pdfBytes = comprobanteService.generarComprobanteEntrada(
                entrada.getId(),
                entrada.getProductoNombre(),
                entrada.getCantidad(),
                entrada.getProveedorNombre(),
                entrada.getRegistradoPorNombre(),
                entrada.getOcurrioEn()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "comprobante-entrada-" + id + ".pdf");

            return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
