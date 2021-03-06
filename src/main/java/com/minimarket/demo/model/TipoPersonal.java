package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "tipo_personal")
public class TipoPersonal extends ModeloGuardable{
	public static String tableName="tipo_personal";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codTipoPersonal;
	public static String idColumnName="codTipoPersonal";

	public static String codTipoCajero = "2";

	@Column(name="nombre")
	public String nombre;
   
	
	
	public TipoPersonal() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static TipoPersonal findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<TipoPersonal> resultados = 
				db.where(idColumnName+"=?", id).results(TipoPersonal.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}


    

	
	
	
}
