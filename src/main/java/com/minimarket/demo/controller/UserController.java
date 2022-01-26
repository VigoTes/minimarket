package com.minimarket.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
	

	
	 
	@GetMapping("/")
	public String listar(Model model) {
	 
		 
		return "home";
	}

}
