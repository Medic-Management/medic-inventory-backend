package com.medic.inventory.service;

import com.medic.inventory.dto.DispensacionRequest;
import com.medic.inventory.dto.DispensacionResponse;
import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DispensacionService {

    private final MovimientoRepository movimientoRepository;
    private final InventarioRepository inventarioRepository;
    private final ProductRepository productRepository;
    private final LoteRepository loteRepository;
    private final UserRepository userRepository;
    private final AlertaService alertaService;
    private final LoteService loteService;

    @Transactional
    public DispensacionResponse registrarDispensacion(DispensacionRequest request, Long userId) {
        Product producto = productRepository.findById(request.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // HU-11: FEFO - Si no se especifica loteId o es 0, seleccionar automáticamente el lote que vence primero
        Long loteIdFinal = request.getLoteId();
        boolean fefoAplicado = false;

        if (loteIdFinal == null || loteIdFinal == 0) {
            var primerLoteFEFO = loteService.obtenerPrimerLoteFEFO(request.getProductoId(), 1L);
            if (primerLoteFEFO == null) {
                throw new RuntimeException("No hay lotes disponibles con stock para este producto");
            }
            loteIdFinal = primerLoteFEFO.getId();
            fefoAplicado = true;
        }

        Lote lote = loteRepository.findById(loteIdFinal)
            .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Inventario inventario = inventarioRepository.findBySedeIdAndLoteId(1L, loteIdFinal)
            .orElseThrow(() -> new RuntimeException("No hay stock disponible para este lote"));

        if (inventario.getCantidad() < request.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + inventario.getCantidad());
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setOcurrioEn(LocalDateTime.now());
        movimiento.setSedeId(1L);
        movimiento.setLoteId(request.getLoteId());
        movimiento.setTipo("SALIDA");
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo() != null ? request.getMotivo() : "Dispensación");
        movimiento.setDocRef(request.getDocumentoReferencia());
        movimiento.setCreadoPor(userId.intValue());

        movimiento = movimientoRepository.save(movimiento);

        inventario.setCantidad(inventario.getCantidad() - request.getCantidad());
        inventarioRepository.save(inventario);

        alertaService.verificarStockYGenerarAlertas();

        DispensacionResponse response = new DispensacionResponse();
        response.setId(movimiento.getId());
        response.setProductoId(producto.getId());
        response.setProductoNombre(producto.getNombre());
        response.setLoteId(lote.getId());
        response.setCodigoLote(lote.getCodigoProductoProv());
        response.setCantidad(request.getCantidad());
        response.setMotivo(movimiento.getMotivo());
        response.setDocumentoReferencia(movimiento.getDocRef());
        response.setOcurrioEn(movimiento.getOcurrioEn());
        response.setDispensadoPor(userId);
        response.setDispensadoPorNombre(usuario.getNombreCompleto());

        return response;
    }

    public List<DispensacionResponse> obtenerMisDispensaciones(Long userId) {
        List<Movimiento> movimientos = movimientoRepository.findByCreadoPorAndTipo(userId.intValue(), "SALIDA");

        return movimientos.stream().map(mov -> {
            DispensacionResponse response = new DispensacionResponse();
            response.setId(mov.getId());
            response.setCantidad(mov.getCantidad());
            response.setMotivo(mov.getMotivo());
            response.setDocumentoReferencia(mov.getDocRef());
            response.setOcurrioEn(mov.getOcurrioEn());
            response.setDispensadoPor(mov.getCreadoPor() != null ? mov.getCreadoPor().longValue() : null);

            loteRepository.findById(mov.getLoteId()).ifPresent(lote -> {
                response.setLoteId(lote.getId());
                response.setCodigoLote(lote.getCodigoProductoProv());

                if (lote.getProducto() != null) {
                    response.setProductoId(lote.getProducto().getId());
                    response.setProductoNombre(lote.getProducto().getNombre());
                }
            });

            return response;
        }).collect(Collectors.toList());
    }

    /**
     * Obtener dispensación (movimiento de salida) por ID
     */
    public DispensacionResponse obtenerDispensacionPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Dispensación no encontrada"));

        if (!"SALIDA".equals(movimiento.getTipo())) {
            throw new RuntimeException("El movimiento especificado no es una dispensación/salida");
        }

        DispensacionResponse response = new DispensacionResponse();
        response.setId(movimiento.getId());
        response.setCantidad(movimiento.getCantidad());
        response.setMotivo(movimiento.getMotivo());
        response.setDocumentoReferencia(movimiento.getDocRef());
        response.setOcurrioEn(movimiento.getOcurrioEn());
        response.setDispensadoPor(movimiento.getCreadoPor() != null ? movimiento.getCreadoPor().longValue() : null);

        // Buscar usuario que creó la dispensación
        if (movimiento.getCreadoPor() != null) {
            userRepository.findById(movimiento.getCreadoPor().longValue()).ifPresent(user -> {
                response.setDispensadoPorNombre(user.getNombreCompleto());
            });
        }

        // Buscar información del lote
        loteRepository.findById(movimiento.getLoteId()).ifPresent(lote -> {
            response.setLoteId(lote.getId());
            response.setCodigoLote(lote.getCodigoProductoProv());

            if (lote.getProducto() != null) {
                response.setProductoId(lote.getProducto().getId());
                response.setProductoNombre(lote.getProducto().getNombre());
            }
        });

        return response;
    }
}
