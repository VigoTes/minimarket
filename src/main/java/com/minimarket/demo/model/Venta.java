package com.minimarket.demo.model;


import java.text.DecimalFormat;
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

    @Column(name="codTipoCDP")
	public int codTipoCDP;

    @Column(name="importeTotal")
    public float importeTotal;
    @Column(name="igv")
    public float igv;
    
    @Column(name="importeBruto")
    public float importeBruto;
    
   
 
	@Column(name="codCliente")
	public int codCliente;

	
    @Column(name="fechaHora")
	@Temporal(TemporalType.TIME)
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

	public List<DetalleVenta> obtenerDetalles() throws Exception{
		Database db = new Database();
		
		List<DetalleVenta> resultados = 
				db.where("codVenta=?", this.codVenta).results(DetalleVenta.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No tiene detalles la venta "+this.codVenta);
		}
		
		return resultados;
	}
	
	public Cliente obtenerCliente() throws Exception{
		Database db = new Database();
		
		List<Cliente> resultados = db.where("codCliente=?", this.codCliente).results(Cliente.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el client "+this.codCliente);
		}
		
		return resultados.get(0);
	}

	public TipoCDP obtenerTipoCDP() throws Exception{
		Database db = new Database();
		
		List<TipoCDP> resultados = db.where("codTipoCDP=?", this.codTipoCDP).results(TipoCDP.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el tipo de CDP "+this.codTipoCDP);
		}
		
		return resultados.get(0);
	}
	  

    public Cliente gCliente() throws Exception{
        return Cliente.findOrFail(String.valueOf(this.codCliente));
    }
    public TipoCDP gTipoCDP() throws Exception{
        return TipoCDP.findOrFail(String.valueOf(this.codTipoCDP));
    }


    public String gClienteDescripcion() throws Exception{
        Cliente cliente = this.gCliente();
         
        if(cliente.codTipoCliente==1)//nat
            return cliente.gNombreCompleto();

        else 
            return cliente.razonSocial;

    }

	public String obtenerImporteTotalFormateado() throws Exception{
		DecimalFormat formato = new DecimalFormat("S/'.' #,###.###");

		return formato.format(this.importeTotal);
	}

	

}
