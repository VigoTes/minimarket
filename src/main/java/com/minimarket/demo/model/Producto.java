package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "producto")
public class Producto extends ModeloGuardable{
	public static String tableName="producto";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codProducto;
	public static String idColumnName="codProducto";

	
	
	
	@Column(name="codCategoria")
	public int codCategoria;
	@Column(name="codEstadoProducto")
	public int codEstadoProducto;
	@Column(name="nombre")
	public String nombre;
	@Column(name="codigoLegible")
	public String codigoLegible;
	@Column(name="precioActual")
	public float precioActual; 
	
	
	public Producto() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Producto findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Producto> resultados = 
				db.where(idColumnName+"=?", id).results(Producto.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
	
	
    public Categoria gCategoria() throws Exception{
    
        return Categoria.findOrFail( " "+ this.codCategoria);
    

        
    }
	
	public String gColor(){

        if(this.codEstadoProducto==2)
            return "rojo";

        return "";

    }

    public EstadoProducto gEstado() throws Exception{
        return EstadoProducto.findOrFail( " "+ this.codEstadoProducto);
    
    }

    
    public String toString() {
    	
    	return "a";
    }
}
