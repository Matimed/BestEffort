package aed;

import java.util.ArrayList;
import java.util.Comparator;

import aed.ColaPrioridad.Heap;
import aed.ColaPrioridad.HeapEnlazado;

public class BestEffort {
    private ArrayList<Integer> posCiudadesEnHeap; //no sé si esto es array de integers. necesita un puntero al nodo de cada ciudad, eso sí
    private ArrayList<Integer> ciudadesMayorGanancia;
    private ArrayList<Integer> ciudadesMayorPerdida;
    private Integer gananciasTotales;
    private Integer trasladosTotales;

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
    
    private HeapEnlazado<Traslado> heapRedituable = new HeapEnlazado<>(comparadorMasRedituable);
    private HeapEnlazado<Traslado> heapAntiguo = new HeapEnlazado<>(comparadorMasAntiguo);

    private Comparator<Ciudades> comparadorSuperavit = (t1, t2) -> {
        return Integer.compare(t2.superavit, t1.superavit); 
    };

    private Heap<Ciudades> heapCiudades = new Heap<>(comparadorSuperavit); 

    private void procesarDespacho(Traslado traslado){

        actualizarCiudad(traslado.origen, traslado.gananciaNeta);

        actualizarCiudad(traslado.destino, -traslado.gananciaNeta);
    }

    private void actualizarCiudad(int idCiudad, int gananciaNeta){
        
    }

    public BestEffort(int cantCiudades, Traslado[] traslados){
        //vamos a decir que cantCiudades == C, y traslados == T, ya que esta función establece las ciudades y las estructuras de los traslados del sistema
        ArrayList<Integer> citys = new ArrayList<Integer>();
        Integer i = 0;
        for (i=0; i<cantCiudades; i++) //O(C)
        {
            citys.add(i); //O(1)
        }
        posCiudadesEnHeap=citys; //O(1)
        ciudadesMayorGanancia=citys; //O(1)
        ciudadesMayorPerdida=citys; //O(1)
    }

    public void registrarTraslados(Traslado[] traslados){

        heapRedituable.inicialiar(heapAntiguo);
        
        for (int i = 0; i < traslados.length; i++) {
            Traslado traslado = traslados[i];
            heapRedituable.apilar(traslado);
        }

    }

    public int[] despacharMasRedituables(int n){
        int [] despachados = new int[n];
        int index = 0;

        while(n > 0 && heapRedituable.getSize()>0){
            Traslado traslado = heapRedituable.desapilarMax();
            despachados[index ++] = traslado.id;

            procesarDespacho(traslado);
            n--;
        }
        return despachados;
    }

    public int[] despacharMasAntiguos(int n){
        int [] despachados = new int[n];
        int index = 0;

        while(n > 0 && heapAntiguo.getSize()>0){
            Traslado traslado = heapAntiguo.desapilarMax();
            despachados[index ++] = traslado.id;

            procesarDespacho(traslado);
            n--;
        }
        return despachados;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0; //O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesMayorGanancia; //dado que esta instrucción solo devuelve un arraylist, es O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesMayorPerdida; //dado que esta instrucción solo devuelve un arraylist, es O(1)
    }

    public int gananciaPromedioPorTraslado(){
        if (trasladosTotales==0) //O(1)
        {
            return 0; //O(1)
        }
        else
        {
            return (int) Math.floor(gananciasTotales/trasladosTotales);//dado que esta instrucción solo obtiene la parte entera de una división de enteros, es O(1)
        }
        //notar que, como cada traslado involucra una ganancia neta >= 1, la ganancia promedio siempre será al menos 1 si se despachó al menos un traslado
    }   
}