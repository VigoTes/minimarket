package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "parametro")
public class Parametro extends ModeloGuardable{
	public static String tableName="parametro";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codParametro;
	public static String idColumnName="codParametro";

	

	@Column(name="valor")
	public float valor;
    @Column(name="nombre")
	public String nombre;
	
	
	public Parametro() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Parametro findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Parametro> resultados = 
				db.where(idColumnName+"=?", id).results(Parametro.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
 
	
	
}
