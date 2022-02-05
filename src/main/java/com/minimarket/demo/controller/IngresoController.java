package com.minimarket.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dieselpoint.norm.Database;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import librerias.Debug;
import librerias.JSONER;
import librerias.ManejadorSesion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.minimarket.demo.model.IngresoAlmacen;
import com.minimarket.demo.model.Producto;
import com.minimarket.demo.model.Proveedor;
import com.minimarket.demo.model.Personal;
import com.minimarket.demo.model.Lote;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.ModelMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("IngresoAlmacen")
public class IngresoController {

	
	
	

	@GetMapping("/Listar")
	public String listarIngresos(Model model, HttpSession session) {
		
		Database db = new Database();
		List<IngresoAlmacen> listaIngresos = db.results(IngresoAlmacen.class);
		db.close();

		model.addAttribute("listaIngresos",listaIngresos);
		
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		
		return "Ingresos/listar";
	}

	@GetMapping("/Registrar")
	public String registrarIngreso(Model model, HttpSession session) throws JsonProcessingException {
		
		Database db = new Database();
		List<Producto> listaProductos = db.results(Producto.class);
		List<Proveedor> listaProveedores = db.results(Proveedor.class);
		List<Personal> listaPersonal = db.results(Personal.class);
		db.close();

		model.addAttribute("listaProductos",listaProductos);
		model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("listaPersonal",listaPersonal);
		

        model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
		model.addAttribute("json_listaProveedores",JSONER.toJson(listaProveedores));
		model.addAttribute("json_listaPersonal",JSONER.toJson(listaPersonal));
		 
		return "Ingresos/registrar";
	}
	
	@GetMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request
		                        ,String json_detalles,  String codPersonal, String puntoVenta_codigo) throws Exception {
                                    
		
            Debug.print(json_detalles);
            
            JSONArray array = new JSONArray(json_detalles);
        
            IngresoAlmacen ingreso = new IngresoAlmacen();
			ingreso.fechaHoraIngreso = LocalDateTime.now();
            ingreso.codPersonalQueIngreso = Integer.valueOf(codPersonal);
			ingreso.costoTotal = 0;
            ingreso.guardar();
            
			int codPunto = Integer.valueOf(puntoVenta_codigo);

            float total = 0; 
			float costo;
            int codProducto, codProveedor, cantidad;
			LocalDateTime fechaVencimiento;
            for (int i = 0; i < array.length (); i++) {
	            
				JSONObject jsonObj = array.getJSONObject(i);
				Debug.print(jsonObj.toString());
				
				cantidad = Integer.valueOf(jsonObj.get("cantidad").toString());
				codProducto = (int) jsonObj.get("producto_codigo");
				codProveedor = (int) jsonObj.get("proveedor_codigo");
				fechaVencimiento = LocalDateTime.now();
				costo = Float.valueOf(jsonObj.get("costo").toString());
				
				//Producto producto = Producto.findOrFail(String.valueOf(codProducto));
				//Proveedor proveedor = Proveedor.findOrFail(String.valueOf(codProveedor));
				
				Lote lote = new Lote();
				lote.codProducto = codProducto;
				lote.codPunto = codPunto;
				lote.stock = cantidad;
				lote.fechaVencimiento=fechaVencimiento;
				lote.stockIngresado=cantidad;
				lote.codIngresoAlmacen=ingreso.codIngresoAlmacen;
				lote.costoCompraLote=costo;
				lote.codigoLegible="XDXD";
				lote.codProveedor=codProveedor;
				lote.guardar();
				
				total += lote.costoCompraLote;
            }
            
            ingreso.costoTotal = total;
            ingreso.guardar();
           

            ManejadorSesion.addMsj(request, "Ingreso registrada exitosamente.");
            return new ModelAndView ("redirect:/IngresoAlmacen/Listar", model);
	}
	
	
}
