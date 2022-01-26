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
import com.minimarket.demo.model.EstadoProducto;
import com.minimarket.demo.model.ModeloGuardable;
import com.minimarket.demo.model.Producto;

import librerias.Debug;
import librerias.ManejadorSesion;

@Controller
@RequestMapping("Productos")
public class ProductoController {




	@GetMapping("/Listar")
	public String Listar(Model model, HttpSession session) {
		
		Database db = new Database();
		List<Producto> listaProductos = db.results(Producto.class);
		model.addAttribute("listaProductos",listaProductos);
		
		db.close();

		
		
		 
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		return "Productos/ListarProductos";
	}


	
	@GetMapping("/Crear")
	public String Crear(Model model, HttpSession session) {
		
		Database db = new Database();
		List<Categoria> listaCategorias = db.results(Categoria.class);
		model.addAttribute("listaCategorias",listaCategorias);
		
		db.close();

		return "Productos/CrearProducto";
	}


	
	@GetMapping("/Editar/{codProducto}")
	public String Editar(Model model, HttpSession session,@PathVariable int codProducto)  throws Exception {
		
		Database db = new Database();
		List<Categoria> listaCategorias = db.results(Categoria.class);
		List<EstadoProducto> listaEstados = db.results(EstadoProducto.class);

		model.addAttribute("listaCategorias",listaCategorias);
        model.addAttribute("listaEstados",listaEstados);

        
		Producto x = Producto.findOrFail(String.valueOf(codProducto));
        model.addAttribute("producto",x);
		
		db.close();


		return "Productos/EditarProducto";
	}


	
	 
	

	@GetMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request
		,int codCategoria,  String nombre, String codigoLegible, float precioActual) {
		
	 
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


	@GetMapping("/Actualizar")
	public ModelAndView  Guardar(ModelMap  model, int codProducto, HttpServletRequest request
		,int codCategoria, String nombre, int codEstadoProducto, String codigoLegible, float precioActual) throws Exception {
		Debug.print("LLEGA ANTES");

		Producto x = Producto.findOrFail(String.valueOf(codProducto));
        Debug.print(""+x.codProducto);
		x.codCategoria = codCategoria;
		x.nombre = nombre;
		x.codigoLegible = codigoLegible;
		x.precioActual = precioActual;
        x.codEstadoProducto = codEstadoProducto;
		x.guardar();

		ManejadorSesion.addMsj(request, "Se ha actualizado el producto '" + x.nombre  + "'.");
		return new ModelAndView ("redirect:/Productos/Listar", model);
	}




 

	@GetMapping("/Ver")
	public String Ver(Model model,String id) throws Exception {
		
		 
		
		Producto prod5 = Producto.findOrFail(id);
		 
		model.addAttribute("var",id);
		model.addAttribute("descripcion",prod5.nombre);
		 
		
		
		return "prueba";
	}
	
	@GetMapping("/Eliminar")
	public String Eliminar(Model model,String codProducto) throws NumberFormatException, Exception {
		
		//añadir verificacion si el prod está siendo usado
		
		Producto prod = Producto.findOrFail(codProducto);
		prod.eliminar();
		
		return "prueba";
		 
	}
	
	
	
	@GetMapping("/prueba")
	public ModelAndView prueba (ModelMap  model,HttpServletRequest request) throws Exception {
		 
		ManejadorSesion.addMsj(request, "Se ha eliminado el pas");
		return new ModelAndView ("redirect:/Productos/Listar", model);
	}


	

 

	
}
