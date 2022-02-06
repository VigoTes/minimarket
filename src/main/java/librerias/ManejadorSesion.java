package librerias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.minimarket.demo.model.Personal;
import com.minimarket.demo.model.Usuario;

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

    public static Personal getPersonalLogeado(HttpSession session) throws Exception{
        
        String codUsuario = String.valueOf(session.getAttribute("codUsuario"));
        Usuario user = Usuario.findOrFail(codUsuario);
        
        return  user.gPersonal();

    }

    public static boolean hayPersonalLogeado(HttpSession session){
        return session.getAttribute("codUsuario")!=null;

    }

    public static void iniciarSesion(HttpSession session,int codUsuario){
        session.setAttribute("codUsuario", codUsuario);
        
    }

	public static void cerrarSesion(HttpSession session){
        session.setAttribute("codUsuario", null);
    }

    public static String getAuthID(HttpSession session){
        return  String.valueOf(session.getAttribute("codUsuario"));
    }

    public static String n(){
        return "hola";

    }
}
