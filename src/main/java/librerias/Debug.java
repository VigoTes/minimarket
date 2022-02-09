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


    //le mandamos algo tipo DIEGO VIGO y te formatea en Diego Vigo
    public static String primeraMayuscula(String cadena){
 
        String[] vectorPalabras = cadena.split("\\s+");
        cadena = "";
        for (int i = 0; i < vectorPalabras.length; i++) {
             
            String palabra = vectorPalabras[i];

            palabra = palabra.toLowerCase();
            String firstLtr = palabra.substring(0, 1);
            String restLtrs = palabra.substring(1, palabra.length());
        
            firstLtr = firstLtr.toUpperCase();
            palabra = firstLtr + restLtrs;
            
            cadena += palabra + " ";
        }
        cadena = cadena.substring(0, cadena.length() - 1);//quitamos el ultimo espacio añadido
        return cadena;


    }
}
