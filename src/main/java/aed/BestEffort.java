package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private ArrayList<Integer> ciudades;
    private ArrayList<Integer> ciudadesMayorGanancia;
    private ArrayList<Integer> ciudadesMayorPerdida;
    private Integer gananciasTotales;
    private Integer trasladosTotales;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        // Implementar
    }

    private Comparator <Traslado> comparadorMasRedituable =(t1, t2) ->{
        if (t1.gananciaNeta != t2.gananciaNeta){
            return Integer.compare (t2.gananciaNeta,t1.gananciaNeta);
        }else{
            return Integer.compare(t1.id,t2.id);
        }
    };

    private Comparator<Traslado> comparadorMasAntiguo = (t1, t2) -> {
        return Integer.compare(t2.timestamp, t1.timestamp); 
    };
    
    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesMayorGanancia; //dado que esta instrucción solo devuelve un arraylist, es O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesMayorPerdida; //dado que esta instrucción solo devuelve un arraylist, es O(1)
    }

    public int gananciaPromedioPorTraslado(){
        if (trasladosTotales==0)
        {
            return 0;
        }
        else
        {
            return (int) Math.floor(gananciasTotales/trasladosTotales);//dado que esta instrucción solo obtiene la parte entera de una división de enteros, es O(1)
        }
        //notar que, como cada traslado involucra una ganancia neta >= 1, la ganancia promedio siempre será al menos 1 si se despachó al menos un traslado
    }   
}