package aed;

import java.util.ArrayList;
import java.util.Comparator;

import aed.ColaPrioridad.Heap;
import aed.ColaPrioridad.HeapEnlazado;

public class BestEffort {
    private ArrayList<Integer> posCiudadesEnHeap; //no sé si esto es array de integers. necesita un puntero al nodo de cada ciudad, eso sí
    private ArrayList<Integer> MayoresGanancias;
    private ArrayList<Integer> MayoresPerdidas;
    private Integer gananciasTotales;
    private Integer trasladosTotales;
    private ArrayList<Ciudades> listaCiudades; 

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

    private Comparator<Ciudades> comparadorSuperavit = (c1, c2) -> {
        return Integer.compare(c2.superavit, c1.superavit); 
    };

    private Heap<Ciudades> heapCiudades = new Heap<>(comparadorSuperavit); 

    private void procesarDespacho(Traslado traslado){
        actualizarCiudad(traslado.origen, traslado.gananciaNeta, 0);
        actualizarCiudad(traslado.destino, 0, traslado.gananciaNeta);

        gananciasTotales += traslado.gananciaNeta;
        trasladosTotales++;
        actualizarListasMayorGananciaYPerdida();
    }

    private void actualizarCiudad(int idCiudad, int ganancia, int perdida){
        Ciudades ciudad = listaCiudades.get(idCiudad); // Acceder a la ciudad en la lista
        ciudad.ganancia += ganancia;  // Actualizar ganancia
        ciudad.perdida += perdida;   // Actualizar pérdida
        ciudad.superavit = ciudad.ganancia - ciudad.perdida; // Recalcular superávit
            // Actualizar en el heap
        heapCiudades.eliminar(ciudad);
        heapCiudades.apilar(ciudad);
        actualizarListasMayorGananciaYPerdida();
    }

    private void actualizarListasMayorGananciaYPerdida() {
        MayoresGanancias.sort((id1, id2) -> {
            int ganancia1 = listaCiudades.get(id1).ganancia;
            int ganancia2 = listaCiudades.get(id2).ganancia;
            return Integer.compare(ganancia2, ganancia1); // Orden descendente por ganancia
        });
    
        MayoresPerdidas.sort((id1, id2) -> {
            int perdida1 = listaCiudades.get(id1).perdida;
            int perdida2 = listaCiudades.get(id2).perdida;
            return Integer.compare(perdida2, perdida1); // Orden descendente por pérdida
        });
    }
    

    public BestEffort(int cantCiudades, Traslado[] traslados){
        //vamos a decir que cantCiudades == C, y traslados == T, ya que esta función establece las ciudades y las estructuras de los traslados del sistema
        gananciasTotales = 0;
        trasladosTotales = 0;
        posCiudadesEnHeap = new ArrayList<>(cantCiudades); //O(1)
        MayoresGanancias = new ArrayList<>(cantCiudades); //O(1)
        MayoresPerdidas = new ArrayList<>(cantCiudades); //O(1)
        listaCiudades = new ArrayList<>(cantCiudades);

        for (int i = 0; i < cantCiudades; i++) {
            posCiudadesEnHeap.add(i);
            MayoresGanancias.add(i);
            MayoresPerdidas.add(i);

            Ciudades ciudad = new Ciudades();
            ciudad.idCiudad = i;  // El índice coincide con el ID
            listaCiudades.add(ciudad);
            heapCiudades.apilar(ciudad);
        }
        registrarTraslados(traslados); //no se si esto iría acá
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
        return heapCiudades.consultarMax().idCiudad; // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return MayoresGanancias; //dado que esta instrucción solo devuelve un arraylist, es O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return MayoresPerdidas; //dado que esta instrucción solo devuelve un arraylist, es O(1)
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