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
    private final UmbralStockRepository umbralStockRepository;

    @Transactional
    public SolicitudCompraResponse crearSolicitud(SolicitudCompraRequest request, Long userId) {
        return crearSolicitudConEstado(request, userId, RestockRequest.RestockStatus.DRAFT, false);
    }

    @Transactional
    public SolicitudCompraResponse crearBorrador(SolicitudCompraRequest request, Long userId) {
        // HU-04: Validar duplicidad - no crear si ya existe pedido pendiente
        if (existePedidoPendiente(request.getProductoId(), request.getProveedorId())) {
            throw new RuntimeException("Ya existe un pedido pendiente para este producto y proveedor");
        }

        return crearSolicitudConEstado(request, userId, RestockRequest.RestockStatus.DRAFT, false);
    }

    @Transactional
    public SolicitudCompraResponse generarPedidoAutomatico(Long productoId, Long proveedorId, Integer cantidad,
                                                          Integer stockActual, Integer nivelAlerta) {
        // HU-04: Validar duplicidad
        if (existePedidoPendiente(productoId, proveedorId)) {
            throw new RuntimeException("Ya existe un pedido pendiente - no se genera duplicado");
        }

        Product producto = productRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Supplier proveedor = supplierRepository.findById(proveedorId)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Sede sede = sedeRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        // HU-04.3: Ajustar cantidad según stock máximo configurado en UmbralStock
        int cantidadAjustada = cantidad;
        String motivoAjuste = null;

        // Buscar umbral configurado para este producto/sede
        var umbralOpt = umbralStockRepository.findBySedeIdAndProductoId(sede.getId(), productoId);
        if (umbralOpt.isPresent()) {
            UmbralStock umbral = umbralOpt.get();
            Integer stockMaximo = umbral.getStockMaximo();

            if (stockMaximo != null && stockMaximo > 0) {
                // Calcular stock futuro si se hace este pedido
                int stockFuturo = stockActual + cantidad;

                // Si excede el máximo, ajustar la cantidad
                if (stockFuturo > stockMaximo) {
                    int cantidadOptima = stockMaximo - stockActual;

                    // Solo ajustar si la cantidad óptima es positiva
                    if (cantidadOptima > 0 && cantidadOptima < cantidad) {
                        cantidadAjustada = cantidadOptima;
                        motivoAjuste = String.format(
                            "Ajustado de %d a %d unidades para no exceder stock máximo de %d (stock actual: %d)",
                            cantidad, cantidadAjustada, stockMaximo, stockActual
                        );
                    } else if (cantidadOptima <= 0) {
                        // Si ya estamos en o sobre el máximo, cancelar pedido
                        throw new RuntimeException(
                            String.format("Stock actual (%d) ya alcanzó o superó el máximo configurado (%d). Pedido automático cancelado.",
                                stockActual, stockMaximo)
                        );
                    }
                }
            }
        }

        RestockRequest solicitud = new RestockRequest();
        solicitud.setSede(sede);
        solicitud.setProduct(producto);
        solicitud.setSupplier(proveedor);
        solicitud.setSolicitadoPor(null); // Generado automáticamente
        solicitud.setRequestedQuantity(cantidadAjustada);
        solicitud.setCantidadAjustada(cantidad != cantidadAjustada ? cantidadAjustada : null);
        solicitud.setMotivoAjuste(motivoAjuste);
        solicitud.setCurrentStock(stockActual);
        solicitud.setAlertLevel(nivelAlerta);
        solicitud.setNotas("Pedido generado automáticamente por bajo stock");
        solicitud.setStatus(RestockRequest.RestockStatus.DRAFT);
        solicitud.setGeneradoAutomaticamente(true);
        solicitud.setEmailSent(false);
        solicitud.setAprobada(false);
        solicitud.setOrigen(2); // 2 = automático

        solicitud = restockRequestRepository.save(solicitud);
        return mapToResponse(solicitud);
    }

    private SolicitudCompraResponse crearSolicitudConEstado(SolicitudCompraRequest request, Long userId,
                                                            RestockRequest.RestockStatus estado, boolean generadoAuto) {
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
        solicitud.setStatus(estado);
        solicitud.setGeneradoAutomaticamente(generadoAuto);
        solicitud.setEmailSent(false);
        solicitud.setAprobada(estado == RestockRequest.RestockStatus.PENDING || estado == RestockRequest.RestockStatus.SENT);
        solicitud.setOrigen(generadoAuto ? 2 : 1);

        solicitud = restockRequestRepository.save(solicitud);
        return mapToResponse(solicitud);
    }

    private boolean existePedidoPendiente(Long productoId, Long proveedorId) {
        // Buscar pedidos en estado DRAFT, PENDING, SENT (estados activos)
        List<RestockRequest> pedidosPendientes = restockRequestRepository.findAll().stream()
            .filter(r -> r.getProduct() != null && r.getProduct().getId().equals(productoId))
            .filter(r -> r.getSupplier() != null && r.getSupplier().getId().equals(proveedorId))
            .filter(r -> r.getStatus() == RestockRequest.RestockStatus.DRAFT ||
                        r.getStatus() == RestockRequest.RestockStatus.PENDING ||
                        r.getStatus() == RestockRequest.RestockStatus.SENT)
            .collect(Collectors.toList());

        return !pedidosPendientes.isEmpty();
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

    // HU-08: Editar borrador
    @Transactional
    public SolicitudCompraResponse editarBorrador(Long id, SolicitudCompraRequest request) {
        RestockRequest solicitud = restockRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitud.getStatus() != RestockRequest.RestockStatus.DRAFT) {
            throw new RuntimeException("Solo se pueden editar solicitudes en estado BORRADOR");
        }

        // Actualizar campos editables
        if (request.getProductoId() != null && request.getProductoId() > 0) {
            Product producto = productRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            solicitud.setProduct(producto);
        }

        if (request.getProveedorId() != null && request.getProveedorId() > 0) {
            Supplier proveedor = supplierRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            solicitud.setSupplier(proveedor);
        }

        if (request.getCantidadSolicitada() != null && request.getCantidadSolicitada() > 0) {
            solicitud.setRequestedQuantity(request.getCantidadSolicitada());
        }

        if (request.getNotas() != null) {
            solicitud.setNotas(request.getNotas());
        }

        solicitud = restockRequestRepository.save(solicitud);
        return mapToResponse(solicitud);
    }

    // HU-08: Aprobar borrador
    @Transactional
    public SolicitudCompraResponse aprobarBorrador(Long id, Long userId) {
        RestockRequest solicitud = restockRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitud.getStatus() != RestockRequest.RestockStatus.DRAFT) {
            throw new RuntimeException("Solo se pueden aprobar solicitudes en estado BORRADOR");
        }

        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        solicitud.setStatus(RestockRequest.RestockStatus.PENDING);
        solicitud.setAprobada(true);
        solicitud.setAprobadoPor(userId);
        solicitud.setFechaAprobacion(java.time.LocalDateTime.now());

        solicitud = restockRequestRepository.save(solicitud);
        return mapToResponse(solicitud);
    }

    // HU-09: Registrar fallo de envío
    @Transactional
    public void registrarFalloEnvio(Long id, String motivoFallo) {
        RestockRequest solicitud = restockRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setStatus(RestockRequest.RestockStatus.SEND_FAILED);
        solicitud.setEmailSent(false);
        solicitud.setNotas((solicitud.getNotas() != null ? solicitud.getNotas() + "\n" : "") +
                          "FALLO DE ENVÍO: " + motivoFallo);

        restockRequestRepository.save(solicitud);
    }

    // HU-09/HU-19: Reenviar pedido con fallo
    @Transactional
    public SolicitudCompraResponse reenviarPedido(Long id) {
        RestockRequest solicitud = restockRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitud.getStatus() != RestockRequest.RestockStatus.SEND_FAILED) {
            throw new RuntimeException("Solo se pueden reenviar solicitudes con fallo de envío");
        }

        solicitud.setStatus(RestockRequest.RestockStatus.PENDING);
        solicitud.setEmailSent(false);

        solicitud = restockRequestRepository.save(solicitud);
        return mapToResponse(solicitud);
    }

    // HU-19: Obtener solicitudes por estado
    public List<SolicitudCompraResponse> obtenerSolicitudesPorEstado(RestockRequest.RestockStatus estado) {
        return restockRequestRepository.findAll().stream()
            .filter(r -> r.getStatus() == estado)
            .map(this::mapToResponse)
            .collect(Collectors.toList());
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
