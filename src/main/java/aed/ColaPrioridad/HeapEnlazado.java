package aed.ColaPrioridad;
import java.util.Comparator;

public class HeapEnlazado<T> extends AbstractHeap<T>{
    /** 
     * Almacena la referencia al heap espejado.
     * Este heap se usa como una del heap original con otro ordenamiento,
     * facilitando operaciones que requieren dos heaps mantieniendo amnos sincronizados.
     */
    HeapEnlazado<T> espejo;

    public HeapEnlazado(Comparator<T> comparador){
        super(comparador);
    }

    /**
     * Enlaza el heap actual con un espejo, permitiendo la sincronización entre ambos.
     * Complejidad: O(1), ya que solo actualiza referencias entre los dos heaps.
     */
    public void inicialiar(HeapEnlazado<T> espejo){
        this.enlazar(espejo);
        espejo.enlazar(this);
    }


    /**
     * Inicializa el heap actual y su espejo con un conjunto de elementos, manteniendo
     * la sincronización entre ambos. 
     *
     * @param espejo el heap espejo a sincronizar.
     * @param elems un array de elementos con los cuales inicializar ambos heaps.
     *
     * Complejidad: O(n), debido a que `array2heap` y `heapify` procesan todos los elementos.
     */
    public void inicialiar(HeapEnlazado<T> espejo, T[] elems){
        this.inicialiar(espejo);
        if (elems != null){ this.array2heap(elems);}
    }

    /**
     * {@inheritDoc}
     * Ademas sincroniza su posición con el heap espejo.
     *
     * Complejidad: O(log n), ya que `siftUp` es la operación más costosa involucrada.
     */
    @Override
    public Nodo apilar(T elem) {
        NodoEnlazado nodo = new NodoEnlazado(elem, this.elementos.size());
        this.elementos.add(nodo);
        nodo.posicionReflejo = this.espejo.apilarReflejo(elem);
        this.siftUp(this.getLastIndx());
        return nodo;
    }    
    
    /**
     * {@inheritDoc}
     * Ademas elimina el nodo 'reflejo' del heap espejo.
     *
     * Complejidad: O(log n), ya que la eliminación del reflejo implica una operación `siftDown`
     */
    @Override
    protected T popLast() { 
        NodoEnlazado nodo = (NodoEnlazado)this.getNodo(this.getLastIndx());
        this.elementos.remove(nodo.posicion);
        this.espejo.eliminarReflejo(nodo);
        nodo.posicion = -1;
        return nodo.valor;
    }

    /**
     * {@inheritDoc}
     * Ademas actualiza su reflejo en el heap espejo.
     *
     * Complejidad: O(1), ya que solo realiza asignaciones y llamadas directas.
     */
    @Override
    protected void cambiarPosicion(Nodo nodo, int index){
        NodoEnlazado nodoEnlazado = (NodoEnlazado) nodo;
        this.elementos.set(index, nodoEnlazado);
        nodoEnlazado.posicion = index;
        this.espejo.actualizarReflejo(nodoEnlazado);
    }    

    /**
     * Vincula el heap actual con el heap espejo.
     * 
     * Complejidad: O(1), ya que solo actualiza una referencia.
     */
    private void enlazar(HeapEnlazado<T> espejo){ this.espejo = espejo; }

    /**
     * Actualiza la posición del reflejo de un nodo en el heap actual.
     *
     * @param reflejo el nodo reflejado cuya posición será actualizada.
     *
     * Complejidad: O(1), ya que solo realiza verificaciones y asignaciones directas.
     */
    private void actualizarReflejo(NodoEnlazado reflejo){
        if (!existe(reflejo.posicionReflejo)) return;
        NodoEnlazado nodo = (NodoEnlazado)this.getNodo(reflejo.posicionReflejo);
        nodo.posicionReflejo = reflejo.posicion;
    }

    /**
     * Elimina el reflejo de un nodo en el heap espejo, manteniendo la propiedad del heap.
     *
     * @param reflejo el nodo reflejado a eliminar.
     *
     * Complejidad: O(log n), debido a la operación de `siftDown` después de eliminar el nodo.
     */
    private void eliminarReflejo(NodoEnlazado reflejo){
        Nodo nodo = this.getNodo(reflejo.posicionReflejo);
        this.swap(nodo.posicion, this.getLastIndx());
        this.elementos.remove(this.getLastIndx());
        nodo.posicion = -1;
        this.siftDown(reflejo.posicionReflejo);
    }

    /**
     * Inserta un elemento en el heap espejo y devuelve su posición inicial.
     *
     * @param valor el elemento a insertar.
     * @return la posición final del elemento después del sift.
     *
     * Complejidad: O(log n), ya que realiza una operación de `siftUp`.
     */
    private int apilarReflejo(T valor){
        NodoEnlazado nodo = new NodoEnlazado(valor, this.elementos.size());
        this.elementos.add(nodo);
        // La posicion del reflejo siempre es el ultimo elemento primero y luego lo acomodo
        nodo.posicionReflejo = this.getLastIndx(); 
        return this.siftUp(this.getLastIndx());
    }

    /**
     * Agrega elementos al heap actual sin actualizar el reflejo.
     *
     * @param elems un array de elementos a agregar.
     *
     * Complejidad: O(n), ya que itera sobre todos los elementos del array.
     */
    private void addElemens(T[] elems){
        for (int i = 0; i < elems.length; i++){
            NodoEnlazado n = new NodoEnlazado(elems[i], i, i);
            this.elementos.add(n);
        }
    }

    /**
     * Convierte un array en un heap, inicializando tanto el heap actual como su espejo.
     *
     * @param elems un array de elementos a organizar en el heap.
     *
     * Complejidad: O(n), ya que incluye agregar elementos y realizar `heapify` en ambos heaps.
     */
    private void array2heap(T[] elems){
        if (this.getSize() != 0) this.elementos.clear();
        this.addElemens(elems);
        espejo.addElemens(elems);
        this.heapify();
        espejo.heapify();
    }

    protected class NodoEnlazado extends Nodo {
        /** 
         * La posición del nodo en el espejo (reflejo) del heap.
         */
        protected int posicionReflejo;
        
        protected NodoEnlazado (T valor){ super(valor); }

        protected NodoEnlazado (T valor, int posicion){ super(valor, posicion); }

        /** 
         * Constructor de NodoEnlazado con valor, posición y posición en el espejo.
         * 
         * @param valor El valor que se almacenará en el nodo.
         * @param posicion La posición del nodo en el heap.
         * @param posicionReflejo La posición del nodo en el espejo.
         */
        protected NodoEnlazado (T valor, int posicion, int posicionReflejo){
            super(valor, posicion);
            this.posicionReflejo = posicionReflejo;
        }
    } 
}