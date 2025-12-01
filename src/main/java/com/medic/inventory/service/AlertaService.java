package com.medic.inventory.service;

import com.medic.inventory.dto.AlertaResponse;
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
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final InventarioRepository inventarioRepository;
    private final UmbralStockRepository umbralStockRepository;
    private final SedeRepository sedeRepository;
    private final LoteRepository loteRepository;

    @Transactional
    public List<AlertaResponse> verificarStockYGenerarAlertas() {
        List<Alerta> alertasGeneradas = new java.util.ArrayList<>();

        List<UmbralStock> umbrales = umbralStockRepository.findAll();

        for (UmbralStock umbral : umbrales) {
            Long sedeId = umbral.getSede().getId();
            Long productoId = umbral.getProducto().getId();

            List<Inventario> inventarios = inventarioRepository.findBySedeId(sedeId);

            int stockTotal = inventarios.stream()
                .filter(inv -> loteRepository.findById(inv.getLoteId())
                    .map(lote -> lote.getProducto().getId().equals(productoId))
                    .orElse(false))
                .mapToInt(Inventario::getCantidad)
                .sum();

            boolean tieneAlertaActiva = alertaRepository.existsBySedeIdAndProductoIdAndActiva(
                sedeId, productoId, 1
            );

            if (stockTotal <= umbral.getMinimo() && !tieneAlertaActiva) {
                Alerta alerta = new Alerta();
                alerta.setSede(umbral.getSede());
                alerta.setProducto(umbral.getProducto());

                if (stockTotal == 0) {
                    alerta.setTipo("STOCK_AGOTADO");
                    alerta.setNivel("ALTA");
                } else if (stockTotal <= umbral.getMinimo() / 2) {
                    alerta.setTipo("STOCK_CRITICO");
                    alerta.setNivel("ALTA");
                } else {
                    alerta.setTipo("STOCK_BAJO");
                    alerta.setNivel("MEDIA");
                }

                alerta.setDisparadaEn(LocalDateTime.now());
                alerta.setActiva(1);

                alertasGeneradas.add(alertaRepository.save(alerta));
            }

            if (stockTotal > umbral.getMinimo() && tieneAlertaActiva) {
                alertaRepository.findBySedeIdAndProductoIdAndActiva(sedeId, productoId, 1)
                    .forEach(alerta -> {
                        alerta.setActiva(0);
                        alerta.setResueltaEn(LocalDateTime.now());
                        alertaRepository.save(alerta);
                    });
            }
        }

        return alertasGeneradas.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<AlertaResponse> obtenerAlertasActivas() {
        List<Alerta> alertas = alertaRepository.findByActivaOrderByDisparadaEnDesc(1);

        return alertas.stream()
            .filter(alerta -> {
                Long sedeId = alerta.getSede().getId();
                Long productoId = alerta.getProducto().getId();

                List<Inventario> inventarios = inventarioRepository.findBySedeId(sedeId);
                int stockActual = inventarios.stream()
                    .filter(inv -> loteRepository.findById(inv.getLoteId())
                        .map(lote -> lote.getProducto().getId().equals(productoId))
                        .orElse(false))
                    .mapToInt(Inventario::getCantidad)
                    .sum();

                return umbralStockRepository.findBySedeIdAndProductoId(sedeId, productoId)
                    .map(umbral -> {
                        if (stockActual > umbral.getMinimo()) {
                            alerta.setActiva(0);
                            alerta.setResueltaEn(LocalDateTime.now());
                            alertaRepository.save(alerta);
                            return false;
                        }
                        return true;
                    })
                    .orElse(false);
            })
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void resolverAlerta(Long alertaId) {
        Alerta alerta = alertaRepository.findById(alertaId)
            .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        alerta.setActiva(0);
        alerta.setResueltaEn(LocalDateTime.now());
        alertaRepository.save(alerta);
    }

    private AlertaResponse mapToResponse(Alerta alerta) {
        AlertaResponse response = new AlertaResponse();
        response.setId(alerta.getId());

        Long sedeId = alerta.getSede().getId();
        Long productoId = alerta.getProducto().getId();

        response.setSedeId(sedeId);
        response.setProductoId(productoId);
        response.setSedeNombre(alerta.getSede().getNombre());
        response.setProductoNombre(alerta.getProducto().getNombre());
        response.setTipo(alerta.getTipo());
        response.setNivel(alerta.getNivel());
        response.setDisparadaEn(alerta.getDisparadaEn());
        response.setResueltaEn(alerta.getResueltaEn());
        response.setActiva(alerta.getActiva() == 1);

        List<Inventario> inventarios = inventarioRepository.findBySedeId(sedeId);
        int stockActual = inventarios.stream()
            .filter(inv -> loteRepository.findById(inv.getLoteId())
                .map(lote -> lote.getProducto().getId().equals(productoId))
                .orElse(false))
            .mapToInt(Inventario::getCantidad)
            .sum();
        response.setStockActual(stockActual);

        umbralStockRepository.findBySedeIdAndProductoId(sedeId, productoId)
            .ifPresent(umbral -> {
                response.setNivelAlerta(umbral.getMinimo());
                response.setSugerencia("Reabastecer antes de " +
                    LocalDateTime.now().plusDays(7).toLocalDate());
            });

        return response;
    }
}
