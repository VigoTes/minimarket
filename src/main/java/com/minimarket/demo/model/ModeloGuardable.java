package com.minimarket.demo.model;

import java.lang.reflect.Field;
import java.util.List;

import com.dieselpoint.norm.Database;
import com.minimarket.demo.model.*;

import librerias.Debug;

public class ModeloGuardable {


 
	// YA FUE ESTO XD
	public static Object probando( int id,String idColumnName,String nombreClase) {
		try {
			
			Class<?> claseHijo = Class.forName(nombreClase);
			
			Database db = new Database();
			List<?> resultados = 
					db.where(idColumnName+"=?", id).results(claseHijo);
			
			System.out.print("-------------------------------------------------------");
			System.out.print(resultados.toString());
			
			db.close();
			return resultados.get(0);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Object();
		}
		 
		
	}
	
	
	//ESTAS DOS FUNCIONES SON PARA LLAMAR A LOS ATRIBUTOS ESTÁTICOS DE LA CLASE HIJO
	public String idColumnName() {
		try {
			return this.getClass().getField("idColumnName").get(null).toString();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String tableName() {
		try {
			return this.getClass().getField("tableName").get(null).toString();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	//retorna el valor de la pk
	public int id() {
    
        try {
        	 
        	String nombrePK = idColumnName();
        	int valorPK = Integer.parseInt(
        					this.getClass().getField(nombrePK).get(this)
        				.toString());
        	
        	
            return valorPK;  
            
        } catch (NoSuchFieldException e) {
        	System.out.println(e);
        	return -1;
        } catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return -1;	 
	}
	
	public void guardar() {
		
		Database db = new Database();
		if(id()==0) { //no existe el registro, hay que crearlo con INSERT
			Debug.print("INSERT");
			db.insert(this);
		}else { //ya existe el registro, lo actualizamos con UPDATE
			Debug.print("UPDATE");
			db.update(this);
		}

		db.close();
	}
	
	 
	
	public int eliminar() {
		String nombrePK = idColumnName();
		String nombreTabla = tableName();
		
		Debug.print("tb="+nombreTabla +"pk=" + nombrePK + "id="+this.id());
		
		Database db = new Database();
		
		int cantEliminados = db.table(nombreTabla).where(nombrePK+"=?", this.id()).delete().getRowsAffected();
		db.close();
		
		
		return cantEliminados;
		
		
	}
	
	
	
}
