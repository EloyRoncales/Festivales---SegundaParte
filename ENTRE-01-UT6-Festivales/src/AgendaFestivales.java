
import java.util.*;


/**
 * Esta clase guarda una agenda con los festivales programados
 * en una serie de meses
 *
 * La agenda guardalos festivales en una colecci�n map
 * La clave del map es el mes (un enumerado festivales.modelo.Mes)
 * Cada mes tiene asociados en una colecci�n ArrayList
 * los festivales  de ese mes
 *
 * Solo aparecen los meses que incluyen alg�n festival
 *
 * Las claves se recuperan en orden alfab�ico
 *
 */
public class AgendaFestivales {
    private TreeMap<Mes, ArrayList<Festival>> agenda;
    
    public AgendaFestivales() {
        this.agenda = new TreeMap<>();
    }

    /**
     * a�ade un nuevo festival a la agenda
     *
     * Si la clave (el mes en el que se celebra el festival)
     * no existe en la agenda se crear� una nueva entrada
     * con dicha clave y la colecci�n formada por ese �nico festival
     *
     * Si la clave (el mes) ya existe se a�ade el nuevo festival
     * a la lista de festivales que ya existe ese ms
     * insert�ndolo de forma que quede ordenado por nombre de festival.
     * Para este segundo caso usa el m�todo de ayuda
     * obtenerPosicionDeInsercion()
     *
     */
    public void addFestival(Festival festival) {
        Mes mes = festival.getMes();
        ArrayList<Festival> festivales = agenda.get(mes);
        if (festivales == null) {
            festivales = new ArrayList<>();
            agenda.put(mes, festivales);
        }
        int posicion = obtenerPosicionDeInsercion(festivales, festival);
        festivales.add(posicion, festival);
    }

    /**
     *
     * @param festivales una lista de festivales
     * @param festival
     * @return la posici�n en la que deber�a ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales,
                                           Festival festival) {
        int inicio = 0;
        int fin = festivales.size() - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            Festival festivalMedio = festivales.get(medio);
            int comparacion = festival.getNombre().compareTo(festivalMedio.getNombre());
            if (comparacion == 0) {
                return medio;
            } else if (comparacion < 0) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }
        return inicio;
        
    }

    /**
     * Representaci�n textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas para recorrer el map
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Mes, ArrayList<Festival>> entry : agenda.entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            for (Festival festival : entry.getValue()) {
                sb.append(festival.getNombre());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve -1
     */
    public int festivalesEnMes(Mes mes) {
        ArrayList<Festival> festivales = agenda.get(mes);
        if (festivales == null) {
            return 0;
        } else {
            return festivales.size();
        }
    }

    /**
     * Se trata de agrupar todos los festivales de la agenda
     * por estilo.
     * Cada estilo que aparece en la agenda tiene asociada una colecci�n
     * que es el conjunto de nombres de festivales que pertenecen a ese estilo
     * Importa el orden de los nombres en el conjunto
     *
     * Identifica el tipo exacto del valor de retorno
     */
    public Map<Estilo, Set<String>> festivalesPorEstilo() {
        
    }

    /**
     * Se cancelan todos los festivales organizados en alguno de los
     * lugares que indica el conjunto en el mes indicado. Los festivales
     * concluidos o que no empezados no se tienen en cuenta
     * Hay que borrarlos de la agenda
     * Si el mes no existe se devuelve -1
     *
     * Si al borrar de un mes los festivales el mes queda con 0 festivales
     * se borra la entrada completa del map
     */
    public int cancelarFestivales(HashSet<String> lugares, Mes mes) {
        ArrayList<Festival> festivales = agenda.get(mes);
        if (festivales == null) {
            return -1;
        }
        int contador = 0;
        Iterator<Festival> iterator = festivales.iterator();
        while (iterator.hasNext()) {
            Festival festival = iterator.next();
            if (lugares.contains(festival.getLugar())) {
                iterator.remove();
                contador++;
            }
        }
        if (festivales.isEmpty()) {
            agenda.remove(mes);
        }
        return contador;
    }
}
