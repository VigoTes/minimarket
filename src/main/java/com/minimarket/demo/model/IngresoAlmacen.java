package com.minimarket.demo.model;

import java.util.List;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import javax.persistence.Table;

import com.dieselpoint.norm.Database;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.format.DateTimeFormatter;
 
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
    

    public String listaStringLotes() throws Exception {
		Database db = new Database();
		List<Lote> listaLote = db.where("codIngresoAlmacen=?", this.codIngresoAlmacen).results(Lote.class);
		db.close();
		if(listaLote.size()==0) {
			throw new Exception("No existen lotes de ingreso "+this.codIngresoAlmacen);
		}
		String cadena="";
		for (Lote lote: listaLote) cadena=cadena+lote.codigoLegible+" - ";

		int caracteresMaximos=40;
		if(cadena.length()<caracteresMaximos){
			cadena=cadena.substring(0, cadena.length()-2);
		}else{
			cadena=cadena.substring(0, caracteresMaximos)+" ...";
		}

        
		return cadena;	
	}
	
	public String obtenerNombresApellidosResponsable() throws Exception{
		Database db = new Database();
		
		List<Personal> listaResponsables = db.where("codPersonal=?", this.codPersonalQueIngreso).results(Personal.class);
		db.close();
		if(listaResponsables.size()==0) {
			throw new Exception("No hay responsable de ingreso "+this.codIngresoAlmacen);
		}
		
		return listaResponsables.get(0).nombres+' '+listaResponsables.get(0).apellidos;	
	}

	public String obtenerFechaHoraIngresoFormateada() throws Exception{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    	return this.fechaHoraIngreso.format(dtf);
	}

	public String obtenerCostoTotalFormateado() throws Exception{
		DecimalFormat formato = new DecimalFormat("S/'.' #,###.###");
		return formato.format(this.costoTotal);
	}
	
	
}