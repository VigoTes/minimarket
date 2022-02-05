package com.minimarket.demo.model;

import java.util.List;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import javax.persistence.Table;

import com.dieselpoint.norm.Database;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
@Table(name = "ingreso_almacen")
public class IngresoAlmacen extends ModeloGuardable{
	public static String tableName="ingreso_almacen";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codIngresoAlmacen;
	public static String idColumnName="codIngresoAlmacen";

	

	@Column(name="codPersonalQueIngreso")
	public int codPersonalQueIngreso;
    @Column(name="costoTotal")
	public float costoTotal; 
	@Column(name="fechaHoraIngreso")
	@Temporal(TemporalType.TIME)
    public LocalDateTime fechaHoraIngreso;
	
	public IngresoAlmacen() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static IngresoAlmacen findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<IngresoAlmacen> resultados = 
				db.where(idColumnName+"=?", id).results(IngresoAlmacen.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
    

    
	
	
	
}