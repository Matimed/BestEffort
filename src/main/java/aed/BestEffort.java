package aed;

import java.util.ArrayList;
import java.util.Comparator;

import aed.ColaPrioridad.HeapEnlazado;

public class BestEffort {
    private ArrayList<Integer> ciudades; //no sé si esto es array de integers. necesita un puntero al nodo de cada ciudad, eso sí
    private ArrayList<Integer> ciudadesMayorGanancia;
    private ArrayList<Integer> ciudadesMayorPerdida;
    private Integer gananciasTotales;
    private Integer trasladosTotales;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        //vamos a decir que cantCiudades == C, y traslados == T, ya que esta función establece las ciudades y las estructuras de los traslados del sistema
        ArrayList<Integer> citys = new ArrayList<Integer>();
        Integer i = 0;
        for (i=0; i<cantCiudades; i++) //O(C)
        {
            citys.add(i); //O(1)
        }
        ciudades=citys; //O(1)
        ciudadesMayorGanancia=citys; //O(1)
        ciudadesMayorPerdida=citys; //O(1)
    }

    private Comparator<Traslado> comparadorMasRedituable =(t1, t2) ->{
        if (t1.gananciaNeta != t2.gananciaNeta){
            return Integer.compare (t2.gananciaNeta,t1.gananciaNeta); //O(1)
        }else{
            return Integer.compare(t1.id,t2.id); //O(1)
        }
    };

    private Comparator<Traslado> comparadorMasAntiguo = (t1, t2) -> {
        return Integer.compare(t2.timestamp, t1.timestamp); //O(1)
    };
    
    public void registrarTraslados(Traslado[] traslados){
        HeapEnlazado<Traslado> heapRedituable = new HeapEnlazado<>(comparadorMasRedituable);
        HeapEnlazado<Traslado> heapAntiguo = new HeapEnlazado<>(comparadorMasAntiguo);

        heapRedituable.inicialiar(heapAntiguo);
        
        for (int i = 0; i < traslados.length; i++) {
            Traslado traslado = traslados[i];
            heapRedituable.apilar(traslado);
        }

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