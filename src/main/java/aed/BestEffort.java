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


    public BestEffort(int cantCiudades, Traslado[] traslados){       
        this.gananciasTotales = 0;
        this.despachosTotales = 0;
        this.cMayorGanancia = new ArrayList<Integer>();
        this.cMayorPerdida = new ArrayList<Integer>();
        
        Ciudad[] ciudades = new Ciudad[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {
            Ciudad ciudad = new Ciudad(i);
            this.cMayorGanancia.add(i);
            this.cMayorPerdida.add(i);
            ciudades[i] = ciudad;
        }
        this.ciudades = cSuperavit.inicialiar(ciudades);
       
       this.tAntiguedad.inicialiar(this.tGanancias, traslados);
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado t : traslados) { tAntiguedad.apilar(t); }
    }

    public int[] despacharMasRedituables(int n){ return despacharDesde(n, this.tGanancias); }

    public int[] despacharMasAntiguos(int n){ return this.despacharDesde(n, this.tAntiguedad); }
    
    public int ciudadConMayorSuperavit() { return cSuperavit.consultarMax().id; }

    public ArrayList<Integer> ciudadesConMayorGanancia() { return this.cMayorGanancia;} 

    public ArrayList<Integer> ciudadesConMayorPerdida() {return this.cMayorPerdida;}

    public int gananciaPromedioPorTraslado(){
        if (despachosTotales==0) {return 0; }
        
        return (int) Math.floor(gananciasTotales/despachosTotales);//dado que esta instrucción solo obtiene la parte entera de una división de enteros, es O(1)
    }   

    private int[] despacharDesde(int n, HeapEnlazado<Traslado> heap){
        int [] despachados = new int[n];
        int index = 0;

        while(n > 0 && !heap.vacia()){
            Traslado traslado = heap.desapilarMax();
            despachados[index ++] = traslado.id;

            procesarDespacho(traslado);
            n--;
        }
        return despachados;
    }
 
    private void procesarDespacho(Traslado  traslado){
        int mayorGanancia = this.getCiudad(this.cMayorGanancia.get(0)).ganancia;
        int mayorPerdida = this.getCiudad(this.cMayorPerdida.get(0)).perdida;

        Ciudad origen = this.getCiudad(traslado.origen);
        origen.ganancia += traslado.gananciaNeta;
        cSuperavit.actualizarPrioridad(this.ciudades.get(origen.id));

        Ciudad destino = this.getCiudad(traslado.destino);
        destino.perdida += traslado.gananciaNeta;
        cSuperavit.actualizarPrioridad(this.ciudades.get(destino.id));

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
    
    private Ciudad getCiudad(int id){  return this.ciudades.get(id).valor; }
}