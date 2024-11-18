package aed;

import java.util.Comparator;

public class Traslado {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    /** 
     * Comparador para ordenar traslados por ganancia neta de forma ascendente.
     * Si la ganancia neta es igual, ordena por ID de forma descendente.
     * 
     * @return Comparator<Traslado> El comparador para ordenar traslados por ganancia y ID.
     */
    public static final Comparator<Traslado> porGanancia() {
        return (t1, t2) -> { 
            if (t1.gananciaNeta != t2.gananciaNeta){ return Integer.compare (t1.gananciaNeta,t2.gananciaNeta); }
            else{ return Integer.compare(t2.id,t1.id); }
        };
    }

    /** 
     * Comparador para ordenar traslados por antigüedad (timestamp) de forma descendente.
     * 
     * @return Comparator<Traslado> El comparador para ordenar traslados por antigüedad.
     */
    public static final Comparator<Traslado> porAntiguedad() { return (t1, t2) -> 
        { return Integer.compare(t2.timestamp, t1.timestamp); };
    }

}
