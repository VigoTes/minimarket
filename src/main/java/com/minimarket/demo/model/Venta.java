package com.minimarket.demo.model;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dieselpoint.norm.Database;

 
@Table(name = "venta")
public class Venta extends ModeloGuardable{
	public static String tableName="venta";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codVenta;
	public static String idColumnName="codVenta";

	
	
	
	@Column(name="codPunto")
	public int codPunto;
    @Column(name="codPersonal")
	public int codPersonal;

    @Column(name="importeTotal")
    public float importeTotal;
    @Column(name="igv")
    public float igv;
    
    @Column(name="importeBruto")
    public float importeBruto;
    
    

 
	@Column(name="dni")
	public String dni;

	
    @Column(name="fechaHora")
	@Temporal(TemporalType.DATE)
    public LocalDateTime fechaHora;

	@Column(name="codigoLegible")
	public String codigoLegible; 
	
	
	public Venta() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Venta findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Venta> resultados = 
				db.where(idColumnName+"=?", id).results(Venta.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
	
	  

}
