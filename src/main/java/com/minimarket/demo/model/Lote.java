package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Formatter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dieselpoint.norm.Database;

 
@Table(name = "lote")
public class Lote extends ModeloGuardable{
	public static String tableName="lote";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codLote;
	public static String idColumnName="codLote";

	

	@Column(name="codigoLegible")
	public String codigoLegible;
    @Column(name="codProducto")
	public int codProducto;
    @Column(name="codProveedor")
	public int codProveedor; 
	@Column(name="codPunto")
	public int codPunto;
	@Column(name="stock")
	public int stock;
    @Column(name="stockIngresado")
	public int stockIngresado;
    @Column(name="codIngresoAlmacen")
	public int codIngresoAlmacen;
    @Column(name="costoCompraLote")
	public float costoCompraLote;


    @Column(name="fechaVencimiento")
	@Temporal(TemporalType.DATE)
    public Date fechaVencimiento;


	public Lote() {
		super();
	}
 
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Lote findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Lote> resultados = 
				db.where(idColumnName+"=?", id).results(Lote.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}
    
    public IngresoAlmacen gIngreso() throws Exception{
        return IngresoAlmacen.findOrFail(String.valueOf(this.codIngresoAlmacen));
    }
    
	public String obtenerCodigoLegible() throws Exception{
		String codigoLegible;
		Formatter numLote = new Formatter();
		numLote.format("%04d",this.codLote);
		codigoLegible="LOT"+numLote+"-";
		numLote.close();
		Formatter numPunto = new Formatter();
		numPunto.format("%03d",this.codPunto);
		codigoLegible=codigoLegible+numPunto;
		numPunto.close();

		return codigoLegible;
	}

	public Producto obtenerProducto() throws Exception{
		
        return Producto.findOrFail(String.valueOf(this.codProducto));
 
	}

    public PuntoVenta gPunto() throws Exception {
        return PuntoVenta.findOrFail(String.valueOf(this.codPunto));
    }

	public Proveedor obtenerProveedor() throws Exception{
        return Proveedor.findOrFail(String.valueOf(this.codProveedor));
 
	}

	public String obtenerCostoFormateado() throws Exception{
		DecimalFormat formato = new DecimalFormat("S/'.' #,###.###");
		return formato.format(this.costoCompraLote);
	}

	public String obtenerFechaVencimientoFormateada() throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	return format.format(this.fechaVencimiento);
	}
	

    public static Lote getLoteAVender(int codProducto, int codPunto){
        Database db = new Database();
        //QUE APAREZCAN ARRIBA LAS QUE TIENEN MENOR FECHA VENCIMIENTO (se vencerán pronto)
        List<Lote> lotes = db.where("codProducto=? and codPunto=?",codProducto,codPunto)
            .orderBy("fechaVencimiento ASC").results(Lote.class);
        db.close();
        Lote lote = lotes.get(0);
        return lote;

    }
}
