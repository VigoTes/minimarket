package librerias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


	
}
