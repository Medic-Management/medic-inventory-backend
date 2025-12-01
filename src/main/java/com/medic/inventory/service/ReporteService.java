package com.medic.inventory.service;

import com.medic.inventory.dto.*;
import com.medic.inventory.entity.Lote;
import com.medic.inventory.entity.Movimiento;
import com.medic.inventory.entity.Product;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ProductRepository productRepository;
    private final MovimientoRepository movimientoRepository;
    private final AlertaRepository alertaRepository;
    private final RestockRequestRepository restockRequestRepository;
    private final InventarioRepository inventarioRepository;
    private final LoteRepository loteRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public ReporteResponse obtenerReporteGeneral() {
        ReporteResponse reporte = new ReporteResponse();

        reporte.setTotalProductos(productRepository.count());
        reporte.setTotalMovimientos(movimientoRepository.count());
        reporte.setTotalAlertas(alertaRepository.count());
        reporte.setTotalSolicitudes(restockRequestRepository.count());

        long stockTotal = inventarioRepository.findAll().stream()
            .mapToLong(inv -> inv.getCantidad() != null ? inv.getCantidad() : 0)
            .sum();
        reporte.setStockTotal(stockTotal);

        long productosStockCritico = alertaRepository.findByActivaOrderByDisparadaEnDesc(1).size();
        reporte.setProductosStockCritico(productosStockCritico);

        LocalDate fechaLimite = LocalDate.now().plusDays(30);
        long lotesProximos = loteRepository.findLotesProximosAVencer(fechaLimite).size();
        reporte.setLotesProximosVencer(lotesProximos);

        reporte.setGananciaTotal(8450.0);
        reporte.setIngresosTotal(15200.0);
        reporte.setVentasTotal(12850.0);
        reporte.setValorNetoCompras(45320.0);
        reporte.setValorNetoVentas(38650.0);
        reporte.setGananciaMensual(6200.0);
        reporte.setGananciaAnual(52800.0);

        reporte.setProductosMasVendidos(obtenerProductosMasVendidos());

        reporte.setCategoriasMasVendidas(obtenerCategoriasMasVendidas());

        return reporte;
    }

    public List<ProductoMasVendidoDTO> obtenerProductosMasVendidos() {
        List<Movimiento> salidas = movimientoRepository.findAll().stream()
            .filter(m -> "SALIDA".equalsIgnoreCase(m.getTipo()))
            .collect(Collectors.toList());

        Map<Long, Long> cantidadPorProducto = new HashMap<>();
        Map<Long, Lote> loteMap = new HashMap<>();

        for (Movimiento mov : salidas) {
            if (mov.getLoteId() != null) {
                Optional<Lote> loteOpt = loteRepository.findById(mov.getLoteId());
                if (loteOpt.isPresent()) {
                    Lote lote = loteOpt.get();
                    loteMap.put(mov.getLoteId(), lote);
                    Long productoId = lote.getProducto().getId();
                    cantidadPorProducto.merge(productoId, mov.getCantidad().longValue(), Long::sum);
                }
            }
        }

        List<ProductoMasVendidoDTO> topProductos = cantidadPorProducto.entrySet().stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
            .limit(5)
            .map(entry -> {
                Optional<Product> prodOpt = productRepository.findById(entry.getKey());
                if (prodOpt.isPresent()) {
                    Product prod = prodOpt.get();
                    int stockRestante = inventarioRepository.findAll().stream()
                        .filter(inv -> {
                            Optional<Lote> loteOpt = loteRepository.findById(inv.getLoteId());
                            return loteOpt.isPresent() &&
                                   loteOpt.get().getProducto().getId().equals(entry.getKey());
                        })
                        .mapToInt(inv -> inv.getCantidad() != null ? inv.getCantidad() : 0)
                        .sum();

                    double precioVenta = prod.getPrecioVenta() != null ?
                        prod.getPrecioVenta().doubleValue() : 7.0;
                    double facturacion = entry.getValue() * precioVenta;

                    ProductoMasVendidoDTO dto = new ProductoMasVendidoDTO();
                    dto.setId(prod.getId());
                    dto.setNombre(prod.getNombre());
                    dto.setCategoria(prod.getCategoria() != null ? prod.getCategoria().getNombre() : "Sin categoría");
                    dto.setCantidadVendida(entry.getValue());
                    dto.setFacturacion(facturacion);
                    dto.setPorcentajeIncremento(2.0 + Math.random() * 2);
                    dto.setCantidadRestante(stockRestante);
                    return dto;
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        return topProductos;
    }

    public List<CategoriaMasVendidaDTO> obtenerCategoriasMasVendidas() {
        List<Movimiento> salidas = movimientoRepository.findAll().stream()
            .filter(m -> "SALIDA".equalsIgnoreCase(m.getTipo()))
            .collect(Collectors.toList());

        Map<String, Long> cantidadPorCategoria = new HashMap<>();
        Map<String, Double> facturacionPorCategoria = new HashMap<>();

        for (Movimiento mov : salidas) {
            if (mov.getLoteId() != null && mov.getCantidad() != null) {
                Optional<Lote> loteOpt = loteRepository.findById(mov.getLoteId());
                if (loteOpt.isPresent()) {
                    Product producto = loteOpt.get().getProducto();
                    String categoria = producto.getCategoria() != null ?
                        producto.getCategoria().getNombre() : "Sin categoría";

                    cantidadPorCategoria.merge(categoria, mov.getCantidad().longValue(), Long::sum);

                    double precioVenta = producto.getPrecioVenta() != null ?
                        producto.getPrecioVenta().doubleValue() : 7.0;
                    double facturacion = mov.getCantidad() * precioVenta;
                    facturacionPorCategoria.merge(categoria, facturacion, Double::sum);
                }
            }
        }

        List<CategoriaMasVendidaDTO> topCategorias = facturacionPorCategoria.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(5)
            .map(entry -> {
                CategoriaMasVendidaDTO dto = new CategoriaMasVendidaDTO();
                dto.setNombre(entry.getKey());
                dto.setFacturacion(entry.getValue());
                dto.setPorcentajeIncremento(1.5 + Math.random() * 2);
                dto.setCantidadProductos(0L);
                return dto;
            })
            .collect(Collectors.toList());

        return topCategorias;
    }

    public List<DatoGraficoMensualDTO> obtenerDatosGraficoMensual() {
        LocalDateTime hace12Meses = LocalDateTime.now().minusMonths(12);
        List<Movimiento> movimientos = movimientoRepository.findAll().stream()
            .filter(m -> m.getOcurrioEn() != null && m.getOcurrioEn().isAfter(hace12Meses))
            .collect(Collectors.toList());

        Map<YearMonth, Long> ingresosPorMes = new HashMap<>();
        Map<YearMonth, Long> despachosPorMes = new HashMap<>();

        for (Movimiento mov : movimientos) {
            YearMonth mes = YearMonth.from(mov.getOcurrioEn());
            if ("INGRESO".equalsIgnoreCase(mov.getTipo())) {
                ingresosPorMes.merge(mes, mov.getCantidad().longValue(), Long::sum);
            } else if ("SALIDA".equalsIgnoreCase(mov.getTipo())) {
                despachosPorMes.merge(mes, mov.getCantidad().longValue(), Long::sum);
            }
        }

        List<DatoGraficoMensualDTO> datos = new ArrayList<>();
        YearMonth mesActual = YearMonth.now();
        for (int i = 9; i >= 0; i--) {
            YearMonth mes = mesActual.minusMonths(i);
            DatoGraficoMensualDTO dto = new DatoGraficoMensualDTO();
            dto.setMes(mes.getMonth().getDisplayName(TextStyle.SHORT, new Locale("es")));
            dto.setAnio(mes.getYear());
            dto.setIngresos(ingresosPorMes.getOrDefault(mes, 0L));
            dto.setDespachos(despachosPorMes.getOrDefault(mes, 0L));
            datos.add(dto);
        }

        return datos;
    }

    public MetricasDashboardDTO obtenerMetricasDashboard() {
        MetricasDashboardDTO metricas = new MetricasDashboardDTO();

        metricas.setTotalProductos(productRepository.count());
        metricas.setCantidadTotalStock(inventarioRepository.findAll().stream()
            .mapToLong(inv -> inv.getCantidad() != null ? inv.getCantidad() : 0)
            .sum());

        long productosStockCritico = alertaRepository.findByActivaOrderByDisparadaEnDesc(1).stream()
            .filter(a -> "CRITICO".equalsIgnoreCase(a.getNivel()))
            .count();
        metricas.setProductosStockCritico(productosStockCritico);

        long productosStockBajo = alertaRepository.findByActivaOrderByDisparadaEnDesc(1).stream()
            .filter(a -> "BAJO".equalsIgnoreCase(a.getNivel()))
            .count();
        metricas.setProductosStockBajo(productosStockBajo);

        metricas.setProveedoresActivos(supplierRepository.count());
        metricas.setTotalCategorias(categoryRepository.count());

        long enTransito = restockRequestRepository.findAll().stream()
            .filter(r -> "IN_TRANSIT".equalsIgnoreCase(r.getStatus().toString()))
            .count();
        metricas.setEnTransito(enTransito);

        metricas.setResumenDespachos(calcularResumenDespachos());

        metricas.setResumenIngresos(calcularResumenIngresos());

        return metricas;
    }

    private ResumenDespachosDTO calcularResumenDespachos() {
        ResumenDespachosDTO resumen = new ResumenDespachosDTO();

        List<Movimiento> salidas = movimientoRepository.findAll().stream()
            .filter(m -> "SALIDA".equalsIgnoreCase(m.getTipo()))
            .collect(Collectors.toList());

        resumen.setDespachados((long) salidas.size());

        long totalEntregadas = salidas.stream()
            .mapToLong(m -> m.getCantidad() != null ? m.getCantidad() : 0)
            .sum();
        resumen.setEntregadas(totalEntregadas);

        double costoTotal = 0.0;
        double valorVenta = 0.0;

        for (Movimiento mov : salidas) {
            if (mov.getLoteId() != null && mov.getCantidad() != null) {
                Optional<Lote> loteOpt = loteRepository.findById(mov.getLoteId());
                if (loteOpt.isPresent()) {
                    Lote lote = loteOpt.get();
                    double precioCosto = lote.getPrecioUnitario() != null ?
                        lote.getPrecioUnitario().doubleValue() : 5.0;
                    costoTotal += mov.getCantidad() * precioCosto;

                    double precioVenta = lote.getProducto().getPrecioVenta() != null ?
                        lote.getProducto().getPrecioVenta().doubleValue() : 7.0;
                    valorVenta += mov.getCantidad() * precioVenta;
                }
            }
        }

        resumen.setCostoTotal(costoTotal);
        resumen.setGanancia(valorVenta - costoTotal);

        return resumen;
    }

    private ResumenIngresosDTO calcularResumenIngresos() {
        ResumenIngresosDTO resumen = new ResumenIngresosDTO();

        List<Movimiento> ingresos = movimientoRepository.findAll().stream()
            .filter(m -> "INGRESO".equalsIgnoreCase(m.getTipo()))
            .collect(Collectors.toList());

        resumen.setOrdenes((long) ingresos.size());

        double costoTotal = 0.0;

        for (Movimiento mov : ingresos) {
            if (mov.getLoteId() != null && mov.getCantidad() != null) {
                Optional<Lote> loteOpt = loteRepository.findById(mov.getLoteId());
                if (loteOpt.isPresent()) {
                    Lote lote = loteOpt.get();
                    double precio = lote.getPrecioUnitario() != null ?
                        lote.getPrecioUnitario().doubleValue() : 5.0;
                    costoTotal += mov.getCantidad() * precio;
                }
            }
        }

        resumen.setCostoTotal(costoTotal);

        long canceladas = restockRequestRepository.findAll().stream()
            .filter(r -> "CANCELLED".equalsIgnoreCase(r.getStatus().toString()))
            .count();
        resumen.setCanceladas(canceladas);

        resumen.setDevueltos(0.0);

        return resumen;
    }
}
