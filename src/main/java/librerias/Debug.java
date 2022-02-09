package librerias;

public class Debug {

	
	
	public static void print(String string) {
		
		System.out.println("----------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------");
		System.out.println("");
		System.out.println(string);
		System.out.println("");
		System.out.println("----------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------");
		 
		
		
		
	}

    public static String rellenarConCeros(int numero,int tamañoFinal){
    
        String numeroEnString = String.valueOf(numero);
        String cadenaCeros = "";
        int cantidadAAñadir = tamañoFinal - numeroEnString.length();

        for(int i=0;i<cantidadAAñadir;i++)
            cadenaCeros +="0";

        return cadenaCeros + numeroEnString;



    }
}
