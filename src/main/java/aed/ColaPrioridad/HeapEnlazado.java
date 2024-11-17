package aed.ColaPrioridad;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapEnlazado<T> extends AbstractHeap<T>{
    ArrayList<NodoEnlazado> elementos;
    HeapEnlazado<T> espejo; // Almacena la referencia al heap espejado

    public HeapEnlazado(Comparator<T> comparador){
        super(comparador);
        this.elementos = new ArrayList<NodoEnlazado>();
    }

    @Override
    public String toString() {
        return this.elementos.toString();
    }


    /**
     * Enlaza el Heap con otro del mismo tipo.
     * 
     */
    public void inicialiar(HeapEnlazado<T> espejo){
        this.enlazar(espejo);
        espejo.enlazar(this);
    }


    /**
     * Enlaza, completa y ordena tanto el heap como su reflejo.
     * 
     */
    public void inicialiar(HeapEnlazado<T> espejo, T[] elems){
        this.inicialiar(espejo);
        if (elems != null){ this.array2heap(elems);}
    }

    @Override
    public Nodo apilar(T elem) {
        NodoEnlazado nodo = new NodoEnlazado(elem, this.elementos.size());
        this.elementos.add(nodo);
        nodo.posicionReflejo = this.espejo.apilarReflejo(elem);
        this.siftUp(this.getLastIndx());
        return nodo;
    }

    @Override
    protected T getValor(int i) { return this.getNodo(i).valor; }

    @Override
    public int getSize() { return this.elementos.size(); }
    
    
    @Override
    protected T popLast() { 
        NodoEnlazado nodo = this.getNodo(this.getLastIndx());
        this.elementos.remove(nodo.posicion);
        this.espejo.eliminarReflejo(nodo.posicionReflejo);
        nodo.posicion = -1;
        return nodo.valor;
    }

    private void enlazar(HeapEnlazado<T> espejo){ this.espejo = espejo; }

    private void actualizarReflejo(int indxNodo, int nuevaRef){
        if(!existe(indxNodo)) return;
        NodoEnlazado nodo = this.getNodo(indxNodo);
        nodo.posicionReflejo = nuevaRef;
    }

    private void eliminarReflejo(int indxNodo){
        NodoEnlazado reflejo = this.getNodo(indxNodo);
        this.swap(reflejo.posicion, this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        reflejo.posicion = -1;
        this.siftDown(indxNodo);
    }

    private int apilarReflejo(T valor){
        NodoEnlazado nodo = new NodoEnlazado(valor, this.elementos.size());
        this.elementos.add(nodo);
        // La posicion del reflejo siempre es el ultimo elemento primero y luego lo acomodo
        nodo.posicionReflejo = this.getLastIndx(); 
        return this.siftUp(this.getLastIndx());
    }

    private void addElemens(T[] elems){
        //Notar que este metodo no actualiza el reflejo automaticamente
        for (int i = 0; i < elems.length; i++){
            NodoEnlazado n = new NodoEnlazado(elems[i], i, i);
            this.elementos.add(n);
        }
    }

    private void array2heap(T[] elems){
        if (this.getSize() != 0) this.elementos.clear();
        this.addElemens(elems);
        espejo.addElemens(elems);
        this.heapify();
        espejo.heapify();
    }


    protected NodoEnlazado getNodo(int nodo) { return this.elementos.get(nodo); }

    @Override
    protected void cambiarPosicion(Nodo nodo, int index){
        NodoEnlazado nodoE = (NodoEnlazado) nodo;
        this.elementos.set(index, nodoE);
        nodo.posicion = index;
        this.espejo.actualizarReflejo(nodoE.posicionReflejo, index);
    }

    protected class NodoEnlazado extends Nodo {
        protected int posicionReflejo;
        
        protected NodoEnlazado (T valor){ super(valor); }

        protected NodoEnlazado (T valor, int posicion){ super(valor, posicion); }

        protected NodoEnlazado (T valor, int posicion, int posicionReflejo){
            super(valor, posicion);
            this.posicionReflejo = posicionReflejo;
        }
    } 
}