package com.medic.inventory.controller;

import com.medic.inventory.dto.UmbralStockRequest;
import com.medic.inventory.dto.UmbralStockResponse;
import com.medic.inventory.service.UmbralStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/umbrales")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class UmbralStockController {

    private final UmbralStockService umbralStockService;

    @GetMapping
    public ResponseEntity<List<UmbralStockResponse>> getAllUmbrales() {
        List<UmbralStockResponse> umbrales = umbralStockService.getAllUmbrales();
        return ResponseEntity.ok(umbrales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UmbralStockResponse> getUmbralById(@PathVariable Long id) {
        UmbralStockResponse umbral = umbralStockService.getUmbralById(id);
        return ResponseEntity.ok(umbral);
    }

    @PostMapping
    public ResponseEntity<UmbralStockResponse> createUmbral(@Valid @RequestBody UmbralStockRequest request) {
        try {
            UmbralStockResponse umbral = umbralStockService.createUmbral(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(umbral);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UmbralStockResponse> updateUmbral(
            @PathVariable Long id,
            @Valid @RequestBody UmbralStockRequest request) {
        try {
            UmbralStockResponse umbral = umbralStockService.updateUmbral(id, request);
            return ResponseEntity.ok(umbral);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUmbral(@PathVariable Long id) {
        umbralStockService.deleteUmbral(id);
        return ResponseEntity.ok().build();
    }
}
