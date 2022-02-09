package com.minimarket.demo.util;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfWriter;
import com.minimarket.demo.model.Cliente;
import com.minimarket.demo.model.TipoCDP;
import com.minimarket.demo.model.Venta;
import com.minimarket.demo.model.DetalleVenta;
import com.minimarket.demo.model.PuntoVenta;

@Component("Ventas/VerVenta")
public class CdpPDF extends AbstractPdfView{
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Venta venta = (Venta) model.get("venta");
        PuntoVenta puntoVenta =venta.obtenerPuntoVenta();
        //TipoCDP tipoCDP = (TipoCDP) model.get("tipoCDP");
        Cliente cliente = (Cliente) model.get("cliente");
        List<DetalleVenta> detalles =  (List<DetalleVenta>) model.get("detalles");


        

        //INICIALIZADO
        document.setPageSize(PageSize.PENGUIN_SMALL_PAPERBACK);
        document.setMargins(-25, -25, 5, 15);
        document.open();

        //FUENTES
        Font fuenteTitulo = FontFactory.getFont("Courier",15,Font.BOLD,Color.BLACK);
        Font fuenteSubTitulo = FontFactory.getFont("Courier",11,Font.BOLD,Color.BLACK);
        Font fuenteSubTituloCursiva = FontFactory.getFont("Courier",11,Font.BOLDITALIC,Color.BLACK);
        Font fuenteCostosNegrita = FontFactory.getFont("Courier",10,Font.BOLD,Color.BLACK);
        Font fuenteCostos = FontFactory.getFont("Courier",10,Color.BLACK);
        //Font fuenteSeparador = FontFactory.getFont("Courier",11,Color.BLACK);
        Font fuenteContenidoTabla = FontFactory.getFont("Courier",8,Color.BLACK);
        Font fuenteContenidoTablaNegrita = FontFactory.getFont("Courier",8,Font.BOLD,Color.BLACK);
        

        //CELDAS
        PdfPCell celdaContenidoTabla = new PdfPCell();
        celdaContenidoTabla.setBorder(0);
        celdaContenidoTabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaContenidoTabla.setVerticalAlignment(Element.ALIGN_CENTER);


        

        //Titulo
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;
        celda=new PdfPCell(new Phrase(venta.descripcionCDP(),fuenteTitulo));
        celda.setBorder(0);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaTitulo.addCell(celda);

        //Descripcion
        celda.setPhrase(new Phrase(puntoVenta.direccion,fuenteContenidoTabla));
        tablaTitulo.addCell(celda);
        celda.setPhrase(new Phrase(puntoVenta.nombre,fuenteContenidoTabla));
        tablaTitulo.addCell(celda);
        document.add(tablaTitulo);
        //tablaTitulo.setSpacingAfter(10);

        //Separador
        PdfPTable tablaSeparador = new PdfPTable(1);
        PdfPCell celdaSeparador = new PdfPCell(new Phrase("-------------------------------------------",fuenteSubTitulo));
        celdaSeparador.setBorder(0);
        celdaSeparador.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaSeparador.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaSeparador.addCell(celdaSeparador);
        document.add(tablaSeparador);

        //TextoNormal
        PdfPTable tablaTextoNormal = new PdfPTable(2);
        PdfPCell celdaTextoNormal = new PdfPCell(new Phrase("Nº DE Venta: ",fuenteContenidoTablaNegrita));
        celdaTextoNormal.setBorder(0);
        celdaTextoNormal.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase(venta.codVenta+"",fuenteContenidoTabla));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase("FECHA DE VENTA: ",fuenteContenidoTablaNegrita));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase(venta.fechaHora.format(dtf),fuenteContenidoTabla));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase("FECHA DE IMPRESION: ",fuenteContenidoTablaNegrita));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase(LocalDateTime.now().format(dtf),fuenteContenidoTabla));
        tablaTextoNormal.addCell(celdaTextoNormal);
        document.add(tablaTextoNormal);

        
        //Separador
        tablaSeparador = new PdfPTable(1);
        celdaSeparador.setPhrase(new Phrase("----------------- CLIENTE -----------------",fuenteSubTitulo));
        tablaSeparador.addCell(celdaSeparador);
        document.add(tablaSeparador);

        //TextoNormal
        tablaTextoNormal = new PdfPTable(2);
        if(cliente.esNatural()){
            celdaTextoNormal.setPhrase(new Phrase("DNI: ",fuenteContenidoTablaNegrita));
            tablaTextoNormal.addCell(celdaTextoNormal);
            celdaTextoNormal.setPhrase(new Phrase(cliente.dni,fuenteContenidoTabla));
            tablaTextoNormal.addCell(celdaTextoNormal);
            celdaTextoNormal.setPhrase(new Phrase("CLIENTE: ",fuenteContenidoTablaNegrita));
            tablaTextoNormal.addCell(celdaTextoNormal);
            celdaTextoNormal.setPhrase(new Phrase(cliente.gNombreCompleto(),fuenteContenidoTabla));
            tablaTextoNormal.addCell(celdaTextoNormal);
        }else{
            celdaTextoNormal.setPhrase(new Phrase("RUC: ",fuenteContenidoTablaNegrita));
            tablaTextoNormal.addCell(celdaTextoNormal);
            celdaTextoNormal.setPhrase(new Phrase(cliente.ruc,fuenteContenidoTabla));
            tablaTextoNormal.addCell(celdaTextoNormal);
            celdaTextoNormal.setPhrase(new Phrase("CLIENTE: ",fuenteContenidoTablaNegrita));
            tablaTextoNormal.addCell(celdaTextoNormal);
            celdaTextoNormal.setPhrase(new Phrase(cliente.razonSocial,fuenteContenidoTabla));
            tablaTextoNormal.addCell(celdaTextoNormal);
        }
        document.add(tablaTextoNormal);

        //Separador
        tablaSeparador = new PdfPTable(1);
        celdaSeparador.setPhrase(new Phrase("---------------- PRODUCTOS ----------------",fuenteSubTitulo));
        tablaSeparador.addCell(celdaSeparador);
        document.add(tablaSeparador);
        
        //Tabla de detalles
        PdfPTable tablaDetalles = new PdfPTable(5);
        celdaContenidoTabla.setPhrase(new Phrase("ÍTEM",fuenteSubTitulo));
        tablaDetalles.addCell(celdaContenidoTabla);
        celdaContenidoTabla.setPhrase(new Phrase("PRODUCTO",fuenteSubTitulo));
        tablaDetalles.addCell(celdaContenidoTabla);
        celdaContenidoTabla.setPhrase(new Phrase("PRECIO",fuenteSubTitulo));
        tablaDetalles.addCell(celdaContenidoTabla);
        celdaContenidoTabla.setPhrase(new Phrase("CANTIDAD",fuenteSubTitulo));
        tablaDetalles.addCell(celdaContenidoTabla);
        celdaContenidoTabla.setPhrase(new Phrase("SUBTOTAL",fuenteSubTitulo));
        tablaDetalles.addCell(celdaContenidoTabla);

        int index=1;
        for(DetalleVenta detalle: detalles){
            celdaContenidoTabla.setPhrase(new Phrase(index+"",fuenteContenidoTabla));
            tablaDetalles.addCell(celdaContenidoTabla);
            celdaContenidoTabla.setPhrase(new Phrase(detalle.obtenerProducto().nombre+"",fuenteContenidoTabla));
            tablaDetalles.addCell(celdaContenidoTabla);
            celdaContenidoTabla.setPhrase(new Phrase(detalle.obtenerPrecioUnitarioFormateado()+"",fuenteContenidoTabla));
            tablaDetalles.addCell(celdaContenidoTabla);
            celdaContenidoTabla.setPhrase(new Phrase(detalle.cantidad+"",fuenteContenidoTabla));
            tablaDetalles.addCell(celdaContenidoTabla);
            celdaContenidoTabla.setPhrase(new Phrase(detalle.obtenerTotalFormateado()+"",fuenteContenidoTabla));
            tablaDetalles.addCell(celdaContenidoTabla);
            index++;
        }
        document.add(tablaDetalles);

        //Separador
        tablaSeparador = new PdfPTable(1);
        celdaSeparador.setPhrase(new Phrase("------------------ PAGO ------------------",fuenteSubTitulo));
        tablaSeparador.addCell(celdaSeparador);
        document.add(tablaSeparador);
        

        //TextoNormal
        tablaTextoNormal = new PdfPTable(2);
        celdaTextoNormal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celdaTextoNormal.setPhrase(new Phrase("SUBTOTAL: ",fuenteCostosNegrita));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase(venta.obtenerImporteBrutoFormateado()+"",fuenteCostos));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase("IGV: ",fuenteCostosNegrita));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase(venta.obtenerIgvFormateado()+"",fuenteCostos));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase("TOTAL A PAGAR: ",fuenteCostosNegrita));
        tablaTextoNormal.addCell(celdaTextoNormal);
        celdaTextoNormal.setPhrase(new Phrase(venta.obtenerImporteTotalFormateado()+"",fuenteCostos));
        tablaTextoNormal.addCell(celdaTextoNormal);
        tablaTextoNormal.setSpacingAfter(10);
        document.add(tablaTextoNormal);

        //Separador
        tablaSeparador = new PdfPTable(1);
        celdaSeparador.setPhrase(new Phrase("###########################################",fuenteSubTitulo));
        tablaSeparador.addCell(celdaSeparador);
        tablaSeparador.setSpacingAfter(5);
        document.add(tablaSeparador);

        tablaSeparador = new PdfPTable(1);
        celdaSeparador.setPhrase(new Phrase("GRACIAS POR SU COMPRA",fuenteSubTituloCursiva));
        tablaSeparador.addCell(celdaSeparador);
        document.add(tablaSeparador);
        
	}

}