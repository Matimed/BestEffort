package aed;

import java.util.ArrayList;
import aed.ColaPrioridad.Heap;
import aed.ColaPrioridad.HeapEnlazado;
import aed.ColaPrioridad.AbstractHeap;

public class BestEffort {
    private HeapEnlazado<Traslado> tAntiguedad = new HeapEnlazado<Traslado>(Traslado.porAntiguedad());
    private HeapEnlazado<Traslado> tGanancias = new HeapEnlazado<Traslado>(Traslado.porGanancia());
    private Heap<Ciudad> cSuperavit = new Heap<Ciudad>(Ciudad.porSuperavit());
    private ArrayList<AbstractHeap<Ciudad>.Nodo> ciudades;
    private ArrayList<Integer> cMayorGanancia;
    private ArrayList<Integer> cMayorPerdida;
    private int gananciasTotales;
    private int despachosTotales;

    /** 
     * Constructor de la clase BestEffort.
     * Inicializa las ciudades, los traslados y otras variables necesarias para el funcionamiento del sistema.
     * 
     * Complejidad: O(|C| + |T|)
     * Notar que |C| representa la cantidad de ciudades y |T| representa la cantidad de traslados.
     */
    public BestEffort(int cantCiudades, Traslado[] traslados){       
        // O(1): Inicialización de variables
        this.gananciasTotales = 0;
        this.despachosTotales = 0;
        this.cMayorGanancia = new ArrayList<Integer>();
        this.cMayorPerdida = new ArrayList<Integer>();
        Ciudad[] ciudades = new Ciudad[cantCiudades];
        
        // O(|C|): Creación de las ciudades
        for (int i = 0; i < cantCiudades; i++) {
            Ciudad ciudad = new Ciudad(i);
            this.cMayorGanancia.add(i);
            this.cMayorPerdida.add(i);
            ciudades[i] = ciudad;
        }

        // O(|C|): Inicialización del conjunto de ciudades
        this.ciudades = cSuperavit.inicialiar(ciudades);
        
        // O(|T|): Inicialización de los traslados con antigüedad y enlace con ganancias
        this.tAntiguedad.inicialiar(this.tGanancias, traslados);
    }

    /**
     * Registra los traslados en la estructura correspondiente.
     * 
     * Complejidad: O(|traslados| * log |T|)
     * Donde |traslados| son los traslados a agregar y 
     * |T| son los traslados que ya están almacenados en el sistema
     */
    public void registrarTraslados(Traslado[] traslados){
        // O(|traslados|): Itera sobre todos los traslados a ingresar
        for (Traslado t : traslados) { tAntiguedad.apilar(t); } // O(log |T|): Inserción de cada nuevo traslado en el heap de antigüedad
    }

    /**
     * Despacha los n traslados más redituables.
     * 
     * Complejidad: O(n * (log |C| + log |T|))
     * Donde |C| es la cantidad de ciudades y |T| es la cantidad de traslados.
     */
    public int[] despacharMasRedituables(int n){ return despacharDesde(n, this.tGanancias); }

    /**
     * Despacha los n traslados más antiguos.
     * 
     * Complejidad: O(n * (log |C| + log |T|))
     * Donde |C| es la cantidad de ciudades y |T| es la cantidad de traslados.
     */
    public int[] despacharMasAntiguos(int n){ return this.despacharDesde(n, this.tAntiguedad); }
    
    /**
     * Obtiene la ciudad con mayor superávit.
     * 
     * Complejidad: O(1)
     */
    public int ciudadConMayorSuperavit() { return cSuperavit.consultarMax().id; }

    /**
     * Obtiene las ciudades con mayor ganancia.
     * 
     * Complejidad: O(1)
     */
    public ArrayList<Integer> ciudadesConMayorGanancia() { return this.cMayorGanancia;} 

    /**
     * Obtiene las ciudades con mayor pérdida.
     * 
     * Complejidad: O(1)
     */
    public ArrayList<Integer> ciudadesConMayorPerdida() {return this.cMayorPerdida;}
    
    /**
     * Calcula la ganancia promedio por traslado.
     * 
     * Complejidad: O(1)
     */
    public int gananciaPromedioPorTraslado(){
        if (despachosTotales==0) {return 0; }
        return (int) Math.floor(gananciasTotales/despachosTotales); 
    }  
    
    /**
     * Despacha los traslados desde un heap dado, procesando los traslados de acuerdo a su orden.
     * 
     * Complejidad: O(n * (log |C| + log |T|))
     * Donde |C| es la cantidad de ciudades y |T| es la cantidad de traslados.
     */
    private int[] despacharDesde(int n, HeapEnlazado<Traslado> heap){
        int [] despachados = new int[n];
        int index = 0;
        
        // O(n): Iteración sobre los traslados a despachar
        while(n > 0 && !heap.vacio()){
            // O(log |T|): Desapilado de un traslado del heap
            Traslado traslado = heap.desapilarMax();
            despachados[index ++] = traslado.id;

            // O(log |C|): Procesamiento de los despachos de las ciudades
            procesarDespacho(traslado);
            n--;
        }
        return despachados;
    }
 
    /**
     * Procesa el despacho de un traslado, actualizando las ciudades involucradas y las ganancias totales.
     * 
     * Complejidad: O(log |C|)
     * Donde |C| es la cantidad de ciudades
     */
    private void procesarDespacho(Traslado traslado){
        // O(1): Obtención de la ciudad con mayor ganancia y mayor pérdida
        int mayorGanancia = this.getCiudad(this.cMayorGanancia.get(0)).ganancia;
        int mayorPerdida = this.getCiudad(this.cMayorPerdida.get(0)).perdida;

        // O(log |C|): Procesamiento de la ciudad origen
        Ciudad origen = this.getCiudad(traslado.origen);
        origen.ganancia += traslado.gananciaNeta;
        cSuperavit.actualizarPrioridad(this.ciudades.get(origen.id));

        // O(log |C|): Procesamiento de la ciudad destino
        Ciudad destino = this.getCiudad(traslado.destino);
        destino.perdida += traslado.gananciaNeta;
        cSuperavit.actualizarPrioridad(this.ciudades.get(destino.id));

        // O(1): Actualización de las ciudades con mayor ganancia y perdida
        this.gananciasTotales += traslado.gananciaNeta;
        this.despachosTotales ++;

        if (origen.ganancia > mayorGanancia) {
            this.cMayorGanancia.clear();
            this.cMayorGanancia.add(origen.id);
        } else if (origen.ganancia == mayorGanancia){ this.cMayorGanancia.add(origen.id);}

        if (destino.perdida > mayorPerdida) {
            this.cMayorPerdida.clear();
            this.cMayorPerdida.add(destino.id);
        } else if (destino.perdida == mayorPerdida){ this.cMayorPerdida.add(destino.id);}
    }
    
    /**
     * Obtiene la ciudad correspondiente a un id.
     * 
     * Complejidad: O(1)
     */
    private Ciudad getCiudad(int id){  return this.ciudades.get(id).valor; }
}