package aed.ColaPrioridad;
import java.util.Comparator;

public abstract class AbstractHeap<T> {
    Comparator<T> comparador;

    public abstract Nodo apilar(T valor);

    protected abstract Nodo getNodo(int index);

    protected abstract void cambiarPosicion(Nodo nodo, int nuevoIndex);

    protected abstract int getSize();
        
    protected abstract T popLast();

    public boolean vacia() { return this.getSize() == 0; }

    public T consultarMax() { return this.getValor(0); }

    public T desapilarMax(){
        this.swap(0,  this.getLastIndx());
        T res = this.popLast();
        this.siftDown(0);
        return res;
    }
    
    protected AbstractHeap(Comparator<T> comparador){ this.comparador = comparador; }

    protected boolean existe(int nodo) { return nodo < this.getSize() && nodo >= 0; }

    protected T getValor(int index) { return this.getNodo(index).valor; }

    protected int getLastIndx() { return this.getSize() - 1; }

    protected int getIndxIzq(int i) { return 2 * i + 1; }
    
    protected int getIndxDer(int i) { return 2 * i + 2; }
    
    protected int getIndxPadre(int i) { return (i - 1) / 2; }

    protected int compare(Nodo nodoA, Nodo nodoB){ 
        return this.comparador.compare(nodoA.valor, nodoB.valor);
    }

    protected int compare(int indxNodoA, int indxNodoB){ 
        return this.comparador.compare(this.getValor(indxNodoA), this.getValor(indxNodoB));
    }

    protected void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        Nodo nodoA = this.getNodo(indxA);
        Nodo nodoB = this.getNodo(indxB);

        this.cambiarPosicion(nodoB, indxA);
        this.cambiarPosicion(nodoA, indxB);   
    }
    

    protected int siftUp(int i){
        while (i != 0 && this.compare(i, this.getIndxPadre(i)) > 0){
            this.swap(i, this.getIndxPadre(i));
            i = this.getIndxPadre(i);
        }
        return i;
    }

    protected int siftDown(int i){
        int indxMayor = this.getIndxMayor(i);
        // Itero mientras tenga un nodo hijo mayor que si mismo
        while  (this.existe(indxMayor) && this.compare(i, indxMayor) < 0) {                
            this.swap(i, indxMayor);
            i = indxMayor;
            indxMayor = this.getIndxMayor(i);
        }
        return i;
    }

    protected void heapify(){
        int i = this.getSize();
        while (i != 0){ // Downheap de abajo hacia arriba O(n)
            i --;
            this.siftDown(i);
        }
    }

    protected int getIndxMayor(int nodo){
        int indxIzq = this.getIndxIzq(nodo);
        int indxDer = this.getIndxDer(nodo);
        if (!this.existe(indxIzq)) return -1;
        else if (!this.existe(indxDer)) return indxIzq;
        else {
            if( this.comparador.compare(this.getValor(indxIzq), this.getValor(indxDer)) > 0) return indxIzq;
            else return indxDer;
        }
    }

    public class Nodo {
        public T valor;
        protected int posicion;

        protected Nodo (T valor){ this.valor = valor; }

        protected Nodo (T valor, int pos){
            this(valor);
            this.posicion = pos;
        }

        @Override
        public String toString() {
            return this.valor.toString();
        }
    } 
}
