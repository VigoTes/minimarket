package com.minimarket.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.sqlmakers.Property;
import com.dieselpoint.norm.sqlmakers.StandardPojoInfo;
import com.minimarket.demo.model.ModeloGuardable;
import com.minimarket.demo.model.Producto;

@Controller
@RequestMapping()
public class Controlador {

	
	 
	@GetMapping("/")
	public String listar(Model model) {
	 
		 
		return "home";
	}

	@GetMapping("/listarIngresos")
	public String listarIngresos(Model model) {
	 
		
		 
		return "Ingresos/listar";
	}

	@GetMapping("/registrarIngreso")
	public String registrarIngreso(Model model) {
	 
		
		 
		return "Ingresos/registrar";
	}


	@GetMapping("/getProducto")
	public String getProducto(Model model,String id) throws Exception {
		
		 
		
		Producto prod5 = Producto.findOrFail(id);
		
		model.addAttribute("var",id);
		model.addAttribute("descripcion",prod5.descripcion);
		 
		
		
		return "prueba";
	}
	
	@GetMapping("/eliminarProducto")
	public String eliminarProducto(Model model,String id) throws NumberFormatException, Exception {
		
		Producto prod5 = Producto.findOrFail(id);
		
		model.addAttribute("var","Se eliminó correctamente el Producto"+prod5.id());
		
		int cantRows = prod5.eliminar();
		
		model.addAttribute("var2",cantRows);
		
		
		return "prueba";
	}
	
	
	


	@GetMapping("/probandoCosas")
	public String probandoCosas(Model model) throws Exception {
		
		Producto p = Producto.findOrFail("138");
		p.descripcion = "nueva desc";
		p.guardar();
		model.addAttribute("var2",p.id());
		
		return "prueba";
	}


	
}
