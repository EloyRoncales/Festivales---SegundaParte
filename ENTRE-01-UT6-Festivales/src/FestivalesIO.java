

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 */
public class FestivalesIO {

    
    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);
                
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        
    }

    /**
     * se parsea la línea extrayendo sus datos y creando y
     * devolviendo un objeto Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        String[] datosFes = lineaFestival.split(":");

        String nombre = datosFes[0].trim();
        String lugar = datosFes[1].trim().toUpperCase();
        LocalDate fechaInicio = LocalDate.parse(datosFes[2].trim());
        DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int duracion = Integer.parseInt(datosFes[3].trim());
        HashSet<Estilo> estilos = new HashSet<>();
            for(int i = 4; i < datosFes.length; i++){
                estilos.add(Estilo.valueOf(datosFes[i].trim().toUpperCase()));
            }

        return new Festival(nombre, lugar, fechaInicio, duracion, estilos);
    }
    
   
    
    
}
