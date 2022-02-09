package com.minimarket.demo.model;

import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

 
@Table(name = "detalle_venta")
public class DetalleVenta extends ModeloGuardable{
	public static String tableName="detalle_venta";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codDetalleVenta;
	public static String idColumnName="codDetalleVenta";

	
 
	
	@Column(name="codVenta")
	public int codVenta;
	@Column(name="codProducto")
	public int codProducto;
	@Column(name="cantidad")
	public int cantidad;
    @Column(name="precioUnitario")
	public float precioUnitario;
	@Column(name="total")
	public float total;

	
	
	public DetalleVenta() {
		super();
	}
 
	DecimalFormat formato = new DecimalFormat("S/ #,###.00");
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static DetalleVenta findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<DetalleVenta> resultados = 
				db.where(idColumnName+"=?", id).results(DetalleVenta.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}

	public Producto obtenerProducto() throws Exception{
		Database db = new Database();
		
		List<Producto> resultados = db.where("codProducto=?", this.codProducto).results(Producto.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe producto "+this.codProducto);
		}
		
		return resultados.get(0);
	}
	

	public String obtenerPrecioUnitarioFormateado() throws Exception{
		return formato.format(this.precioUnitario);
	}

	public String obtenerTotalFormateado() throws Exception{
		return formato.format(this.total);
	}
	
	
	
}
