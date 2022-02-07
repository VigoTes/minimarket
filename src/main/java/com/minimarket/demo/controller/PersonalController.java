package com.minimarket.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.minimarket.demo.model.Personal;
import com.minimarket.demo.model.TipoPersonal;
import com.minimarket.demo.model.Usuario;

import librerias.Debug;
import librerias.ManejadorSesion;

@Controller
@RequestMapping("Personal")
public class PersonalController {



	@GetMapping("/Listar")
	public String Listar(Model model, HttpSession session) {
		
		Database db = new Database();
		List<Personal> listaPersonal = db.results(Personal.class);
		model.addAttribute("listaPersonal",listaPersonal);
		
		db.close();
        
		
		
		 
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		return "Personal/ListarPersonal";
	}


	
	@GetMapping("/Crear")
	public String Crear(Model model, HttpSession session) {
		
		Database db = new Database();
		List<TipoPersonal> listaTipoPersonal = db.results(TipoPersonal.class);

		model.addAttribute("listaTipoPersonal",listaTipoPersonal);
		
		db.close();

		return "Personal/CrearPersonal";
	}


	
	@GetMapping("/Editar/{codPersonal}")
	public String Editar(Model model, HttpSession session,@PathVariable int codPersonal)  throws Exception {
		
		Database db = new Database();
        List<TipoPersonal> listaTipoPersonal = db.results(TipoPersonal.class);

        model.addAttribute("listaTipoPersonal",listaTipoPersonal);
		
        
		Personal personal = Personal.findOrFail(String.valueOf(codPersonal));
        model.addAttribute("personal",personal);
		
		db.close();


		return "Personal/EditarPersonal";
	}


	
	 
	

	@PostMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request
		    ,String nombres,String apellidos, String dni, int codTipoPersonal, String usuario, String password) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        String contraseñaEncriptada = bCryptPasswordEncoder.encode(password);
            
        Usuario user = new Usuario();
        user.usuario = usuario;
        user.password = contraseñaEncriptada;
        user.guardar();
        

        Personal personal = new Personal();
        personal.nombres = nombres;
        personal.apellidos = apellidos;
        personal.dni = dni;
        personal.codTipoPersonal = codTipoPersonal;
        personal.codUsuario = user.codUsuario;
        personal.guardar();
        
        ManejadorSesion.addMsj(request, "Se ha creado el Personal '" + personal.gNombreCompleto()  + "'.");
        return new ModelAndView ("redirect:/Personal/Listar", model);
	}

    @PostMapping("/Actualizar")
	public ModelAndView  Actualizar(ModelMap  model , HttpServletRequest request, String codPersonal,String codUsuario
		    ,String nombres,String apellidos, String dni, int codTipoPersonal, String usuario, String password)  throws Exception{

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        String contraseñaEncriptada = bCryptPasswordEncoder.encode(password);
            
        Usuario user =  Usuario.findOrFail(codUsuario);
        user.usuario = usuario;
        user.password = contraseñaEncriptada;
        user.guardar();
        

        Personal x = Personal.findOrFail(codPersonal);
        x.nombres = nombres;
        x.apellidos = apellidos;
        x.dni = dni;
        x.codTipoPersonal = codTipoPersonal;
        x.guardar();
        
        ManejadorSesion.addMsj(request, "Se ha actualizado el Personal '" + x.gNombreCompleto()  + "'.");
        return new ModelAndView ("redirect:/Personal/Listar", model);
	}


	@GetMapping("/Ver")
	public String Ver(Model model,String id) throws Exception {
		
		Personal personal = Personal.findOrFail(id);

		model.addAttribute("personal",personal);
		return "prueba";
	}
	
	@GetMapping("/Deshabilitar/{codPersonal}")
	public ModelAndView Deshabilitar( HttpServletRequest request,ModelMap model, @PathVariable  String codPersonal) throws NumberFormatException, Exception {
		
		 
		Personal personal = Personal.findOrFail(codPersonal);
        personal.activo = 0;
		personal.guardar();
		
		ManejadorSesion.addMsj(request, "Se ha DESHABILITADO el Personal '" + personal.gNombreCompleto()  + "'.");
		return new ModelAndView ("redirect:/Personal/Listar", model);
		 
	}
	
    @GetMapping("/Habilitar/{codPersonal}")
	public ModelAndView Habilitar( HttpServletRequest request,ModelMap model, @PathVariable  String codPersonal) throws NumberFormatException, Exception {
		
		 
		Personal personal = Personal.findOrFail(codPersonal);
        personal.activo = 1;
		personal.guardar();
		
		ManejadorSesion.addMsj(request, "Se ha HABILITADO el Personal '" + personal.gNombreCompleto()  + "'.");
		return new ModelAndView ("redirect:/Personal/Listar", model);
		 
	}
	
	 

	

 

	
}
