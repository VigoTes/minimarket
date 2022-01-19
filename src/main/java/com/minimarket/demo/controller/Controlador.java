package com.minimarket.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dieselpoint.norm.Database;
import com.minimarket.demo.model.Producto;

@Controller
@RequestMapping()
public class Controlador {

	
	 
	@GetMapping("/listar")
	public String listar(Model model) {
	 
		
		 
		return "index";
	}

	@GetMapping("/probandoCosas")
	public String probandoCosas(Model model,String id) {
		
		
		Producto prod = new Producto();
		prod.setDescripcion("insertando");
		prod.guardar();
		
		Producto prod5 = Producto.findOrFail(5);
		
		model.addAttribute("var",id);
		model.addAttribute("descripcion",prod5.getDescripcion());
		
		
		
		
		return "prueba";
	}



	
}
