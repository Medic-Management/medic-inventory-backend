package com.medic.inventory.controller;

import com.medic.inventory.dto.ProductRequest;
import com.medic.inventory.dto.ProductResponse;
import com.medic.inventory.dto.ProductWithInventoryDTO;
import com.medic.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request) {
        try {
            ProductResponse product = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (RuntimeException e) {
            // HU-12 Escenario 1: Retornar mensaje de error específico
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        try {
            ProductResponse product = productService.updateProduct(id, request);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // HU-12 Escenario 3: Retornar mensaje de error específico
            if (e.getMessage().contains("PRODUCTO_EN_USO")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(java.util.Map.of("message", e.getMessage().replace("PRODUCTO_EN_USO: ", "")));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(java.util.Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/critical")
    public ResponseEntity<List<ProductResponse>> getCriticalProducts() {
        List<ProductResponse> products = productService.getCriticalProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponse>> getLowStockProducts() {
        List<ProductResponse> products = productService.getLowStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/with-inventory")
    public ResponseEntity<List<ProductWithInventoryDTO>> getProductsWithInventory() {
        List<ProductWithInventoryDTO> products = productService.getProductsWithInventory();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}/lotes-disponibles")
    public ResponseEntity<List<Object>> getLotesDisponibles(@PathVariable Long id) {
        try {
            List<Object> lotes = productService.getLotesDisponiblesByProducto(id);
            return ResponseEntity.ok(lotes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
