package com.minimarket.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.minimarket.demo.model.*;


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
public class AuthController {


	 
    @GetMapping("/login")
	public String verLogin(Model model, HttpSession session) {

        /* 
        if(Empleado::getEmpleadoLogeado()!=""){//si hay alguien logeado
            return redirect()->route('user.home');
        }
        */
        
		
        
        
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		return "Login";
	}

	@PostMapping("/logearse")
	public ModelAndView logearse(ModelMap model, HttpServletRequest request, 
				String usuario, String password, HttpSession session) {

                    
        Debug.print("LLEGANDO PRO ACUI");
        //verificamos si existe el usuario
        String msjError = "";
        Database db = new Database();
        List<Usuario> listaUsuarios = db.where("usuario=?",usuario).results(Usuario.class);
        db.close();
        if( listaUsuarios.size() >0) {

            Usuario objectoUsuario = listaUsuarios.get(0);
            BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder(4);
            Debug.print("La contraseña es:"+objectoUsuario.password);
            if(encriptador.matches(password, objectoUsuario.password) ){ 
                ManejadorSesion.iniciarSesion(session,objectoUsuario.codUsuario);
            }else{
                msjError = "Contraseña incorrecta.";
            }
        }else {
            msjError =  "El usuario ingresado no existe.";
        	
        }


        
        //Si hubo algun error
        if(msjError !=""){
            ManejadorSesion.addMsj(request,msjError);
            return new ModelAndView ("redirect:/login", model);
        }

        
		 
		return new ModelAndView ("redirect:/", model);
	}
	 
	
 
    @GetMapping("/cerrarSesion")
    public ModelAndView cerrarSesion(ModelMap model, HttpSession session){

        ManejadorSesion.cerrarSesion(session);
        return new ModelAndView ("redirect:/login", model);
    }

/* 
    
    //Cuando se quiera ir a / se redirijirá a /home
    @GetMapping("/")
    public ModelAndView redireccionHome(ModelMap model,HttpSession session){
        return new ModelAndView("redirect:/",model);
    }
     */


    //ESTA VA A SER LA RUTA BASE
    @GetMapping("/home")
    public ModelAndView home(ModelMap model, HttpSession session){

        if(!ManejadorSesion.hayPersonalLogeado(session)){ //si no hay nadie logeado, lo mandamos al login
            return new ModelAndView ("redirect:/login", model);
        }

        return new ModelAndView("home");
    }
    

    @GetMapping("/probandoCosas")
    public String probandoCosas(Model model, HttpSession session) throws Exception{
        
        IngresoAlmacen i = IngresoAlmacen.findOrFail("2");

        model.addAttribute("var",i.obtenerCostoTotalFormateado());

        return "prueba";
    }


    
    //URL EJEMPLO :: /encriptarContraseña?clave=123
    @GetMapping("/encriptarContraseña")
    public String encriptarContraseña(Model model, HttpSession session,String clave) {
        
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        String contraseñaEncriptada = bCryptPasswordEncoder.encode(clave);
        model.addAttribute("var",contraseñaEncriptada);

        return "prueba";
    }
    
    
    @GetMapping("/verSesion")
    public String verSesion(Model model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("var", ManejadorSesion.getAuthID(session));
        return "prueba";
    }

 

}
