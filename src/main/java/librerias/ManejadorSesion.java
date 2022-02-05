package librerias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.minimarket.demo.model.Personal;

public class ManejadorSesion {

	
	//AÑADE A LA SESION EL MENSAJE
	public static void addMsj(HttpServletRequest request,String msj) {
			
			request.getSession().setAttribute("msj", msj);
			
		}
		
	//OBTIENE EL MENSAJE DE LA SESION Y LO DESTRUYE, RETORNANDOLO
	public static String getMsj(HttpSession session) {
		
		String msj = (String) session.getAttribute("msj");
		Debug.print(msj);
		session.setAttribute("msj", null);
		return msj;
		
	}

    public static Personal getLogeado(HttpSession session) throws Exception{
        String codPersonal = (String) session.getAttribute("codPersonalLogeado");
        return Personal.findOrFail(codPersonal);

    }


	
}
