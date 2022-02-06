package com.minimarket.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.minimarket.demo.model.*;
import librerias.Debug;
import librerias.ManejadorSesion;

public class EmpleadoLogeadoInteceptor implements HandlerInterceptor{

	

    /* Validar sesion */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
 
        
        if (!ManejadorSesion.hayPersonalLogeado(request.getSession())) {
            response.sendRedirect("/login");
            return false;
        }
       
        return true;

    }


    //Añadir variables a la vista
    @Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler, ModelAndView modelAndView) 
                                                        throws Exception {

        //Debug.print("ESTAMOS LLEGANDO AL POST");
        if(ManejadorSesion.hayPersonalLogeado(request.getSession())){
            Personal pLogeado = ManejadorSesion.getPersonalLogeado(request.getSession());
            if(modelAndView == null)
                modelAndView = new ModelAndView();

            modelAndView.addObject("personalLogeado", pLogeado);
            
        }
        
    }

    @Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {
 
    }
	


}
