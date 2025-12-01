package com.medic.inventory.controller;

import com.medic.inventory.dto.SolicitudCompraRequest;
import com.medic.inventory.dto.SolicitudCompraResponse;
import com.medic.inventory.dto.SugerenciaPedidoResponse;
import com.medic.inventory.service.SolicitudCompraService;
import com.medic.inventory.service.SugerenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sugerencias")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class SugerenciaController {

    private final SugerenciaService sugerenciaService;
    private final SolicitudCompraService solicitudCompraService;

    /**
     * HU-07 Escenario 1: Obtener sugerencias de pedido
     */
    @GetMapping("/pedidos")
    public ResponseEntity<List<SugerenciaPedidoResponse>> obtenerSugerenciasPedido() {
        List<SugerenciaPedidoResponse> sugerencias = sugerenciaService.obtenerSugerenciasPedido();
        return ResponseEntity.ok(sugerencias);
    }

    /**
     * HU-07 Escenario 2: Crear borrador desde sugerencia
     */
    @PostMapping("/crear-borrador")
    public ResponseEntity<SolicitudCompraResponse> crearBorradorDesdeSugerencia(
            @RequestBody SugerenciaPedidoResponse sugerencia,
            Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());

        // Convertir sugerencia a request de solicitud
        SolicitudCompraRequest request = new SolicitudCompraRequest();
        request.setProductoId(sugerencia.getProductoId());
        request.setProveedorId(sugerencia.getProveedorId());
        request.setCantidadSolicitada(sugerencia.getCantidadSugerida());
        request.setNotas("Borrador creado desde sugerencia: " + sugerencia.getJustificacion());

        // Crear borrador
        SolicitudCompraResponse borrador = solicitudCompraService.crearBorrador(request, userId);

        return ResponseEntity.ok(borrador);
    }
}
