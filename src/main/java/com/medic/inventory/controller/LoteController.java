package com.medic.inventory.controller;

import com.medic.inventory.dto.LoteResponse;
import com.medic.inventory.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
