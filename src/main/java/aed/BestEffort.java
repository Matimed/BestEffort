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

    /**O(|C|+|T|)
     * Notar que, en esta función, CantCiudades = C, y traslados = T 
    */
    public BestEffort(int cantCiudades, Traslado[] traslados){       
        //O(1)
        this.gananciasTotales = 0;
        this.despachosTotales = 0;
        this.cMayorGanancia = new ArrayList<Integer>();
        this.cMayorPerdida = new ArrayList<Integer>();
        Ciudad[] ciudades = new Ciudad[cantCiudades];
        
        //O(|C|)
        for (int i = 0; i < cantCiudades; i++) {
            //O(1)
            Ciudad ciudad = new Ciudad(i);
            this.cMayorGanancia.add(i);
            this.cMayorPerdida.add(i);
            ciudades[i] = ciudad;
        }

        //O(|C|)
        this.ciudades = cSuperavit.inicialiar(ciudades);
        
        //O(|T|)
        this.tAntiguedad.inicialiar(this.tGanancias, traslados);
    }

    /*O(|traslados|*log|T|)
    */
    public void registrarTraslados(Traslado[] traslados){
        //O(|traslados|)
        for (Traslado t : traslados) 
        { 
            //O(log|T|)
            tAntiguedad.apilar(t); 
        }
    }

    /*O(n*(log|C|+log|T|))
    */
    public int[] despacharMasRedituables(int n){ return despacharDesde(n, this.tGanancias); }

    /*O(n*(log|C|+log|T|))
    */
    public int[] despacharMasAntiguos(int n){ return this.despacharDesde(n, this.tAntiguedad); }
    
    /*O(1)
    */
    public int ciudadConMayorSuperavit() { return cSuperavit.consultarMax().id; }

    /*O(1)
    */
    public ArrayList<Integer> ciudadesConMayorGanancia() { return this.cMayorGanancia;} 

    /*O(1)
    */
    public ArrayList<Integer> ciudadesConMayorPerdida() {return this.cMayorPerdida;}
    
    /*O(1)
    */
    public int gananciaPromedioPorTraslado(){
        if (despachosTotales==0) {return 0; }
        return (int) Math.floor(gananciasTotales/despachosTotales); }   
    
    /*O(n*(log|C|+log|T|))
    */ 
    private int[] despacharDesde(int n, HeapEnlazado<Traslado> heap){
        //O(1)
        int [] despachados = new int[n];
        int index = 0;
        
        //O(n)
        while(n > 0 && !heap.vacio()){
            //O(log|T|)
            Traslado traslado = heap.desapilarMax();
            despachados[index ++] = traslado.id;

            //O(log|C|)
            procesarDespacho(traslado);
            n--;
        }
        return despachados;
    }
 
    /*O(log|C|+log|T|)
    */
    private void procesarDespacho(Traslado traslado){
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
    
    /*O(1) 
    */
    private Ciudad getCiudad(int id){  return this.ciudades.get(id).valor; }
}