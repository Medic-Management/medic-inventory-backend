package com.medic.inventory.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.medic.inventory.dto.AlertaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ComprobanteService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarComprobanteDispensacion(
            Long dispensacionId,
            String productoNombre,
            Integer cantidad,
            String loteNumero,
            String usuarioNombre,
            LocalDateTime fecha) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("COMPROBANTE DE DISPENSACIÓN")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Sistema de Inventario Médico")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            document.add(new Paragraph("Información del Movimiento")
                    .setFontSize(14)
                    .setBold()
                    .setMarginTop(10));

            Table table = new Table(2);
            table.setWidth(500);

            table.addCell("ID Dispensación:");
            table.addCell(String.valueOf(dispensacionId));

            table.addCell("Fecha:");
            table.addCell(fecha.format(DATE_FORMATTER));

            table.addCell("Producto:");
            table.addCell(productoNombre);

            table.addCell("Cantidad:");
            table.addCell(cantidad + " unidades");

            table.addCell("Lote:");
            table.addCell(loteNumero);

            table.addCell("Dispensado por:");
            table.addCell(usuarioNombre);

            document.add(table);

            document.add(new Paragraph("\nDocumento generado automáticamente")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30));

            document.close();

            log.info("Comprobante de dispensación generado: ID {}", dispensacionId);
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Error generando comprobante de dispensación", e);
            throw new RuntimeException("Error al generar comprobante PDF", e);
        }
    }

    public byte[] generarComprobanteEntrada(
            Long entradaId,
            String productoNombre,
            Integer cantidad,
            String proveedorNombre,
            String usuarioNombre,
            LocalDateTime fecha) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("COMPROBANTE DE ENTRADA")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Sistema de Inventario Médico")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            document.add(new Paragraph("Información del Movimiento")
                    .setFontSize(14)
                    .setBold()
                    .setMarginTop(10));

            Table table = new Table(2);
            table.setWidth(500);

            table.addCell("ID Entrada:");
            table.addCell(String.valueOf(entradaId));

            table.addCell("Fecha:");
            table.addCell(fecha.format(DATE_FORMATTER));

            table.addCell("Producto:");
            table.addCell(productoNombre);

            table.addCell("Cantidad:");
            table.addCell(cantidad + " unidades");

            table.addCell("Proveedor:");
            table.addCell(proveedorNombre);

            table.addCell("Registrado por:");
            table.addCell(usuarioNombre);

            document.add(table);

            document.add(new Paragraph("\nDocumento generado automáticamente")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30));

            document.close();

            log.info("Comprobante de entrada generado: ID {}", entradaId);
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Error generando comprobante de entrada", e);
            throw new RuntimeException("Error al generar comprobante PDF", e);
        }
    }

    /**
     * HU-03 Escenario 2: Genera reporte consolidado de alertas en PDF
     */
    @SuppressWarnings("unchecked")
    public byte[] generarReporteAlertas(Map<String, Object> reporteData) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título
            document.add(new Paragraph("REPORTE CONSOLIDADO DE ALERTAS")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Sistema de Inventario Médico")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // Período del reporte
            LocalDateTime desde = (LocalDateTime) reporteData.get("periodoDesde");
            LocalDateTime hasta = (LocalDateTime) reporteData.get("periodoHasta");
            document.add(new Paragraph("Período: " + desde.format(DATE_FORMATTER) + " - " + hasta.format(DATE_FORMATTER))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10));

            // KPIs
            document.add(new Paragraph("Indicadores Clave")
                    .setFontSize(14)
                    .setBold()
                    .setMarginTop(10));

            Table kpiTable = new Table(2);
            kpiTable.setWidth(500);

            kpiTable.addCell("Total de Alertas:");
            kpiTable.addCell(String.valueOf(reporteData.get("totalAlertas")));

            kpiTable.addCell("Alertas Activas:");
            kpiTable.addCell(String.valueOf(reporteData.get("alertasActivas")));

            kpiTable.addCell("Alertas Resueltas:");
            kpiTable.addCell(String.valueOf(reporteData.get("alertasResueltas")));

            kpiTable.addCell("Nivel ALTA:");
            kpiTable.addCell(String.valueOf(reporteData.get("alertasAltas")));

            kpiTable.addCell("Nivel MEDIA:");
            kpiTable.addCell(String.valueOf(reporteData.get("alertasMedias")));

            kpiTable.addCell("Nivel BAJA:");
            kpiTable.addCell(String.valueOf(reporteData.get("alertasBajas")));

            document.add(kpiTable);

            // Top 10 Críticos
            document.add(new Paragraph("\nTop 10 Medicamentos Críticos")
                    .setFontSize(14)
                    .setBold()
                    .setMarginTop(20));

            List<AlertaResponse> top10 = (List<AlertaResponse>) reporteData.get("top10Criticos");

            if (top10 != null && !top10.isEmpty()) {
                Table alertTable = new Table(new float[]{1, 3, 2, 2, 2});
                alertTable.setWidth(500);

                alertTable.addHeaderCell("ID");
                alertTable.addHeaderCell("Producto");
                alertTable.addHeaderCell("Sede");
                alertTable.addHeaderCell("Nivel");
                alertTable.addHeaderCell("Stock Actual");

                for (AlertaResponse alerta : top10) {
                    alertTable.addCell(String.valueOf(alerta.getId()));
                    alertTable.addCell(alerta.getProductoNombre());
                    alertTable.addCell(alerta.getSedeNombre());
                    alertTable.addCell(alerta.getNivel());
                    alertTable.addCell(String.valueOf(alerta.getStockActual()));
                }

                document.add(alertTable);
            } else {
                document.add(new Paragraph("No hay alertas críticas activas")
                        .setFontSize(10)
                        .setItalic());
            }

            // Pie de página
            document.add(new Paragraph("\nDocumento generado automáticamente el " +
                    LocalDateTime.now().format(DATE_FORMATTER))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30));

            document.close();

            log.info("Reporte consolidado de alertas generado exitosamente");
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Error generando reporte de alertas", e);
            throw new RuntimeException("Error al generar reporte de alertas PDF", e);
        }
    }
}
