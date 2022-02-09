package librerias;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.InputStreamReader;
 

public class MaracsoftBot {
    

    static String tokenBot = "1856544579:AAFFp1cFdLpwTuNZlZDIa7tJu3XDLi27bk4";

    //IDS de telegram
    static String idCanalLogsProduccion = "-539429585";
    static String idUsuarioDiego = "1448599566";
    static String idCanalLogsPruebas = "-467002205";
    static String idCanalBackups = "-465250581";
    
    
    private static String getURLBase(){
        return "https://api.telegram.org/bot" + MaracsoftBot.tokenBot;

    }

    
    public static String getURLArchivos(){
        return MaracsoftBot.getURLBase() + "/sendDocument"; 
    }

    public static String getURLMensajes(){
        return MaracsoftBot.getURLBase() + "/sendMessage"; 
    }

    /* Envia un mensaje al canal de maracsoft */
    public static String enviarMensaje(String msg){
        
        String idDestino = idCanalLogsPruebas;

        String link = MaracsoftBot.getURLMensajes() +  "?chat_id="+idDestino+"&parse_mode=HTML&text=" + msg;
        Debug.print("El link consultado es:" +link);
        return MaracsoftBot.consultaGET(link);
    }
    
    private static String consultaGET(String link){
        String resultado ="";

        URL objetoURL;
        try {
            
            
            // Creando un objeto objetoURL
            objetoURL = new URL(link);
            // Realizando la petición GET
            URLConnection con = objetoURL.openConnection();
            
            // Leyendo el resultado
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
 
            String linea;
            while ((linea = in.readLine()) != null) {
                resultado += linea;
            }

            return resultado;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
 
    }






    static String tokenParaAPISunat = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hcmFjc29mdEBnbWFpbC5jb20ifQ.n5ullrY3C430Q8IHYmxk38bidOi7sLDuB2n_ULc63F0";

    public static String ConsultarAPISunatDNI(String dni){
        try {
            
            String linkConsulta = "https://dniruc.apisperu.com/api/v1/dni/"+ dni+"?token=" + tokenParaAPISunat;
            
            String respuestaGET = MaracsoftBot.consultaGET(linkConsulta);
            
            if( respuestaGET.contains("\"success\":false")){
                return "0";
            }

            //LLEGA ASI
            // {"dni":"71208489","nombres":"DIEGO ERNESTO","apellidoPaterno":"VIGO","apellidoMaterno":"BRIONES","codVerifica":"3"}

            JSONObject objeto = new JSONObject(respuestaGET);
             

            JSONObject nuevo = new JSONObject();
            nuevo.put("nombres"  ,  Debug.primeraMayuscula(objeto.get("nombres").toString() ) );
            nuevo.put("apellidos", 
                        Debug.primeraMayuscula(  
                            objeto.get("apellidoPaterno").toString()
                            + " " + 
                            objeto.get("apellidoMaterno").toString() 
                        ));


            return nuevo.toString();

        } catch (Exception ex) {
            Debug.print(ex.toString());
            return "0";
        }

    }







}
