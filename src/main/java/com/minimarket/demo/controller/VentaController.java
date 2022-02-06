package com.minimarket.demo.controller;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

 
import org.springframework.web.bind.annotation.PathVariable;
import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.sqlmakers.Property;
import com.dieselpoint.norm.sqlmakers.StandardPojoInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.minimarket.demo.model.Categoria;
import com.minimarket.demo.model.DetalleVenta;
import com.minimarket.demo.model.EstadoProducto;
import com.minimarket.demo.model.ModeloGuardable;
import com.minimarket.demo.model.Producto;
import com.minimarket.demo.model.Venta;

import librerias.Debug;
import librerias.JSONER;
import librerias.ManejadorSesion;

@Controller
@RequestMapping("Ventas")
public class VentaController {
    



	@GetMapping("/Listar")
	public ModelAndView Listar(Model model, HttpSession session) {
		
		Database db = new Database();
		List<Venta> listaVentas = db.results(Venta.class);
		model.addAttribute("listaVentas",listaVentas);
		
		db.close();
		
		
		
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		return new ModelAndView("Ventas/ListarVentas");
	}


	
	@GetMapping("/Crear")
	public ModelAndView Crear(ModelMap model, HttpSession session) throws JsonProcessingException {
		
		Database db = new Database();
		List<Producto> listaProductos = db.results(Producto.class);

		model.addAttribute("listaProductos",listaProductos);
		
		db.close();
		
        
        model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
        return new ModelAndView("Ventas/CrearVenta");
		 
	}



	 
    // ME QUEDÉ AQUI TRATANDO DE ITERAR ESTE OBJETO JSON 
	@GetMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request
		                        ,String json_detalles,  String dni) throws Exception {
                                    
		
            Debug.print(json_detalles);
            
            JSONArray array = new JSONArray(json_detalles);
        
            Venta venta = new Venta();
            venta.codPunto = 1;
            venta.codPersonal = 1;
            venta.importeBruto = 0;
            venta.importeTotal = 0;
            venta.igv = 0;
            venta.dni = dni;
            venta.fechaHora = LocalDateTime.now();
            venta.codigoLegible = "V2521";
            venta.guardar();
            
            float total = 0;
            int codProducto;
            int cantidad ;
            for (int i = 0; i < array.length (); i++) {
	            
            	 JSONObject jsonObj = array.getJSONObject(i);
            	 Debug.print(jsonObj.toString());
            	 
            	 codProducto = (int) jsonObj.get("producto_codigo");
           
            	 cantidad = Integer.valueOf(jsonObj.get("cantidad").toString());
            	 
            	 Producto producto = Producto.findOrFail(String.valueOf(codProducto));
            	 
            	 DetalleVenta detalle = new DetalleVenta();
            	 detalle.codVenta = venta.codVenta;
            	 detalle.codProducto = codProducto;
            	 detalle.cantidad = cantidad;
            	 
            	 detalle.precioUnitario = producto.precioActual;
            	 detalle.total = producto.precioActual * cantidad;
            	 detalle.guardar();
                 
            	 total += detalle.total;
            }
            
            venta.importeTotal = total;
            venta.igv = (float) (total / 1.18);
            venta.importeBruto = venta.importeTotal - venta.importeBruto;
            venta.guardar();
           

            ManejadorSesion.addMsj(request, "Venta registrada exitosamente.");
            return new ModelAndView ("redirect:/Ventas/Listar", model);
	}


    
    
}
