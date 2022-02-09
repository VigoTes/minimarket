package com.minimarket.demo.util;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.PageSize;
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


        PdfPTable tablaDetalles = new PdfPTable(5);

        //ocument.setPageSize(PageSize.LARGE_CROWN_OCTAVO);
        document.setPageSize(PageSize.LEGAL);
        document.open();

        detalles.forEach(detalle -> {
            tablaDetalles.addCell("0");
            tablaDetalles.addCell(detalle.codProducto+"");
            tablaDetalles.addCell(detalle.precioUnitario+"");
            tablaDetalles.addCell(detalle.cantidad+"");
            tablaDetalles.addCell(detalle.total+"");
        });


        document.add(tablaDetalles);

	}

	

}