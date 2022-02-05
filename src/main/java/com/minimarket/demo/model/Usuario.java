package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "usuario")
public class Usuario extends ModeloGuardable{
	public static String tableName="usuario";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codUsuario;
	public static String idColumnName="codUsuario";
    
	

	@Column(name="usuario")
	public String usuario;
    @Column(name="password")
	public String password;
	
	
	public Usuario() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Usuario findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Usuario> resultados = 
				db.where(idColumnName+"=?", id).results(Usuario.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
    

	
	
	
}
