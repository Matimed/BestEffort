package aed.ColaPrioridad;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class AbstractHeap<T> {
    Comparator<T> comparador;
    ArrayList<Nodo> elementos;


    /**
     * Inserta un nuevo valor en el heap, ajustando la posición para mantener la propiedad del heap.
     * 
     * @param valor el valor a insertar en el heap.
     * @return el nodo creado que contiene el valor insertado.
    */
    public abstract Nodo apilar(T valor);
    
        
    /**
     * Elimina y devuelve el último elemento del heap.
     * 
     * @return el valor del último elemento eliminado.
     */
    protected abstract T popLast();
    
        
    /**
     * Cambia la posición de un nodo en el heap a un nuevo índice.
     * 
     * @param nodo el nodo cuya posición debe ser cambiada.
     * @param nuevoIndex el nuevo índice que se asignará al nodo.
     */
    protected abstract void cambiarPosicion(Nodo nodo, int nuevoIndex);
    
    /**
     * Verifica si el heap está vacío.
     * 
     * Complejidad: O(1), ya que simplemente verifica el tamaño actual del heap.
     *
     * @return true si el heap está vacío, false en caso contrario.
     */
    public boolean vacio() { return this.getSize() == 0; }
    
    /**
     * Devuelve el valor de mayor prioridad sin eliminarlo del heap.
     * 
     * Complejidad: O(1), ya que solo accede al primer elemento del heap.
     *
     * @return el valor de mayor prioridad en el heap.
     */
    public T consultarMax() { return this.getValor(0); }
     
    /**
     * Devuelve y elimina el elemento con mayor prioridad en el heap.
     * 
     * Complejidad: O(log n), ya que realiza un intercambio y una operación de `siftDown`,
     * ambas logarítmicas.
     *
     * @return el elemento de mayor prioridad que fue eliminado del heap.
    */
    public T desapilarMax(){
        this.swap(0,  this.getLastIndx());
        T res = this.popLast();
        this.siftDown(0);
        return res;
    }
 
    /**
     * Ajusta la posición de un nodo en el heap cuando su prioridad cambia, 
     * restaurando la propiedad del heap.
     * 
     * Complejidad: O(log n), ya que puede realizar una llamada a `siftUp` o `siftDown`,
     * cada una de las cuales tiene una complejidad logarítmica.
     *
     * @param nodo el nodo cuya prioridad ha sido actualizada.
     */
    public void actualizarPrioridad(Nodo nodo){
        if (this.compare(nodo.posicion, this.getIndxPadre(nodo.posicion)) > 0) this.siftUp(nodo.posicion);
        else this.siftDown(nodo.posicion);
    }   
        
    /**
     * Recibe el comparador con el cual se ordenara el Heap
     */
    protected AbstractHeap(Comparator<T> comparador){ 
        this.comparador = comparador;
        this.elementos = new ArrayList<Nodo>();
    }
    
    protected boolean existe(int nodo) { return nodo < this.getSize() && nodo >= 0; }
    
    protected Nodo getNodo(int nodo) { return this.elementos.get(nodo); }
    
    protected T getValor(int index) { return this.getNodo(index).valor; }
    
    protected int getLastIndx() { return this.getSize() - 1; }
    
    protected int getIndxIzq(int i) { return 2 * i + 1; }
    
    protected int getIndxDer(int i) { return 2 * i + 2; }
    
    protected int getIndxPadre(int i) { return (i - 1) / 2; }
    
    protected int getSize() { return this.elementos.size(); }        
    
    
    protected int compare(int indxNodoA, int indxNodoB){ 
        return this.comparador.compare(this.getValor(indxNodoA), this.getValor(indxNodoB));
    }

    /**
     * Intercambia dos nodos en el heap, actualizando sus posiciones internas.
     * 
     * Complejidad: O(1), ya que solo realiza un número fijo de operaciones, como
     * obtener referencias y actualizar posiciones.
     *
     * @param indxA índice del primer nodo.
     * @param indxB índice del segundo nodo.
     */
    protected void swap(int indxA, int indxB) {
        if (indxA == indxB) return;
        
        Nodo nodoA = this.getNodo(indxA);
        Nodo nodoB = this.getNodo(indxB);

        this.cambiarPosicion(nodoB, indxA);
        this.cambiarPosicion(nodoA, indxB);   
    }    

        
    /**
     * Restaura la propiedad de heap subiendo un nodo en la estructura 
     * hasta que su prioridad sea menor o igual a la de su padre.
     * 
     * Complejidad: O(log n), ya que como máximo recorre la altura del heap.
     *
     * @param i índice del nodo que se debe ajustar hacia arriba.
     * @return el índice final del nodo tras ajustar su posición.
     */
    protected int siftUp(int i){
        while (i != 0 && this.compare(i, this.getIndxPadre(i)) > 0){
            this.swap(i, this.getIndxPadre(i)); // Intercambia con su padre
            i = this.getIndxPadre(i); //Avanza el padre
        }
        return i; // Retorna la posicion final del nodo
    }

    /**
     * Restaura la propiedad de heap bajando un nodo en la estructura 
     * hasta que su prioridad sea mayor o igual a la de sus hijos.
     * 
     * Complejidad: O(log n), ya que como máximo recorre la altura del heap.
     *
     * @param i índice del nodo que se debe ajustar hacia abajo.
     * @return el índice final del nodo tras ajustar su posición.
     */
    protected int siftDown(int i) {
        int indxMayor = this.getIndxMayor(i); // Encuentra el hijo con mayor prioridad
        // Mientras el nodo actual sea menor que su hijo con mayor prioridad
        while (this.existe(indxMayor) && this.compare(i, indxMayor) < 0) { 
            this.swap(i, indxMayor); // Intercambia con el hijo mayor
            i = indxMayor; // Avanza al hijo mayor
            indxMayor = this.getIndxMayor(i); // Recalcula el hijo con mayor prioridad
        }
        return i; // Retorna la posición final del nodo
    }

    /**
     * Convierte una lista desordenada en un heap válido al aplicar 
     * la operación de `siftDown` desde el último nodo padre hasta la raíz.
     * 
     * Complejidad: O(n), ya que se procesan nodos desde hojas hasta la raíz,
     * y los nodos en niveles inferiores requieren menos trabajo.
     */
    protected void heapify(){
        int i = this.getSize(); // Comienza desde el último nodo
        while (i != 0){  // Recorre desde el último nodo padre hasta la raíz
            i --; // Retrocede al nodo anterior
            this.siftDown(i); // Restaura la propiedad de heap
        }
    }

    protected int getIndxMayor(int nodo){
        int indxIzq = this.getIndxIzq(nodo);
        int indxDer = this.getIndxDer(nodo);
        if (!this.existe(indxIzq)) return -1;
        else if (!this.existe(indxDer)) return indxIzq;
        else {
            if( this.compare(indxIzq,indxDer) > 0) return indxIzq;
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
    } 
}
