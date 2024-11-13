package aed.ColaPrioridad;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapEnlazado<T> extends AbstractHeap<T>{
    ArrayList<Nodo> elementos;
    HeapEnlazado<T> espejo; // Almacena la referencia al heap espejado

    public HeapEnlazado(Comparator<T> comparador){
        this.elementos = new ArrayList<Nodo>();
        this.comparador = comparador;
    }

    @Override
    public void apilar(T elem) {
        Nodo nodo = new Nodo(elem);
        this.elementos.add(nodo);
        nodo.posicionEspejo = this.espejo.apilarReflejo(elem);
        this.siftUp(this.getLastIndx());
    }

    @Override
    protected T getValor(int i) { return this.getNodo(i).valor; }

    @Override
    protected int getSize() { return this.elementos.size(); }
    
    @Override
    protected void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        Nodo nodoA = this.getNodo(indxA);
        Nodo nodoB = this.getNodo(indxB);
    
        this.setNodo(indxA, nodoB);
        this.setNodo(indxB, nodoA);
    }
    
    @Override
    protected T popLast() { 
        Nodo nodo = this.getNodo(this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        this.espejo.eliminarReflejo(nodo.posicionEspejo);

        return nodo.valor;
    }

    public void enlazar(HeapEnlazado<T> espejo){
        this.espejo = espejo;
    }

    public void actualizarReflejo(int indxNodo, int nuevaRef){
        if(!existe(indxNodo)) return;
        Nodo nodo = this.getNodo(indxNodo);
        nodo.posicionEspejo = nuevaRef;
    }

    public void eliminarReflejo(int indxNodo){
        this.swap(indxNodo, this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        this.siftDown(indxNodo);
    }

    public int apilarReflejo(T valor){
        Nodo nodo = new Nodo(valor);
        this.elementos.add(nodo);
        // La posicion del reflejo siempre es el ultimo elemento primero y luego lo acomodo
        nodo.posicionEspejo = this.getLastIndx(); 
        return this.siftUp(this.getLastIndx());
    }

    private Nodo getNodo(int nodo) { return this.elementos.get(nodo); }

    private void setNodo(int indx, Nodo nodo){
        this.elementos.set(indx, nodo);
        this.espejo.actualizarReflejo(nodo.posicionEspejo, indx);
    }

    protected class Nodo {
        public int posicionEspejo;
        public T valor;

        public Nodo (T valor){
            this.valor = valor;
        }
    } 
}