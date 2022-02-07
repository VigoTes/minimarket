package com.minimarket.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import com.dieselpoint.norm.Database;

import com.minimarket.demo.model.PuntoVenta;
import com.minimarket.demo.model.TipoPersonal;
 
@Table(name = "personal")
public class Personal extends ModeloGuardable{
	public static String tableName="personal";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int codPersonal;
	public static String idColumnName="codPersonal";

	

	@Column(name="nombres")
	public String nombres;
    @Column(name="apellidos")
	public String apellidos;
	@Column(name="dni")
	public String dni;

    @Column(name="codTipoPersonal")
	public int codTipoPersonal;
	@Column(name="codUsuario")
	public int codUsuario;

    @Column(name="activo")
	public int activo;


	public Personal() {
		super();
	}
 
    public String gNombreCompleto(){

        return this.apellidos  +" " + this.nombres ;
    }
	
	 
	// ESTE CÓDIGO ES FIJO, SOLO SE CAMBIA EL NOMBRE DE LA CLASE EN 3 LUGARES
	public static Personal findOrFail(String id) throws Exception {
		Database db = new Database();
		
		List<Personal> resultados = 
				db.where(idColumnName+"=?", id).results(Personal.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe el objeto con "+idColumnName+"="+id);
		}
		
		return resultados.get(0);	
	}

	public static List<Personal> obtenerPersonalPorTipo(String tipo) throws Exception{// Administrador-Cajero-Supervisor
		Database db = new Database();
		List<TipoPersonal> tiposPersonal = db.where("nombre=?",tipo).results(TipoPersonal.class);
		List<Personal> resultados = db.where("codTipoPersonal=?", tiposPersonal.get(0).codTipoPersonal).results(Personal.class);
		db.close();
		if(resultados.size()==0) {
			throw new Exception("No existe personal de tipo "+tipo);
		}
		
		return resultados;
	}

	public PuntoVenta obtenerPuntoVenta() throws Exception {
		Database db = new Database();
		List<PuntoVenta> puntosVenta = db.where("codPersonalCajero=?",this.codPersonal).results(PuntoVenta.class);
		db.close();

		if(puntosVenta.size()==0) {
			throw new Exception("No ningun punto de venta con el personal "+this.codPersonal);
		}
		return puntosVenta.get(0);
	}


	public String obtenerNombreCompleto() throws Exception{
		return this.nombres+' '+this.apellidos;
	}
    

	public String gColor(){
        if(this.activo==0)
            return "rojo";

        return "";

    }

    public boolean estaActivo(){
        return this.activo==1;
    }

    public TipoPersonal gTipoPersonal() throws Exception{
        return TipoPersonal.findOrFail(   String.valueOf(this.codTipoPersonal));
    }
    public Usuario gUsuario() throws Exception{
        return Usuario.findOrFail(String.valueOf(this.codUsuario));
    }
    
	public String gActivoTexto(){
        if(this.estaActivo())
            return "Habilitado";

        return "Deshabilitado";
    }
	
}
