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
import com.minimarket.demo.model.Proveedor;
import com.minimarket.demo.model.TipoPersonal;

import librerias.Debug;
import librerias.ManejadorSesion;

@Controller
@RequestMapping("Proveedores")
public class ProveedorController {



	@GetMapping("/Listar")
	public String Listar(Model model, HttpSession session) {
		
		Database db = new Database();
		List<Proveedor> listaProveedores = db.results(Proveedor.class);
		model.addAttribute("listaProveedores",listaProveedores);
		
		db.close();
        
        
		
		 
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		return "Proveedores/ListarProveedores";
	}


	
	@GetMapping("/Crear")
	public String Crear(Model model, HttpSession session) {
		 

		return "Proveedores/CrearProveedor";
	}


	
	@GetMapping("/Editar/{codProveedor}")
	public String Editar(Model model, HttpSession session,@PathVariable String codProveedor)  throws Exception {
		
		Database db = new Database();
		Proveedor proveedor = Proveedor.findOrFail(String.valueOf(codProveedor));

        model.addAttribute("proveedor",proveedor);
		db.close();


		return "Proveedores/EditarProveedor";
	}


	
	 
	

	@PostMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request
		,String telefonoContacto,  String razonSocial, String nombreContacto, String ruc) {
		
	 
		Proveedor x = new Proveedor();
        x.razonSocial = razonSocial;
        x.telefonoContacto = telefonoContacto ;
        x.nombreContacto = nombreContacto ;
        x.ruc = ruc;

		x.guardar();

		ManejadorSesion.addMsj(request, "Se ha creado el Proveedor '" + x.razonSocial  + "'.");
		return new ModelAndView ("redirect:/Proveedores/Listar", model);
	}


	@PostMapping("/Actualizar")
	public ModelAndView  Actualizar(ModelMap  model, String codProveedor, HttpServletRequest request
            ,String telefonoContacto,  String razonSocial, String nombreContacto, String ruc) throws Exception {
		 
		Proveedor x = Proveedor.findOrFail(codProveedor);
       
        x.razonSocial = razonSocial;
        x.telefonoContacto = telefonoContacto ;
        x.nombreContacto = nombreContacto ;
        x.ruc = ruc;
        
		x.guardar();

		ManejadorSesion.addMsj(request, "Se ha actualizado el Proveedor '" + x.razonSocial  + "'.");
		return new ModelAndView ("redirect:/Proveedores/Listar", model);
	}

	@GetMapping("/Ver")
	public String Ver(Model model,String id) throws Exception {
		
		Proveedor proveedor = Proveedor.findOrFail(id);

		model.addAttribute("proveedor",proveedor);
		return "prueba";
	}
	
    

	

 

	
}
