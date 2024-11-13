package aed.ColaPrioridad;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> extends AbstractHeap<T> {
    ArrayList<T> elementos;

    public Heap(Comparator<T> comparador){
        super(comparador);
        this.elementos = new ArrayList<T>();
    }

    public Heap(Comparator<T> comparador, T[] elems){
        this(comparador); // Creo el Heap vacio
        for (T e: elems){ this.elementos.add(e);} //Agrego al arrayList O(n)
        this.heapify();
    }

    public void apilar(T elem) {
        this.elementos.add(elem);
        this.siftUp(this.elementos.size()-1);
    }

    public ArrayList<T> sort(){
        ArrayList<T> ordenado = new ArrayList<T>();
        while (!this.vacia()){
            ordenado.add(this.desapilarMax());
        }
        this.elementos = ordenado;
        return ordenado;
    }

    @Override
    protected T getValor(int nodo) { return this.elementos.get(nodo); }

    @Override
    protected int getSize() { return this.elementos.size(); }

    @Override
    protected void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        T nodoA = this.getValor(indxA);
        T nodoB = this.getValor(indxB);

        this.elementos.set(indxA, nodoB);
        this.elementos.set(indxB, nodoA);   
    }
    
    @Override
    protected T popLast() { 
        T res = this.elementos.get(this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        return res;
    }
}
