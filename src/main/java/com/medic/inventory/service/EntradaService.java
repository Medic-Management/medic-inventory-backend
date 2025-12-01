package com.medic.inventory.service;

import com.medic.inventory.dto.EntradaRequest;
import com.medic.inventory.dto.EntradaResponse;
import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntradaService {

    private final MovimientoRepository movimientoRepository;
    private final InventarioRepository inventarioRepository;
    private final ProductRepository productRepository;
    private final LoteRepository loteRepository;
    private final UserRepository userRepository;

    @Transactional
    public EntradaResponse registrarEntrada(EntradaRequest request, Long userId) {
        Product producto = productRepository.findById(request.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Lote lote = loteRepository.findByProductoIdAndCodigoProductoProv(
            request.getProductoId(),
            request.getCodigoLote()
        ).orElseGet(() -> {
            Lote nuevoLote = new Lote();
            nuevoLote.setProducto(producto);
            nuevoLote.setCodigoProductoProv(request.getCodigoLote());
            nuevoLote.setFechaVencimiento(LocalDate.parse(request.getFechaVencimiento()));
            nuevoLote.setEstado(true);
            return loteRepository.save(nuevoLote);
        });

        Inventario inventario = inventarioRepository.findBySedeIdAndLoteId(1L, lote.getId())
            .orElseGet(() -> {
                Inventario nuevoInventario = new Inventario();
                nuevoInventario.setSedeId(1L);
                nuevoInventario.setLoteId(lote.getId());
                nuevoInventario.setCantidad(0);
                return nuevoInventario;
            });

        int stockAnterior = inventario.getCantidad();

        Movimiento movimiento = new Movimiento();
        movimiento.setOcurrioEn(LocalDateTime.now());
        movimiento.setSedeId(1L);
        movimiento.setLoteId(lote.getId());
        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getObservaciones() != null ?
            request.getObservaciones() : "Entrada de mercancía");
        movimiento.setDocRef(request.getDocumentoReferencia());
        movimiento.setCreadoPor(userId.intValue());

        movimiento = movimientoRepository.save(movimiento);

        inventario.setCantidad(inventario.getCantidad() + request.getCantidad());
        inventarioRepository.save(inventario);

        EntradaResponse response = new EntradaResponse();
        response.setId(movimiento.getId());
        response.setProductoId(producto.getId());
        response.setProductoNombre(producto.getNombre());
        response.setLoteId(lote.getId());
        response.setCodigoLote(lote.getCodigoProductoProv());
        response.setCantidad(request.getCantidad());
        response.setDocumentoReferencia(movimiento.getDocRef());
        response.setOcurrioEn(movimiento.getOcurrioEn());
        response.setRegistradoPor(userId);
        response.setRegistradoPorNombre(usuario.getNombreCompleto());
        response.setStockAnterior(stockAnterior);
        response.setStockNuevo(inventario.getCantidad());

        return response;
    }
}
