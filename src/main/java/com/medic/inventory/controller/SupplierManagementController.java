package com.medic.inventory.controller;

import com.medic.inventory.dto.SupplierRequest;
import com.medic.inventory.dto.SupplierResponse;
import com.medic.inventory.service.SupplierManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class SupplierManagementController {

    private final SupplierManagementService supplierManagementService;

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        List<SupplierResponse> suppliers = supplierManagementService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Long id) {
        SupplierResponse supplier = supplierManagementService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@Valid @RequestBody SupplierRequest request) {
        try {
            SupplierResponse supplier = supplierManagementService.createSupplier(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request) {
        try {
            SupplierResponse supplier = supplierManagementService.updateSupplier(id, request);
            return ResponseEntity.ok(supplier);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleSupplierStatus(@PathVariable Long id) {
        supplierManagementService.toggleSupplierStatus(id);
        return ResponseEntity.ok().build();
    }
}
