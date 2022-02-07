package com.minimarket.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.minimarket.demo.model.Categoria;
 
import com.minimarket.demo.model.ModeloGuardable;
import com.minimarket.demo.model.Personal;
import com.minimarket.demo.model.PuntoVenta;
import com.minimarket.demo.model.TipoPersonal;

import librerias.Debug;
import librerias.ManejadorSesion;

@Controller
@RequestMapping("PuntosVenta")
public class PuntoVentaController {



	@GetMapping("/Listar")
	public String Listar(Model model, HttpSession session) {
		
		Database db = new Database();
		List<PuntoVenta> listaPuntosVenta = db.results(PuntoVenta.class);
		model.addAttribute("listaPuntosVenta",listaPuntosVenta);
		
		db.close();
        
		
		
		 
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		return "PuntosVenta/ListarPuntosVenta";
	}


	
	@GetMapping("/Crear")
	public String Crear(Model model, HttpSession session) {
		
		Database db = new Database();
		List<Personal> listaCajeros = db.where("codTipoPersonal=?", TipoPersonal.codTipoCajero) .results(Personal.class);
		model.addAttribute("listaCajeros",listaCajeros);
		
		db.close();

		return "PuntosVenta/CrearPuntoVenta";
	}


	
	@GetMapping("/Editar/{codPunto}")
	public String Editar(Model model, HttpSession session,@PathVariable int codPunto)  throws Exception {
		
		Database db = new Database();
		 
        List<Personal> listaCajeros = db.where("codTipoPersonal=?", TipoPersonal.codTipoCajero).results(Personal.class);
		model.addAttribute("listaCajeros",listaCajeros);

        
		PuntoVenta x = PuntoVenta.findOrFail(String.valueOf(codPunto));
        model.addAttribute("punto",x);
		
		db.close();


		return "PuntosVenta/EditarPuntoVenta";
	}


	
	 
	

	@PostMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request
		,String direccion,  String nombre, String codPersonalCajero) {
		
	 
		PuntoVenta x = new PuntoVenta();
        x.nombre = nombre;
        x.direccion = direccion;
        x.codPersonalCajero = Integer.parseInt(codPersonalCajero);
        x.activo = 1;
		x.guardar();

		ManejadorSesion.addMsj(request, "Se ha creado el PuntoVenta '" + x.nombre  + "'.");
		return new ModelAndView ("redirect:/PuntosVenta/Listar", model);
	}


	@PostMapping("/Actualizar")
	public ModelAndView  Actualizar(ModelMap  model, int codPunto, HttpServletRequest request
		,String direccion, String nombre, String codPersonalCajero) throws Exception {
		 
		PuntoVenta x = PuntoVenta.findOrFail(String.valueOf(codPunto));
       
        x.nombre = nombre;
        x.direccion = direccion;
        x.codPersonalCajero = Integer.parseInt(codPersonalCajero);
		x.guardar();

		ManejadorSesion.addMsj(request, "Se ha actualizado el PuntoVenta '" + x.nombre  + "'.");
		return new ModelAndView ("redirect:/PuntosVenta/Listar", model);
	}

	@GetMapping("/Ver")
	public String Ver(Model model,String id) throws Exception {
		
		PuntoVenta puntoVenta = PuntoVenta.findOrFail(id);

		model.addAttribute("puntoVenta",puntoVenta);
		return "prueba";
	}
	
	@GetMapping("/Deshabilitar/{codPunto}")
	public ModelAndView Deshabilitar( HttpServletRequest request,ModelMap model, @PathVariable  String codPunto) throws NumberFormatException, Exception {
		
		 
		PuntoVenta punto = PuntoVenta.findOrFail(codPunto);
        punto.activo = 0;
		punto.guardar();
		
		ManejadorSesion.addMsj(request, "Se ha DESHABILITADO el Punto Venta '" + punto.nombre  + "'.");
		return new ModelAndView ("redirect:/PuntosVenta/Listar", model);
		 
	}
	
    @GetMapping("/Habilitar/{codPunto}")
	public ModelAndView Habilitar( HttpServletRequest request,ModelMap model, @PathVariable  String codPunto) throws NumberFormatException, Exception {
		
		 
		PuntoVenta punto = PuntoVenta.findOrFail(codPunto);
        punto.activo = 1;
		punto.guardar();
		
		ManejadorSesion.addMsj(request, "Se ha HABILITADO el Punto Venta '" + punto.nombre  + "'.");
		return new ModelAndView ("redirect:/PuntosVenta/Listar", model);
		 
	}
	
	 

	

 

	
}
