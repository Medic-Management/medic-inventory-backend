package com.medic.inventory.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
