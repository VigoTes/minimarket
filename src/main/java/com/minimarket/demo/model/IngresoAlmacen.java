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
	@Column(name="comentario")
	public String comentario;
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

	public List<Lote> obtenerLotes() throws Exception{
		Database db = new Database();
		List<Lote> listaLote = db.where("codIngresoAlmacen=?", this.codIngresoAlmacen).results(Lote.class);
		db.close();
		if(listaLote.size()==0) {
			throw new Exception("No existen lotes de ingreso "+this.codIngresoAlmacen);
		}

		return listaLote;
	}
    

	public PuntoVenta obtenerPuntoVenta() throws Exception{
		return PuntoVenta.findOrFail(this.obtenerLotes().get(0).codPunto+"");
	}

    public String listaStringLotes() throws Exception {
		String cadena="";
		for (Lote lote: this.obtenerLotes()) cadena=cadena+lote.codigoLegible+" - ";

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

	public String obtenerFechaIngresoFormateada() throws Exception{
		String str = this.obtenerFechaHoraIngresoFormateada();
        String[] newStr = str.split("\\s+");
    	return newStr[0];
	}


	public String obtenerCostoTotalFormateado() throws Exception{
		DecimalFormat formato = new DecimalFormat("S/'.' #,###.###");
		return formato.format(this.costoTotal);
	}
	
	public static List<IngresoAlmacen> filtrarIngresos(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) throws Exception{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String inicio=fechaHoraInicio.format(dtf);
		String fin=fechaHoraFin.format(dtf);

		List<IngresoAlmacen> listaIngresos;
		Database db = new Database();
		if(fechaHoraInicio.isBefore(fechaHoraFin) && !inicio.equals(fin)){
			listaIngresos = db.where("fechaHoraIngreso>? and fechaHoraIngreso<?", inicio, fin).results(IngresoAlmacen.class);
		}
		else{
			listaIngresos = db.results(IngresoAlmacen.class);
		}
		db.close();
		return listaIngresos;
	}

	
	public static String verificarFiltro(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) throws Exception{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String inicio=fechaHoraInicio.format(dtf);
		String fin=fechaHoraFin.format(dtf);

		if(fechaHoraInicio.isBefore(fechaHoraFin) && !inicio.equals(fin)){
			return "se filtra";
		}
		return "no se filtra";
	}
	

}