package aed.ColaPrioridad;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> extends AbstractHeap<T> {

    public Heap(Comparator<T> comparador){ super(comparador); }

    /**
     * Inicializa el heap con los elementos proporcionados y los organiza
     * para mantener la propiedad del heap.
     *
     * @param elems un array de elementos de tipo T que se agregarán al heap.
     * @return una lista de nodos que representan los elementos inicializados 
     * en el orden en el que fueron dados.
     *
     * Complejidad: O(n), ya que agregar los elementos al ArrayList es O(n), y
     * la operación de `heapify` también tiene una complejidad de O(n).
     */
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

    /**
     * {@inheritDoc}
     *
     * Complejidad: O(log n), ya que se realiza una operación de `siftUp` al final.
    */
    @Override
    public Nodo apilar(T elem) {
        Nodo nodo = new Nodo(elem, this.elementos.size());
        this.elementos.add(nodo);
        this.siftUp(this.elementos.size()-1);
        return nodo;
    }
    
    /**
     * {@inheritDoc}
     *
     * Complejidad: O(1), ya que elimina directamente el último elemento de la lista.
     */
    @Override
    protected T popLast() { 
        Nodo nodo = this.getNodo(this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        nodo.posicion = -1; 
        return nodo.valor;
    }


    /**
     * {@inheritDoc}
     *
     * Complejidad: O(1), porque simplemente ajusta un elemento en la lista y actualiza su posición.
     */
    @Override
    protected void cambiarPosicion(Nodo nodo, int indx){
        this.elementos.set(indx, nodo);
        nodo.posicion = indx;
    }
}
