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
	public String codCategoria;
	@Column(name="codEstadoProducto")
	public String codEstadoProducto;
	@Column(name="nombre")
	public String nombre;
	@Column(name="codigoLegible")
	public String codigoLegible;
	@Column(name="precioActual")
	public String precioActual;
	
	
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
	
	
	
	
}
