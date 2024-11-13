package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> implements ColaPrioridad<T> {
    ArrayList<T> elementos;
    Comparator<T> comparador;

    private int getIndxIzq(int i) { return 2 * i + 1; }
    
    private int getIndxDer(int i) { return 2 * i + 2; }
    
    private int getIndxPadre(int i) { return (i - 1) / 2; }

    private int getLastIndx() { return this.elementos.size() - 1; }
    
    private T getValor(int nodo) { return this.elementos.get(nodo); }

    private T getValorIzq(int nodo) { return this.elementos.get(this.getIndxIzq(nodo)); }

    private T getValorDer(int nodo) { return this.elementos.get(this.getIndxDer(nodo)); }

    private T getValorPadre(int nodo) { return this.elementos.get(this.getIndxPadre(nodo)); }

    private boolean existe (int nodo) { return nodo < this.elementos.size(); }

    private void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        T nodoA = this.getValor(indxA);
        T nodoB = this.getValor(indxB);
        this.elementos.set(indxA, nodoB);
        this.elementos.set(indxB, nodoA);   
    }
    

    private T popLast() { 
        T res = this.elementos.get(this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        return res;
    }

    private void siftUp(int i){
        while (i != 0 && this.comparador.compare(this.getValor(i), this.getValorPadre(i)) > 0){
            this.swap(i, this.getIndxPadre(i));
            i = this.getIndxPadre(i);
        }
    }

    private void siftDown(int i){
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
        return this.elementos.get(0);
    }

    public T desapilarMax(){
        T res =  this.elementos.get(0);
        T ultimo = this.popLast();
        if(!this.vacia()){
            this.elementos.set(0, ultimo);
            this.siftDown(0);
        }
        return res;
    }

    public boolean vacia(){
        return this.elementos.isEmpty();
    }

}


