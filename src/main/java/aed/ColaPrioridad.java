package aed;

interface ColaPrioridad<T> {
    /**
     * Devuelve verdadero si la cola esta vacia
     * 
     */
    public boolean vacia();

    /**
     * Agrega un elemento a la la secuencia.
     * 
     */
    public void apilar(T elem);

    /**
     * Devuelve el elemento de mayor prioridad segun el criterio de comparacion.
     * 
    */
    public T consultarMax();

    /**
     * Retorna y remueve el elemento de mayor prioridad segun el criterio de comparacion.
     * 
     */
    public T desapilarMax();

}