package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "tipo_cdp")

public class TipoCDP extends ModeloGuardable{
	public static String tableName="tipo_cdp";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codTipoCDP;
	public static String idColumnName="codTipoCDP";

	

	@Column(name="nombre")
	public String nombre;
	
	
	public TipoCDP() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static TipoCDP findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<TipoCDP> resultados = 
				db.where(idColumnName+"=?", id).results(TipoCDP.class);
		db.close();
		if(resultados.size()==0)
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		
		
		return resultados.get(0);	
	}
	
	
	
	
}
