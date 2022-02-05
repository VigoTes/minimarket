package com.minimarket.demo.controller;


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
	public String Listar(Model model, HttpSession session) {
		
		Database db = new Database();
		List<Venta> listaVentas = db.results(Venta.class);
		model.addAttribute("listaVentas",listaVentas);
		
		db.close();
		
		
		 
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
        
		return "Ventas/ListarVentas";
	}


	
	@GetMapping("/Crear")
	public String Crear(Model model, HttpSession session) throws JsonProcessingException {
		
		Database db = new Database();
		List<Producto> listaProductos = db.results(Producto.class);

		model.addAttribute("listaProductos",listaProductos);
		
		db.close();
		
        
        model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
		return "Ventas/CrearVenta";
	}


/* 
	 
    // ME QUEDÉ AQUI TRATANDO DE ITERAR ESTE OBJETO JSON 
	@GetMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request
		,String json_detalles,  String dni) {
		
            JSONObject object = new JSONObject ();
            JSONArray keys = object.names ();

            for (int i = 0; i < keys.length (); i++) {
	            
	            String key = keys.getString (i); // Here's your key
	            String value = object.getString (key); // Here's your value
	            
	            
            }

            Producto x = new Producto();
            x.codCategoria = codCategoria;
            x.codEstadoProducto = 1;
            x.nombre = nombre;
            x.codigoLegible = codigoLegible;
            x.precioActual = precioActual;
            x.guardar();

            ManejadorSesion.addMsj(request, "Se ha creado el producto '" + x.nombre  + "'.");
            return new ModelAndView ("redirect:/Productos/Listar", model);
	}

 */

    
}
