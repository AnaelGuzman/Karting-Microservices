package com.msreservation.services;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.msreservation.entities.Reserva;
import com.msreservation.models.Tarifa;
import com.msreservation.models.Usuario;
import com.msreservation.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.FileOutputStream;


@Service
public class GeneratorVoucherService {
    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    RestTemplate restTemplate;

    public File generarComprobante(Reserva reserva) throws Exception {
        String fileName = "comprobante_" + reserva.getId() + ".pdf";
        File pdfFile = new File("comprobantes/" + fileName);
        pdfFile.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        Usuario[] usuarios = restTemplate.getForObject(
                "http://msuser/api/v1/usuarios/reserva/" + reserva.getId(),
                Usuario[].class);
        Tarifa tarifa = restTemplate.getForObject("http://msfee/api/v1/fees/"+reserva.getIdFee(),Tarifa.class);
        // Encabezado
        doc.add(new Paragraph("Comprobante de Reserva")
                .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph(" "));

        // Información de reserva
        doc.add(new Paragraph("Datos de la Reserva:").setBold());
        doc.add(new Paragraph("Código: " + reserva.getId()));
        doc.add(new Paragraph("Fecha: " + reserva.getFechaReserva()));
        doc.add(new Paragraph("Hora Inicio: " + reserva.getHoraInicio()));
        doc.add(new Paragraph("Hora Fin: " + reserva.getHoraFin()));
        doc.add(new Paragraph("Tarifa General: " + tarifa.getPrecio()));
        doc.add(new Paragraph("Participantes: " + (usuarios != null ? usuarios.length : 0)));
        doc.add(new Paragraph(" "));

        if (usuarios != null && usuarios.length > 0) {
            // Crear tabla con 4 columnas (coincide con los encabezados)
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 3, 2,2,2,2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Añadir encabezados
            addHeaderCell(table, "Nombre");
            addHeaderCell(table, "Apellido");
            addHeaderCell(table, "Tarifa Base");
            addHeaderCell(table, "Descuento Aplicado");
            addHeaderCell(table, "Descuento");
            addHeaderCell(table, "Tarifa Con Descuento");
            addHeaderCell(table, "Iva");

            // Añadir datos de usuarios
            for (Usuario usuario : usuarios) {
                Double decuento = tarifa.getPrecio()-usuario.getTarifaPersonal();
                Double ivapersonaño = usuario.getTarifaPersonal()*0.19;
                addDataCell(table, usuario.getNombre());
                addDataCell(table, usuario.getApellido());
                addDataCell(table, reserva.getDecripcionFee());
                addDataCell(table, usuario.getNombreDescuento() != null ? usuario.getNombreDescuento() : "N/A");
                addDataCell(table, decuento.toString());
                addDataCell(table, usuario.getTarifaPersonal().toString());
                addDataCell(table, ivapersonaño.toString());
            }

            doc.add(table);
            doc.add(new Paragraph(" "));

            // Tabla de totales
            Table totalesTable = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2}));
            totalesTable.setWidth(UnitValue.createPercentValue(50));
            totalesTable.setHorizontalAlignment(HorizontalAlignment.RIGHT);

            Double totalSinIva = reserva.getPrecioTotal() / 1.19;
            Double iva = reserva.getPrecioTotal() * 0.19;

            addHeaderCell(totalesTable, "Total con Descuento:");
            addHeaderCell(totalesTable, "Impuesto (19%):");
            addHeaderCell(totalesTable, "Total Final:");

            addDataCell(totalesTable, "$" + String.format("%.2f", totalSinIva));

            addDataCell(totalesTable, "$" + String.format("%.2f", iva));

            addDataCell(totalesTable, "$" + String.format("%.2f", reserva.getPrecioTotal())).setBold();

            doc.add(totalesTable);
            doc.add(new Paragraph(" "));

            // Reservado por
            doc.add(new Paragraph("Reservado por: " + usuarios[0].getNombre() + " " +
                    usuarios[0].getApellido()).setItalic());
        } else {
            doc.add(new Paragraph("No hay participantes registrados para esta reserva").setItalic());
        }

        doc.close();
        return pdfFile;
    }

    // Método para celdas de encabezado (sin cambios)
    private void addHeaderCell(Table table, String text) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text).setBold());
        cell.setBackgroundColor(new DeviceRgb(63, 81, 181)); // Azul
        cell.setFontColor(new DeviceRgb(255, 255, 255)); // Blanco
        table.addHeaderCell(cell);
    }

    // Método para celdas de datos normales (mejorado)
    private Cell addDataCell(Table table, String text) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text != null ? text : ""));
        cell.setPadding(5); // Espaciado interno
        table.addCell(cell);
        return cell; // Permitir encadenamiento
    }
}