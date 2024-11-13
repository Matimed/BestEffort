package aed.ColaPrioridad;

interface ColaPrioridad<T> {
    /**
     * Devuelve verdadero si no contiene elementos. 
     * 
     */
    public boolean vacia();

    /**
     * Agrega un elemento y lo ordena.
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