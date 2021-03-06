package com.minimarket.demo.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 

import org.springframework.web.bind.annotation.PathVariable;
import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.sqlmakers.Property;
import com.dieselpoint.norm.sqlmakers.StandardPojoInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.minimarket.demo.model.Categoria;
import com.minimarket.demo.model.Cliente;
import com.minimarket.demo.model.DetalleVenta;
import com.minimarket.demo.model.EstadoProducto;
import com.minimarket.demo.model.Lote;
import com.minimarket.demo.model.ModeloGuardable;
import com.minimarket.demo.model.Personal;
import com.minimarket.demo.model.Producto;
import com.minimarket.demo.model.PuntoVenta;
import com.minimarket.demo.model.Venta;

import librerias.Debug;
import librerias.JSONER;
import librerias.ManejadorSesion;
import librerias.MaracsoftBot;

@Controller
@RequestMapping("Ventas")
public class VentaController {
    



	@GetMapping("/Listar")
	public ModelAndView Listar(Model model, HttpSession session)  throws Exception{
		
        

		Database db = new Database();
		List<Venta> listaVentas = db.orderBy("codigoLegible DESC").results(Venta.class);
		model.addAttribute("listaVentas",listaVentas);
		
		db.close();
		
		model.addAttribute("msj",ManejadorSesion.getMsj(session));
		return new ModelAndView("Ventas/ListarVentas");
	}

    



    
	
	@GetMapping("/Crear")
	public ModelAndView Crear(ModelMap model, HttpSession session) throws Exception {
		
        Personal cajero = ManejadorSesion.getPersonalLogeado(session);
        PuntoVenta punto = cajero.obtenerPuntoVenta();

		Database db = new Database();

        //Productos que tienen stock en este punto de venta
        String sqlB = "SELECT producto.* from producto "
                            + " inner join lote on lote.codProducto = producto.codProducto "
                            + " where codPunto = ?"
                            + " group by producto.codProducto "
                            + " having sum(stock)>0";
		List<Producto> listaProductos = db.sql(sqlB, punto.codPunto ) .results(Producto.class);

        List<Cliente> listaClientes = db.results(Cliente.class);
        List<Lote> listaLotesPunto = db.where("codPunto=?",punto.codPunto).results(Lote.class);

		model.addAttribute("listaProductos",listaProductos);
		model.addAttribute("listaClientes",listaProductos);
        model.addAttribute("punto",punto);
        
		
		db.close();
		
        
        model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
        model.addAttribute("json_listaClientes",JSONER.toJson(listaClientes));
        model.addAttribute("json_listaLotesPunto",JSONER.toJson(listaLotesPunto));
        
        
        return new ModelAndView("Ventas/CrearVenta");
		 
	}



	 
    // ME QUED?? AQUI TRATANDO DE ITERAR ESTE OBJETO JSON 
	@GetMapping("/Guardar")
	public ModelAndView  Guardar(ModelMap  model , HttpServletRequest request, String dni , String nombres,String apellidos, HttpSession session
            ,String ruc , String razonSocial,int codTipoCDP, int codTipoCliente ,String json_detalles,  String codCliente) throws Exception {
                                    
            Personal cajero = ManejadorSesion.getPersonalLogeado(session);


            Cliente cliente;
            //Si no lleg?? un dato cliente, creamos uno nuevo
            if(codCliente.equals("0")){
                cliente = new Cliente();
                cliente.nombres = nombres;
                cliente.apellidos = apellidos;
                cliente.dni = dni;

                cliente.ruc = ruc;
                cliente.razonSocial = razonSocial;
                cliente.codTipoCliente = codTipoCliente;
                
                cliente.guardar();
                cliente.codCliente = cliente.gUltimaID(); //ya que el ORM no inserta en el obj el nuevo id generado, lo hago manual
                
            }else
                cliente = Cliente.findOrFail(codCliente);

                
            Debug.print("El id es" + cliente.codCliente);
            Debug.print(json_detalles);
            
            JSONArray array = new JSONArray(json_detalles);
        
            Venta venta = new Venta();
            venta.codPunto = cajero.obtenerPuntoVenta().codPunto;
            venta.codPersonal = cajero.codPersonal;
            venta.importeBruto = 0;
            venta.importeTotal = 0;
            venta.igv = 0;
            venta.codTipoCDP = codTipoCDP;
            venta.codCliente = cliente.codCliente;
            venta.fechaHora = LocalDateTime.now();
            venta.codigoLegible = "";
            venta.guardar();
            
            venta.codigoLegible = "V"+ LocalDateTime.now().getYear()+"-"+ Debug.rellenarConCeros(venta.codVenta, 6);
            
            float total = 0;
            int codProducto;
            int cantidad ;
            for (int i = 0; i < array.length (); i++) {
	            
            	 JSONObject jsonObj = array.getJSONObject(i);
            	 Debug.print(jsonObj.toString());
            	 
            	 codProducto = (int) jsonObj.get("producto_codigo");
           
            	 cantidad = Integer.valueOf(jsonObj.get("cantidad").toString());
            	 
            	 Producto producto = Producto.findOrFail(String.valueOf(codProducto));
            	 
            	 DetalleVenta detalle = new DetalleVenta();
            	 detalle.codVenta = venta.codVenta;
            	 detalle.codProducto = codProducto;
            	 detalle.cantidad = cantidad;
            	 
            	 detalle.precioUnitario = producto.precioActual;
            	 detalle.total = producto.precioActual * cantidad;
            	 detalle.guardar();  
                 
            	 total += detalle.total;
            
                 
                 //Ahora restamos el stock del lote correspondiente
                 Lote loteARestar = Lote.getLoteAVender(codProducto, venta.codPunto);
                 loteARestar.stock = loteARestar.stock - cantidad;
                 loteARestar.guardar();

            }
            
            venta.importeTotal = total;
            venta.importeBruto = Math.round((float) (total / 1.18));
            venta.igv = venta.importeTotal - venta.importeBruto;
            venta.guardar();
           

            
            ManejadorSesion.addMsj(request, "Venta registrada exitosamente.");
            return new ModelAndView ("redirect:/PuntosVenta/ListarVentasDePuntoActual", model);
	}


    @GetMapping("/Ver/{codVenta}")
	public String verIngreso(Model model, HttpSession sessionl, @PathVariable("codVenta") String codVenta) throws Exception {
        Venta venta = Venta.findOrFail(codVenta);


		model.addAttribute("venta",venta);
		model.addAttribute("tipoCDP",venta.obtenerTipoCDP());
		model.addAttribute("cliente",venta.obtenerCliente());
		model.addAttribute("detalles",venta.obtenerDetalles());
		

        //model.addAttribute("json_listaProductos",JSONER.toJson(listaProductos));
		//model.addAttribute("json_listaProveedores",JSONER.toJson(listaProveedores));
		
		//model.addAttribute("json_listaPersonal",JSONER.toJson(listaPersonal));

        model.addAttribute("json_cliente",JSONER.toJson(venta.obtenerCliente()));
		 
		return "Ventas/VerVenta";
	}
    



    @GetMapping("/ConsultarPorDNI/{dni}")
    @ResponseBody //para retornar no una vista sino contenido
	public String ConsultarPorDNI(@PathVariable("dni") String dni) throws Exception {
        
        
        return  MaracsoftBot.ConsultarAPISunatDNI(dni);
	}
    


    
    
    @GetMapping("/Clientes")
	public String Clientes(Model model, HttpSession session  ) throws Exception {
         
        Database db = new Database();
        List<Cliente> listaClientes = db.results(Cliente.class);
        db.close();


        model.addAttribute("listaClientes",listaClientes); 
		return "Ventas/ListarClientes";
	}
    
    


    @GetMapping("/EnviarReporteDiario")
    @ResponseBody //para retornar no una vista sino contenido
	public String EnviarReporteDiario( HttpSession session ) throws Exception {


        DateTimeFormatter formatterVista = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        Personal personal = ManejadorSesion.getPersonalLogeado(session);
        PuntoVenta punto = personal.obtenerPuntoVenta();

        String msj = "";

        Database db = new Database();
        List<Venta> listaVentas = db.where("codPunto=? and date(fechaHora)=?", punto.codPunto,  LocalDateTime.now().format(formatterSQL) ).results(Venta.class);

        float sum = 0;
        for (Venta venta : listaVentas) {
            sum+= venta.importeTotal;
        }

        db.close();

        String totalVendido = String.valueOf(sum);

        msj = "Reporte diario. El d??a " +  LocalDateTime.now().format(formatterVista) + " se vendi?? un total de " 
        + totalVendido + " en el punto de venta " + punto.nombre;
        
        return MaracsoftBot.enviarMensaje(msj);
	}
    

}
