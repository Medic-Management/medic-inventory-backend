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
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntradaService {

    private final MovimientoRepository movimientoRepository;
    private final InventarioRepository inventarioRepository;
    private final ProductRepository productRepository;
    private final LoteRepository loteRepository;
    private final UserRepository userRepository;

    // HU-01: Umbral configurable para alertas de vencimiento (90 días por defecto)
    private static final int UMBRAL_VENCIMIENTO_DIAS = 90;

    @Transactional
    public EntradaResponse registrarEntrada(EntradaRequest request, Long userId) {
        Product producto = productRepository.findById(request.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LocalDate fechaVencimiento = LocalDate.parse(request.getFechaVencimiento());

        // HU-01 Escenario 2: Validar fecha de vencimiento próxima
        validarFechaVencimiento(fechaVencimiento, request.getConfirmarVencimientoCercano());

        // HU-01 Escenario 3: Verificar duplicidad de lote
        Optional<Lote> loteExistente = loteRepository.findByProductoIdAndCodigoProductoProv(
            request.getProductoId(),
            request.getCodigoLote()
        );

        if (loteExistente.isPresent()) {
            Lote lote = loteExistente.get();
            // Verificar si es exactamente el mismo lote (mismo código + fecha + producto)
            if (lote.getFechaVencimiento() != null &&
                lote.getFechaVencimiento().equals(fechaVencimiento)) {
                log.warn("Intento de registro de lote duplicado. UserID: {}, Lote: {}, Producto: {}",
                    userId, request.getCodigoLote(), producto.getNombre());
                throw new RuntimeException("LOTE_DUPLICADO: Lote duplicado – registro no permitido. " +
                    "Ya existe un lote con código '" + request.getCodigoLote() +
                    "' y fecha de vencimiento " + fechaVencimiento);
            }
        }

        // Si no es duplicado, crear o reutilizar lote
        Lote lote = loteExistente.orElseGet(() -> {
            Lote nuevoLote = new Lote();
            nuevoLote.setProducto(producto);
            nuevoLote.setCodigoProductoProv(request.getCodigoLote());
            nuevoLote.setFechaVencimiento(fechaVencimiento);
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

        // HU-04 Escenario 3: Validar stock máximo antes de registrar entrada
        Optional<UmbralStock> umbralOpt = umbralStockRepository.findBySedeIdAndProductoId(1L, producto.getId());
        if (umbralOpt.isPresent()) {
            UmbralStock umbral = umbralOpt.get();
            Integer stockMaximo = umbral.getStockMaximo();

            if (stockMaximo != null && stockMaximo > 0) {
                int stockFuturo = stockAnterior + request.getCantidad();

                if (stockFuturo > stockMaximo) {
                    log.warn("Intento de entrada que excede stock máximo. Producto: {}, Stock actual: {}, Cantidad entrada: {}, Stock máximo: {}",
                        producto.getNombre(), stockAnterior, request.getCantidad(), stockMaximo);
                    throw new RuntimeException(String.format(
                        "STOCK_MAXIMO_EXCEDIDO: La entrada de %d unidades excedería el stock máximo configurado de %d unidades. " +
                        "Stock actual: %d. Cantidad máxima permitida: %d unidades.",
                        request.getCantidad(), stockMaximo, stockAnterior, (stockMaximo - stockAnterior)
                    ));
                }
            }
        }

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

    /**
     * HU-01: Obtener entrada por ID para generación de PDF
     */
    @Transactional(readOnly = true)
    public EntradaResponse obtenerEntradaPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrada no encontrada"));

        if (!"ENTRADA".equals(movimiento.getTipo())) {
            throw new RuntimeException("El movimiento especificado no es una entrada");
        }

        EntradaResponse response = new EntradaResponse();
        response.setId(movimiento.getId());
        response.setCantidad(movimiento.getCantidad());
        response.setDocumentoReferencia(movimiento.getDocRef());
        response.setOcurrioEn(movimiento.getOcurrioEn());
        response.setRegistradoPor(movimiento.getCreadoPor() != null ? movimiento.getCreadoPor().longValue() : null);

        // Buscar usuario que creó la entrada
        if (movimiento.getCreadoPor() != null) {
            userRepository.findById(movimiento.getCreadoPor().longValue()).ifPresent(user -> {
                response.setRegistradoPorNombre(user.getNombreCompleto());
            });
        }

        // Buscar información del lote y producto
        loteRepository.findById(movimiento.getLoteId()).ifPresent(lote -> {
            response.setLoteId(lote.getId());
            response.setCodigoLote(lote.getCodigoProductoProv());

            if (lote.getProducto() != null) {
                response.setProductoId(lote.getProducto().getId());
                response.setProductoNombre(lote.getProducto().getNombre());
            }
        });

        // Proveedor: Por ahora "Sin especificar" ya que no se guarda en movimiento
        response.setProveedorNombre("Sin especificar");

        return response;
    }

    /**
     * HU-01 Escenario 2: Validar fecha de vencimiento próxima (< 90 días)
     * Si el vencimiento está dentro del umbral y no hay confirmación, lanza excepción
     */
    private void validarFechaVencimiento(LocalDate fechaVencimiento, Boolean confirmarVencimientoCercano) {
        LocalDate hoy = LocalDate.now();

        // Validar que no sea una fecha pasada
        if (fechaVencimiento.isBefore(hoy)) {
            throw new RuntimeException("La fecha de vencimiento no puede ser anterior a la fecha actual");
        }

        // Calcular días hasta vencimiento
        long diasHastaVencimiento = ChronoUnit.DAYS.between(hoy, fechaVencimiento);

        // Si está dentro del umbral (90 días) y no hay confirmación, lanzar advertencia
        if (diasHastaVencimiento <= UMBRAL_VENCIMIENTO_DIAS) {
            if (confirmarVencimientoCercano == null || !confirmarVencimientoCercano) {
                log.warn("Intento de registro de lote próximo a vencer sin confirmación. " +
                    "Días hasta vencimiento: {}, Umbral: {}", diasHastaVencimiento, UMBRAL_VENCIMIENTO_DIAS);
                throw new RuntimeException("VENCIMIENTO_CERCANO: Medicamento próximo a vencer (" +
                    diasHastaVencimiento + " días). ¿Confirmar registro?");
            } else {
                log.info("Lote próximo a vencer registrado con confirmación. Días hasta vencimiento: {}",
                    diasHastaVencimiento);
            }
        }
    }
}
