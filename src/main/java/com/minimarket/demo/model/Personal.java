package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "personal")
public class Personal extends ModeloGuardable{
	public static String tableName="personal";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codPersonal;
	public static String idColumnName="codPersonal";

	

	@Column(name="nombres")
	public String nombres;
    @Column(name="apellidos")
	public String apellidos;
	@Column(name="dni")
	public String dni;

    @Column(name="codTipoPersonal")
	public int codTipoPersonal;
	@Column(name="codUsuario")
	public int codUsuario;

	public Personal() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Personal findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Personal> resultados = 
				db.where(idColumnName+"=?", id).results(Personal.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
    

    
	
	
}
