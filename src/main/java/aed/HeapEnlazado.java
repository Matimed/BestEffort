package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapEnlazado<T> implements ColaPrioridad<T> {
    ArrayList<Nodo> elementos;
    Comparator<T> comparador;
    HeapEnlazado<T> espejo; // Almacena la referencia al heap espejado

    private int getIndxIzq(int i) { return 2 * i + 1; }
    
    private int getIndxDer(int i) { return 2 * i + 2; }
    
    private int getIndxPadre(int i) { return (i - 1) / 2; }

    private int getLastIndx() { return this.elementos.size() - 1; }
    
    private Nodo getNodo(int nodo) { return this.elementos.get(nodo); }

    private T getValor(int i) { return this.getNodo(i).valor; }

    private T getValorIzq(int nodo) { return this.getValor(this.getIndxIzq(nodo)); }

    private T getValorDer(int nodo) { return this.getValor(this.getIndxDer(nodo)); }

    private T getValorPadre(int nodo) { return this.getValor(this.getIndxPadre(nodo)); }

    private boolean existe (int nodo) { return nodo < this.elementos.size(); }

    private void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        Nodo nodoA = this.getNodo(indxA);
        Nodo nodoB = this.getNodo(indxB);
    
        this.setNodo(indxA, nodoB);
        this.setNodo(indxB, nodoA);
    }

    private void setNodo(int indx, Nodo nodo){
        this.elementos.set(indx, nodo);
        this.espejo.actualizarReflejo(nodo.posicionEspejo, indx);
    }

    private T popLast() { 
        Nodo nodo = this.getNodo(this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        this.espejo.eliminarReflejo(nodo.posicionEspejo);

        return nodo.valor;
    }

    private int siftUp(int i){
        while (i != 0 && this.comparador.compare(this.getValor(i), this.getValorPadre(i)) > 0){
            this.swap(i, this.getIndxPadre(i));
            i = this.getIndxPadre(i);
        }
        return i;
    }

    private int siftDown(int i){
        int indxHijoMayor;

        // Itero mientras tenga un nodo hijo menor que si mismo
        while  (this.existe(this.getIndxIzq(i)) && this.comparador.compare(this.getValor(i), this.getValorIzq(i)) < 0 ||
                this.existe(this.getIndxDer(i)) && this.comparador.compare(this.getValor(i), this.getValorDer(i)) < 0) {
                
            if (!this.existe(this.getIndxDer(i))) indxHijoMayor = this.getIndxIzq(i);
            else {
                if (this.comparador.compare(this.getValorIzq(i) , this.getValorDer(i)) > 0) indxHijoMayor = getIndxIzq(i);
                else indxHijoMayor = getIndxDer(i); 
            }

            this.swap(i, indxHijoMayor);
            i = indxHijoMayor;
        }
        return i;
    }

    public HeapEnlazado(Comparator<T> comparador){
        this.elementos = new ArrayList<Nodo>();
        this.comparador = comparador;
    }

    public void apilar(T elem) {
        Nodo nodo = new Nodo(elem);
        this.elementos.add(nodo);
        nodo.posicionEspejo = this.espejo.apilarReflejo(elem);
        this.siftUp(this.getLastIndx());
    }

    public T consultarMax() {
        return this.getValor(0);
    }

    public T desapilarMax(){
        this.swap(0,  this.getLastIndx());
        T res = this.popLast();
        this.siftDown(0);
        
        return res;
    }

    public boolean vacia(){
        return this.elementos.isEmpty();
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


    private class Nodo {
        public int posicionEspejo;
        public T valor;

        public Nodo (T valor){
            this.valor = valor;
        }

        @Override
        public String toString(){
            return this.valor.toString() + ':' +  Integer. toString(this.posicionEspejo) ;
        }

    } 

}


