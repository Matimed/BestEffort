package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> implements ColaPrioridad<T> {
    ArrayList<T> elementos;
    Comparator<T> comparador;

    protected int getIndxIzq(int i) { return 2 * i + 1; }
    
    protected int getIndxDer(int i) { return 2 * i + 2; }
    
    protected int getIndxPadre(int i) { return (i - 1) / 2; }

    protected int getLastIndx() { return this.elementos.size() - 1; }
    
    protected boolean existe (int nodo) { return nodo < this.elementos.size(); }

    protected T getValor(int nodo) { return this.elementos.get(nodo); }

    protected T getValorIzq(int nodo) { return this.getValor(this.getIndxIzq(nodo)); }

    protected T getValorDer(int nodo) { return this.getValor(this.getIndxDer(nodo)); }

    protected T getValorPadre(int nodo) { return this.getValor(this.getIndxPadre(nodo)); }

    public boolean vacia() { return this.elementos.isEmpty(); }

    protected void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        T nodoA = this.getValor(indxA);
        T nodoB = this.getValor(indxB);

        this.elementos.set(indxA, nodoB);
        this.elementos.set(indxB, nodoA);   
    }
    

    protected T popLast() { 
        T res = this.elementos.get(this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        return res;
    }

    protected int siftUp(int i){
        while (i != 0 && this.comparador.compare(this.getValor(i), this.getValorPadre(i)) > 0){
            this.swap(i, this.getIndxPadre(i));
            i = this.getIndxPadre(i);
        }
        return i;
    }

    protected int siftDown(int i){
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

    public Heap(Comparator<T> comparador){
        this.elementos = new ArrayList<T>();
        this.comparador = comparador;
    }

    public void apilar(T elem) {
        this.elementos.add(elem);
        this.siftUp(this.elementos.size()-1);
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
}
