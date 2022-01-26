package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "categoria")

public class Categoria extends ModeloGuardable{
	public static String tableName="categoria";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codCategoria;
	public static String idColumnName="codCategoria";

	

	@Column(name="nombre")
	public String nombre;
	
	
	public Categoria() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Categoria findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Categoria> resultados = 
				db.where(idColumnName+"=?", id).results(Categoria.class);
		db.close();
		if(resultados.size()==0)
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		
		
		return resultados.get(0);	
	}
	
	
	
	
}
