package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "punto_venta")
public class PuntoVenta extends ModeloGuardable{
	public static String tableName="punto_venta";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codPunto;
	public static String idColumnName="codPunto";

	

	@Column(name="nombre")
	public String nombre;    
    @Column(name="codPersonalCajero")
	public int codPersonalCajero;
	
    @Column(name="direccion")
	public String direccion;

    @Column(name="activo")
	public int activo;
	
	
	public PuntoVenta() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static PuntoVenta findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<PuntoVenta> resultados = 
				db.where(idColumnName+"=?", id).results(PuntoVenta.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
    

    public Personal gCajero() throws Exception{
        return Personal.findOrFail(String.valueOf(this.codPersonalCajero));
    }

    public String gEstado(){
        if(this.estaActivo())
            return "Activo";
        
        return "Deshabilitado";
            
    } 
	
	public String gColor(){

        if(!this.estaActivo())
            return "rojo";

        return "";

    }

    public boolean estaActivo(){
        return this.activo==1;

    }
	
}