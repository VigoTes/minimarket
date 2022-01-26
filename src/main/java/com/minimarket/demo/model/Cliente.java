package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "cliente")
public class Cliente extends ModeloGuardable{
	public static String tableName="cliente";
	
	@Id
	public String dni;
	public static String idColumnName="dni";

	

	@Column(name="nombres")
	public String nombre;
    @Column(name="apellidos")
	public String apellidos;
	
	
	public Cliente() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Cliente findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Cliente> resultados = 
				db.where(idColumnName+"=?", id).results(Cliente.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
	
	
	
	
}
