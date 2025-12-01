package com.medic.inventory.controller;

import com.medic.inventory.dto.*;
import com.medic.inventory.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/general")
    public ResponseEntity<ReporteResponse> obtenerReporteGeneral() {
        ReporteResponse reporte = reporteService.obtenerReporteGeneral();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDTO>> obtenerProductosMasVendidos() {
        List<ProductoMasVendidoDTO> productos = reporteService.obtenerProductosMasVendidos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categorias-mas-vendidas")
    public ResponseEntity<List<CategoriaMasVendidaDTO>> obtenerCategoriasMasVendidas() {
        List<CategoriaMasVendidaDTO> categorias = reporteService.obtenerCategoriasMasVendidas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/grafico-mensual")
    public ResponseEntity<List<DatoGraficoMensualDTO>> obtenerDatosGraficoMensual() {
        List<DatoGraficoMensualDTO> datos = reporteService.obtenerDatosGraficoMensual();
        return ResponseEntity.ok(datos);
    }

    @GetMapping("/metricas-dashboard")
    public ResponseEntity<MetricasDashboardDTO> obtenerMetricasDashboard() {
        MetricasDashboardDTO metricas = reporteService.obtenerMetricasDashboard();
        return ResponseEntity.ok(metricas);
    }
}
