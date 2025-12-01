package com.medic.inventory.service;

import com.medic.inventory.dto.SolicitudCompraRequest;
import com.medic.inventory.dto.SolicitudCompraResponse;
import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitudCompraService {

    private final RestockRequestRepository restockRequestRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final SedeRepository sedeRepository;

    @Transactional
    public SolicitudCompraResponse crearSolicitud(SolicitudCompraRequest request, Long userId) {
        Product producto = productRepository.findById(request.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Supplier proveedor = supplierRepository.findById(request.getProveedorId())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Sede sede = sedeRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        RestockRequest solicitud = new RestockRequest();
        solicitud.setSede(sede);
        solicitud.setProduct(producto);
        solicitud.setSupplier(proveedor);
        solicitud.setSolicitadoPor(usuario);
        solicitud.setRequestedQuantity(request.getCantidadSolicitada());
        solicitud.setNotas(request.getNotas());
        solicitud.setStatus(RestockRequest.RestockStatus.PENDING);
        solicitud.setEmailSent(false);
        solicitud.setAprobada(true);
        solicitud.setOrigen(1);

        solicitud = restockRequestRepository.save(solicitud);
        return mapToResponse(solicitud);
    }

    public List<SolicitudCompraResponse> obtenerSolicitudes() {
        return restockRequestRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public SolicitudCompraResponse obtenerSolicitudPorId(Long id) {
        RestockRequest solicitud = restockRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        return mapToResponse(solicitud);
    }

    @Transactional
    public void actualizarEstado(Long id, String nuevoEstado) {
        RestockRequest solicitud = restockRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        try {
            RestockRequest.RestockStatus status = RestockRequest.RestockStatus.valueOf(nuevoEstado);
            solicitud.setStatus(status);
            restockRequestRepository.save(solicitud);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado inválido: " + nuevoEstado);
        }
    }

    @Transactional
    public void marcarEmailEnviado(Long id) {
        RestockRequest solicitud = restockRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEmailSent(true);
        if (solicitud.getStatus() == RestockRequest.RestockStatus.PENDING) {
            solicitud.setStatus(RestockRequest.RestockStatus.SENT);
        }
        restockRequestRepository.save(solicitud);
    }

    private SolicitudCompraResponse mapToResponse(RestockRequest solicitud) {
        SolicitudCompraResponse response = new SolicitudCompraResponse();
        response.setId(solicitud.getId());

        if (solicitud.getSede() != null) {
            response.setSedeId(solicitud.getSede().getId());
            response.setSedeNombre(solicitud.getSede().getNombre());
        }

        if (solicitud.getProduct() != null) {
            response.setProductoId(solicitud.getProduct().getId());
            response.setProductoNombre(solicitud.getProduct().getNombre());
        }

        if (solicitud.getSupplier() != null) {
            response.setProveedorId(solicitud.getSupplier().getId());
            response.setProveedorNombre(solicitud.getSupplier().getNombre());
        }

        if (solicitud.getSolicitadoPor() != null) {
            response.setSolicitadoPorId(solicitud.getSolicitadoPor().getId());
            response.setSolicitadoPorNombre(solicitud.getSolicitadoPor().getNombreCompleto());
        }

        response.setCantidadSolicitada(solicitud.getRequestedQuantity());
        response.setEstado(solicitud.getStatus() != null ? solicitud.getStatus().name() : "PENDING");
        response.setNotas(solicitud.getNotas());
        response.setEmailEnviado(solicitud.getEmailSent());
        response.setCreadaEn(solicitud.getCreadaEn());

        return response;
    }
}
