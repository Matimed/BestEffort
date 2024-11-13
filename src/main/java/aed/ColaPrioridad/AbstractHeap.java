package aed.ColaPrioridad;
import java.util.Comparator;

public abstract class AbstractHeap<T> implements ColaPrioridad<T> {
    Comparator<T> comparador;

    protected abstract T getValor(int nodo);

    protected abstract int getSize();
    
    protected abstract void swap(int indxA, int indxB);
    
    protected abstract T popLast();

    public boolean vacia() { return this.getSize() == 0; }

    public T consultarMax() { return this.getValor(0); }

    public T desapilarMax(){
        this.swap(0,  this.getLastIndx());
        T res = this.popLast();
        this.siftDown(0);
        return res;
    }
    
    protected boolean existe(int nodo) { return nodo < this.getSize() && nodo >= 0; }

    protected int getLastIndx() { return this.getSize() - 1; }

    protected int getIndxIzq(int i) { return 2 * i + 1; }
    
    protected int getIndxDer(int i) { return 2 * i + 2; }
    
    protected int getIndxPadre(int i) { return (i - 1) / 2; }

    protected int siftUp(int i){
        while (i != 0 && this.comparador.compare(this.getValor(i), this.getValor(this.getIndxPadre(i))) > 0){
            this.swap(i, this.getIndxPadre(i));
            i = this.getIndxPadre(i);
        }
        return i;
    }

    protected int siftDown(int i){
        int indxMayor = this.getIndxMayor(i);
        // Itero mientras tenga un nodo hijo mayor que si mismo
        while  (this.existe(indxMayor) && this.comparador.compare(this.getValor(i), this.getValor(indxMayor)) < 0) {                
            this.swap(i, indxMayor);
            i = indxMayor;
            indxMayor = this.getIndxMayor(i);
        }
        return i;
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
}
