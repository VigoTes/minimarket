package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "productos")
public class Producto extends ModeloGuardable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	private static String idColumnName="idProducto";
	
	@Column(name="descripcion")
	private String descripcion;
	
	
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
 
		// 
	}
	
	public static Producto findOrFail(int id) {
		Database db = new Database();
		List<Producto> resultados = 
				db.where(idColumnName+"=?", id).results(Producto.class);
		return resultados.get(0);
		
	}
	
	
}