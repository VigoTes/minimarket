package com.minimarket.demo.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dieselpoint.norm.Database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import librerias.Debug;
import java.util.Date;
import librerias.JSONER;
import librerias.ManejadorSesion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.minimarket.demo.model.IngresoAlmacen;
import com.minimarket.demo.model.Producto;
import com.minimarket.demo.model.Proveedor;
import com.minimarket.demo.model.Personal;
import com.minimarket.demo.model.Lote;
import com.minimarket.demo.model.PuntoVenta;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.ModelMap;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;  

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("IngresoAlmacen")
public class IngresoController {
	private DateTimeFormatter formatoObtenerFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	
	
    
	@GetMapping("/Listar")
	public String listarIngresos(Model model, HttpSession session, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin) throws Exception {

		if(fechaInicio==null){
			fechaInicio = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
			//fechaInicio = "08/02/2022";
		}
		if(fechaFin==null){
			fechaFin = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
			//fechaFin = "08/02/2022";
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(fechaInicio+" 00:00", formatter);
		LocalDateTime fin = LocalDateTime.parse(fechaFin+" 00:00", formatter);

		List<IngresoAlmacen> listaIngresos = IngresoAlmacen.filtrarIngresos(inicio, fin);
		/*
		Database db = new Database();
		List<IngresoAlmacen> listaIngresos = db.results(IngresoAlmacen.class);
		db.close();
		*/
		model.addAttribute("listaIngresos",listaIngresos);
		model.addAttribute("fechaInicio",fechaInicio);
		model.addAttribute("fechaFin",fechaFin);
		
        
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		//model.addAttribute("msj",inicio.toString()+"  "+fin.toString()+"    "+IngresoAlmacen.verificarFiltro(inicio, fin));
		return "Ingresos/listar";
	}

	@GetMapping("/Registrar")
	public String registrarIngreso(Model model, HttpSession session) throws Exception {
		
		Database db = new Database();
		List<Producto> listaProductos = db.results(Producto.class);
		List<Proveedor> listaProveedores = db.results(Proveedor.class);
		//List<Personal> listaPersonal = db.results(Personal.class);
		db.close();

		PuntoVenta puntoVenta = Personal.findOrFail("1").obtenerPuntoVenta();//colocar aqui el pk del personal logueado

		model.addAttribute("listaProductos",listaProductos);
		model.addAttribute("supervisor",ManejadorSesion.getPersonalLogeado(session));
		model.addAttribute("listaProveedores",listaProveedores);
		//model.addAttribute("listaPersonal",Personal.obtenerPersonalPorTipo("Supervisor"));
		model.addAttribute("puntoVenta",puntoVenta);
		

        model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
		model.addAttribute("json_listaProveedores",JSONER.toJson(listaProveedores));
		//model.addAttribute("json_listaPersonal",JSONER.toJson(listaPersonal));
		 
		return "Ingresos/registrar";
	}
	
	@GetMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request, HttpSession session
		                        ,String json_detalles,  String puntoVenta_codigo,  String comentario) throws Exception {
                                    
		
            Debug.print(json_detalles);
            
            JSONArray array = new JSONArray(json_detalles);
        
            IngresoAlmacen ingreso = new IngresoAlmacen();
			ingreso.fechaHoraIngreso = LocalDateTime.now();
			ingreso.codPersonalQueIngreso = ManejadorSesion.getPersonalLogeado(session).codPersonal;
			ingreso.comentario = comentario;
			ingreso.costoTotal = 0;
            ingreso.guardar();
            
			int codPunto = Integer.valueOf(puntoVenta_codigo);

            float total = 0; 
			float costo;
            int cantidad;
			Date fechaVencimiento;

            for (int i = 0; i < array.length (); i++) {
				JSONObject jsonObj = array.getJSONObject(i);
				Debug.print(jsonObj.toString());
				
				cantidad = Integer.valueOf(jsonObj.get("cantidad").toString());
				JSONObject productoObj = new JSONObject(jsonObj.get("producto").toString());
				JSONObject proveedorObj = new JSONObject(jsonObj.get("proveedor").toString());
				//fechaVencimiento = LocalDate.parse(jsonObj.get("fechaVencimiento").toString()+" 00:00", formatoObtenerFecha);
				fechaVencimiento = new SimpleDateFormat("dd/MM/yyyy").parse(jsonObj.get("fechaVencimiento").toString());
				costo = Float.valueOf(jsonObj.get("costo").toString());
				
				Lote lote = new Lote();
				lote.codProducto =Integer.parseInt(productoObj.get("codProducto").toString());
				lote.codPunto = codPunto;
				
				
				
				lote.stock = cantidad;
				
				
				
				lote.fechaVencimiento=fechaVencimiento;
				lote.stockIngresado=cantidad;
				lote.codIngresoAlmacen=ingreso.codIngresoAlmacen;
				lote.costoCompraLote=costo;
				lote.codigoLegible="";
				lote.codProveedor=Integer.parseInt(proveedorObj.get("codProveedor").toString());
				lote.guardar();
				lote.codigoLegible=lote.obtenerCodigoLegible();
				lote.guardar();

				total += lote.costoCompraLote;
				
            }
            
            ingreso.costoTotal = total;
            ingreso.guardar();
           
			
            ManejadorSesion.addMsj(request, "Ingreso registrada exitosamente.");
            return new ModelAndView ("redirect:/IngresoAlmacen/Listar", model);
	}
	
	@GetMapping("/Editar/{codIngresoAlmacen}")
	public String editarIngreso(Model model, HttpSession sessionl, @PathVariable("codIngresoAlmacen") String codIngresoAlmacen) throws Exception {
		IngresoAlmacen ingresoAlmacen = IngresoAlmacen.findOrFail(codIngresoAlmacen);

		Database db = new Database();
		List<Producto> listaProductos = db.results(Producto.class);
		List<Proveedor> listaProveedores = db.results(Proveedor.class);
		db.close();

		//PuntoVenta puntoVenta = Personal.findOrFail("1").obtenerPuntoVenta();//colocar aqui el pk del personal logueado

		model.addAttribute("supervisor",ManejadorSesion.getPersonalLogeado(sessionl));
		model.addAttribute("listaProductos",listaProductos);
		model.addAttribute("listaProveedores",listaProveedores);
		//model.addAttribute("listaPersonal",Personal.obtenerPersonalPorTipo("Supervisor"));
		model.addAttribute("ingresoAlmacen",ingresoAlmacen);
		model.addAttribute("puntoVenta",ingresoAlmacen.obtenerPuntoVenta());
		

        model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
		model.addAttribute("json_listaProveedores",JSONER.toJson(listaProveedores));
		model.addAttribute("json_detallesGuardados",JSONER.toJson(ingresoAlmacen.obtenerLotes()));
		//model.addAttribute("json_listaPersonal",JSONER.toJson(listaPersonal));
		 
		return "Ingresos/editar";
	}
	

	@GetMapping("/Actualizar")
	public ModelAndView  Actualizar(ModelMap  model , HttpServletRequest request
		                        ,String json_detalles,  String puntoVenta_codigo,  String comentario,  String codIngresoAlmacen) throws Exception {
                                    
		
            Debug.print(json_detalles);
            
            JSONArray array = new JSONArray(json_detalles);
        
            IngresoAlmacen ingreso = IngresoAlmacen.findOrFail(codIngresoAlmacen);
			//ingreso.fechaHoraIngreso = LocalDateTime.now();
            //ingreso.codPersonalQueIngreso = Integer.valueOf(codPersonal);
			ingreso.comentario = comentario;
			ingreso.costoTotal = 0;
            ingreso.guardar();

			for(Lote lote:ingreso.obtenerLotes()){
				lote.eliminar();
			}
            
			int codPunto = Integer.valueOf(puntoVenta_codigo);

            float total = 0; 
			float costo;
            int cantidad;
			Date fechaVencimiento;

            for (int i = 0; i < array.length (); i++) {
				JSONObject jsonObj = array.getJSONObject(i);
				Debug.print(jsonObj.toString());
				
				cantidad = Integer.valueOf(jsonObj.get("cantidad").toString());
				JSONObject productoObj = new JSONObject(jsonObj.get("producto").toString());
				JSONObject proveedorObj = new JSONObject(jsonObj.get("proveedor").toString());
				//fechaVencimiento = LocalDate.parse(jsonObj.get("fechaVencimiento").toString()+" 00:00", formatoObtenerFecha);
				fechaVencimiento = new SimpleDateFormat("dd/MM/yyyy").parse(jsonObj.get("fechaVencimiento").toString());
				costo = Float.valueOf(jsonObj.get("costo").toString());
				
				Lote lote = new Lote();
				lote.codProducto =Integer.parseInt(productoObj.get("codProducto").toString());
				lote.codPunto = codPunto;
				
				
				
				lote.stock = cantidad;
				
				
				
				lote.fechaVencimiento=fechaVencimiento;
				lote.stockIngresado=cantidad;
				lote.codIngresoAlmacen=ingreso.codIngresoAlmacen;
				lote.costoCompraLote=costo;
				lote.codigoLegible="";
				lote.codProveedor=Integer.parseInt(proveedorObj.get("codProveedor").toString());
				lote.guardar();
				lote.codigoLegible=lote.obtenerCodigoLegible();
				lote.guardar();

				total += lote.costoCompraLote;
				
            }
            
            ingreso.costoTotal = total;
            ingreso.guardar();
           
			
            ManejadorSesion.addMsj(request, "Ingreso editado exitosamente.");
            return new ModelAndView ("redirect:/IngresoAlmacen/Listar", model);
	}

	@GetMapping("/Ver/{codIngresoAlmacen}")
	public String verIngreso(Model model, HttpSession sessionl, @PathVariable("codIngresoAlmacen") String codIngresoAlmacen) throws Exception {
		IngresoAlmacen ingresoAlmacen = IngresoAlmacen.findOrFail(codIngresoAlmacen);
		/*
		Database db = new Database();
		List<Producto> listaProductos = db.results(Producto.class);
		List<Proveedor> listaProveedores = db.results(Proveedor.class);
		db.close();
		*/
		//PuntoVenta puntoVenta = Personal.findOrFail("1").obtenerPuntoVenta();//colocar aqui el pk del personal logueado

		//model.addAttribute("listaProductos",listaProductos);
		//model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("listaPersonal",Personal.obtenerPersonalPorTipo("Supervisor"));
		model.addAttribute("ingresoAlmacen",ingresoAlmacen);
		model.addAttribute("puntoVenta",ingresoAlmacen.obtenerPuntoVenta());
		model.addAttribute("lotes",ingresoAlmacen.obtenerLotes());
		

        //model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
		//model.addAttribute("json_listaProveedores",JSONER.toJson(listaProveedores));
		
		//model.addAttribute("json_listaPersonal",JSONER.toJson(listaPersonal));
		 
		return "Ingresos/ver";
	}





	@GetMapping("/ListarLotes")
	public String ListarLotes(Model model, HttpSession session) throws Exception {
		 
		Database db = new Database();
		List<Lote> listaLotes = db.orderBy("codLote DESC") .results(Lote.class);
		db.close();
	 
		model.addAttribute("listaLotes",listaLotes);
		 
		return "Ingresos/ListarLotes";
	}



}
