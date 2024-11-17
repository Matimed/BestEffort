package aed.ColaPrioridad;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> extends AbstractHeap<T> {
    ArrayList<Nodo> elementos;

    public Heap(Comparator<T> comparador){
        super(comparador);
        this.elementos = new ArrayList<Nodo>();
    }

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

    // public ArrayList<T> sort(){
    //     ArrayList<T> ordenado = new ArrayList<T>();
    //     while (!this.vacia()){
    //         ordenado.add(this.desapilarMax());
    //     }
    //     this.elementos = ordenado;
    //     return ordenado;
    // }

    public Nodo getNodo(int indx) {return this.elementos.get(indx); }

    @Override
    protected T getValor(int nodo) { return this.getNodo(nodo).valor; }

    @Override
    protected int getSize() { return this.elementos.size(); }

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

    public void cambiarPrioridad(Nodo nodo, T valor){
        nodo.valor = valor;
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
