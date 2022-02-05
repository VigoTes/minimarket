package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "proveedor")
public class Proveedor extends ModeloGuardable{
	public static String tableName="proveedor";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codProveedor;
	public static String idColumnName="codProveedor";

	

	@Column(name="nombre")
	public String nombre;
    @Column(name="ruc")
	public String ruc;
	@Column(name="contacto")
	public String contacto;
	
	public Proveedor() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Proveedor findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Proveedor> resultados = 
				db.where(idColumnName+"=?", id).results(Proveedor.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
    

   	
	
	
}
