package com.medic.inventory.service;

import com.medic.inventory.dto.UmbralStockRequest;
import com.medic.inventory.dto.UmbralStockResponse;
import com.medic.inventory.entity.Product;
import com.medic.inventory.entity.Sede;
import com.medic.inventory.entity.UmbralStock;
import com.medic.inventory.repository.ProductRepository;
import com.medic.inventory.repository.SedeRepository;
import com.medic.inventory.repository.UmbralStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UmbralStockService {

    private final UmbralStockRepository umbralStockRepository;
    private final SedeRepository sedeRepository;
    private final ProductRepository productRepository;

    public List<UmbralStockResponse> getAllUmbrales() {
        return umbralStockRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public UmbralStockResponse getUmbralById(Long id) {
        UmbralStock umbral = umbralStockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Umbral no encontrado"));
        return mapToResponse(umbral);
    }

    @Transactional
    public UmbralStockResponse createUmbral(UmbralStockRequest request) {
        Sede sede = sedeRepository.findById(request.getSedeId())
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        Product producto = productRepository.findById(request.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        umbralStockRepository.findBySedeIdAndProductoId(request.getSedeId(), request.getProductoId())
            .ifPresent(existing -> {
                throw new RuntimeException("Ya existe un umbral para esta sede y producto");
            });

        UmbralStock umbral = new UmbralStock();
        umbral.setSede(sede);
        umbral.setProducto(producto);
        umbral.setMinimo(request.getMinimo());
        umbral.setPuntoPedido(request.getPuntoPedido());
        umbral.setStockSeguridad(request.getStockSeguridad());
        umbral.setUmbralCoberturaDias(request.getUmbralCoberturaDias());
        umbral.setStockMaximo(request.getStockMaximo());

        umbral = umbralStockRepository.save(umbral);
        return mapToResponse(umbral);
    }

    @Transactional
    public UmbralStockResponse updateUmbral(Long id, UmbralStockRequest request) {
        UmbralStock umbral = umbralStockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Umbral no encontrado"));

        Sede sede = sedeRepository.findById(request.getSedeId())
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        Product producto = productRepository.findById(request.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        umbral.setSede(sede);
        umbral.setProducto(producto);
        umbral.setMinimo(request.getMinimo());
        umbral.setPuntoPedido(request.getPuntoPedido());
        umbral.setStockSeguridad(request.getStockSeguridad());
        umbral.setUmbralCoberturaDias(request.getUmbralCoberturaDias());
        umbral.setStockMaximo(request.getStockMaximo());

        umbral = umbralStockRepository.save(umbral);
        return mapToResponse(umbral);
    }

    @Transactional
    public void deleteUmbral(Long id) {
        if (!umbralStockRepository.existsById(id)) {
            throw new RuntimeException("Umbral no encontrado");
        }
        umbralStockRepository.deleteById(id);
    }

    private UmbralStockResponse mapToResponse(UmbralStock umbral) {
        UmbralStockResponse response = new UmbralStockResponse();
        response.setId(umbral.getId());
        response.setSedeId(umbral.getSede().getId());
        response.setSedeNombre(umbral.getSede().getNombre());
        response.setProductoId(umbral.getProducto().getId());
        response.setProductoNombre(umbral.getProducto().getNombre());
        response.setMinimo(umbral.getMinimo());
        response.setPuntoPedido(umbral.getPuntoPedido());
        response.setStockSeguridad(umbral.getStockSeguridad());
        response.setUmbralCoberturaDias(umbral.getUmbralCoberturaDias());
        response.setStockMaximo(umbral.getStockMaximo());
        return response;
    }
}
