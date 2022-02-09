package com.minimarket.demo.util;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.Color;
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

@Component("Ventas/VerVenta")
public class CdpPDF extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Venta venta = (Venta) model.get("venta");
        TipoCDP tipoCDP = (TipoCDP) model.get("tipoCDP");
        Cliente cliente = (Cliente) model.get("cliente");
        List<DetalleVenta> detalles =  (List<DetalleVenta>) model.get("detalles");


        

        //INICIALIZADO
        document.setPageSize(PageSize.PENGUIN_SMALL_PAPERBACK);
        document.setMargins(-25, -25, 5, 15);
        document.open();

        //FUENTES
        Font fuenteTitulo = FontFactory.getFont("Courier",15,Font.BOLD,Color.BLACK);
        //fuenteTitulo.setStyle(Font.BOLD);

        Font fuenteContenidoTabla = FontFactory.getFont("Courier",8,Color.BLACK);
        PdfPCell celdaContenidoTabla = new PdfPCell();
        celdaContenidoTabla.setBorder(0);
        celdaContenidoTabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaContenidoTabla.setVerticalAlignment(Element.ALIGN_CENTER);


        Font fuenteSubTitulo = FontFactory.getFont("Courier",11,Font.BOLD,Color.BLACK);

        //Titulo
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;
        celda=new PdfPCell(new Phrase("FACTURA",fuenteTitulo));
        celda.setBorder(0);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(10);

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



        //ESTRUCTURA DEL DOCUMENTO
        document.add(tablaTitulo);
        document.add(tablaDetalles);

	}

	

}