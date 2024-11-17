package aed.ColaPrioridad;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> extends AbstractHeap<T> {

    public Heap(Comparator<T> comparador){ super(comparador); }

    public ArrayList<Nodo> inicialiar(T[] elems){
        ArrayList<Nodo> res = new ArrayList<Nodo>();
        for (int i = 0; i < elems.length; i++){ 
            Nodo nodo = new Nodo(elems[i], i);
            res.add(nodo);
            this.elementos.add(nodo);
        } //Agrego al arrayList O(n)
        this.heapify();
        return res;
    }


    @Override
    public Nodo apilar(T elem) {
        Nodo nodo = new Nodo(elem, this.elementos.size());
        this.elementos.add(nodo);
        this.siftUp(this.elementos.size()-1);
        return nodo;
    }

    public Nodo getNodo(int indx) {return this.elementos.get(indx); }

    @Override
    protected void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        Nodo nodoA = this.getNodo(indxA);
        Nodo nodoB = this.getNodo(indxB);

        this.cambiarPosicion(nodoB, indxA);
        this.cambiarPosicion(nodoA, indxB);   
    }
    
    @Override
    protected T popLast() { 
        Nodo nodo = this.getNodo(this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        nodo.posicion = -1; 
        return nodo.valor;
    }

    public void actualizarPrioridad(Nodo nodo){
        Nodo padre = this.getNodo(this.getIndxPadre(nodo.posicion));
        if (this.compare(nodo, padre) > 0) this.siftUp(nodo.posicion);
        else this.siftDown(nodo.posicion);
    }   

    @Override
    protected void cambiarPosicion(Nodo nodo, int indx){
        this.elementos.set(indx, nodo);
        nodo.posicion = indx;
    }
}
