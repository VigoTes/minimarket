package com.minimarket.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Ingreso")
public class IngresoController {

	
	
	

	@GetMapping("/listarIngresos")
	public String listarIngresos(Model model) {
	 
		return "Ingresos/listar";
	}

	@GetMapping("/registrarIngreso")
	public String registrarIngreso(Model model) {	
		 
		return "Ingresos/registrar";
	}
	
	
	
	
}
