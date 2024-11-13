package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> implements ColaPrioridad<T> {
    ArrayList<T> elementos;
    Comparator<T> comparador;

    private int getIndxHijoIzq(int i) { return 2 * i + 1; }
    
    private int getIndxHijoDer(int i) { return 2 * i + 2; }
    
    private int getIndxPadre(int i) { return (i - 1) / 2; }
    
    private T getNodo(int nodo) { return this.elementos.get(nodo); }

    private T getNodoHijoIzq(int nodo) { return this.elementos.get(this.getIndxHijoIzq(nodo)); }

    private T getNodoHijoDer(int nodo) { return this.elementos.get(this.getIndxHijoDer(nodo)); }

    private T getNodoPadre(int nodo) { return this.elementos.get(this.getIndxPadre(nodo)); }

    private boolean existe (int nodo) { return nodo < this.elementos.size(); }

    private T popLast() { 
        T res = this.elementos.get(this.elementos.size() - 1);
        this.elementos.remove(this.elementos.size() - 1);
        return res;
    }

    private void siftUp(int i){
        T nodo;
        T padre;
        while (i != 0 && this.comparador.compare(this.getNodo(i), this.getNodoPadre(i)) > 0){
            nodo = this.getNodo(i);
            padre = this.getNodoPadre(i);
            this.elementos.set(this.getIndxPadre(i), nodo);
            this.elementos.set(i, padre);
            i = this.getIndxPadre(i);
        }
    }

    private void siftDown(int i){
        T nodo;
        int indxHijoMayor;
        T nodoHijoMayor;

        // Itero mientras tenga un nodo hijo menor que si mismo
        while  (this.existe(this.getIndxHijoIzq(i)) && this.comparador.compare(this.getNodo(i), this.getNodoHijoIzq(i)) < 0 ||
                this.existe(this.getIndxHijoDer(i)) && this.comparador.compare(this.getNodo(i), this.getNodoHijoDer(i)) < 0) {
                
            if (!this.existe(this.getIndxHijoDer(i))) indxHijoMayor = this.getIndxHijoIzq(i);
            else {
                if (this.comparador.compare(this.getNodoHijoIzq(i) , this.getNodoHijoDer(i)) > 0) indxHijoMayor = getIndxHijoIzq(i);
                else indxHijoMayor = getIndxHijoDer(i); 
            }

            nodoHijoMayor = this.getNodo(indxHijoMayor);
            nodo = this.getNodo(i);
            this.elementos.set(i, nodoHijoMayor);
            this.elementos.set(indxHijoMayor, nodo);
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


