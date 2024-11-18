package aed;

import java.util.Comparator;

public class Ciudad {
    int id;
    int ganancia;
    int perdida;

    public Ciudad(int id){
        this.id = id;
        this.ganancia = 0;
        this.perdida = 0;
    }

    /** 
     * Calcula el superávit de la ciudad como la diferencia entre la ganancia y la pérdida. 
     * @return int El superávit de la ciudad (ganancia - pérdida).
     */
    public int superavit() { return this.ganancia - this.perdida;}

    /** 
     * Comparador que ordena las ciudades por su superávit.
     * Primero compara el superávit de forma ascendente, y si son iguales, compara el ID de las ciudades de manera descendente.
     * 
     * @return Comparator<Ciudad> El comparador para ordenar ciudades por superávit y ID.
     */
    public static final Comparator<Ciudad> porSuperavit() { 
        return (c1, c2) -> {
            if (c1.superavit() != c2.superavit()){ return Integer.compare (c1.superavit(), c2.superavit()); }
            else{ return Integer.compare(c2.id, c1.id); }
        };
    }

}