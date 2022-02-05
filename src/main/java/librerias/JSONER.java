package librerias;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONER {

	
	public static String toJson(Object lista) {
		

		ObjectMapper m = new ObjectMapper();
        try {
        	
			return m.writeValueAsString(lista);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return "ERROR";
	}
	
}
