package com.minimarket.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.minimarket.demo.interfaceService.IProductoService;
import com.minimarket.demo.modelo.Producto;

@Controller
@RequestMapping
public class Controlador {

	
	@Autowired
	private IProductoService service;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		List<Producto> productos = service.listar();
		
		model.addAttribute("productos",productos);
		return "index";
	}
	
}
