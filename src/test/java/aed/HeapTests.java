package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import aed.ColaPrioridad.Heap;
import aed.ColaPrioridad.HeapEnlazado;

public class HeapTests {

    @Test
    public void max_heap(){
        Heap<Integer> maxHeap = new Heap<Integer>(Comparator.naturalOrder());
        
        maxHeap.apilar(5);
        maxHeap.apilar(3);
        assertEquals(maxHeap.consultarMax(),5);
        maxHeap.apilar(8);
        maxHeap.apilar(1);

        assertFalse(maxHeap.vacio());
        assertEquals(maxHeap.desapilarMax(), 8);
        assertEquals(maxHeap.desapilarMax(), 5);
        assertEquals(maxHeap.desapilarMax(), 3);
        assertEquals(maxHeap.desapilarMax(), 1);
        assertTrue(maxHeap.vacio());

        //que pasa con valores duplicados
        maxHeap.apilar(7);
        maxHeap.apilar(7);
        assertEquals(maxHeap.consultarMax(),7);
        assertEquals(maxHeap.desapilarMax(), 7);
        assertEquals(maxHeap.desapilarMax(), 7);
        assertTrue(maxHeap.vacio());

    }

    @Test
    public void min_heap(){
        Heap<Integer> minHeap = new Heap<Integer>(Comparator.reverseOrder());
        minHeap.apilar(5);
        minHeap.apilar(3);
        minHeap.apilar(8);
        minHeap.apilar(1);

        assertFalse(minHeap.vacio());
        assertEquals(minHeap.desapilarMax(), 1);
        assertEquals(minHeap.desapilarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 5);
        assertEquals(minHeap.desapilarMax(), 8);
        assertTrue(minHeap.vacio());

        //que pasa con valores duplicados
        minHeap.apilar(3);
        minHeap.apilar(3);
        assertEquals(minHeap.consultarMax(),3);
        assertEquals(minHeap.desapilarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 3);
        assertTrue(minHeap.vacio());
    }

    @Test
    public void array2heap(){
        Integer[] elems = {4,5,9,1,0,3,-5};
        Heap<Integer> maxHeap = new Heap<Integer>(Comparator.naturalOrder());
        maxHeap.inicialiar(elems);
        
        assertFalse(maxHeap.vacio());
        assertEquals(maxHeap.desapilarMax(), 9);
        assertEquals(maxHeap.desapilarMax(), 5);
        assertEquals(maxHeap.desapilarMax(), 4);
        assertEquals(maxHeap.desapilarMax(), 3);
        assertEquals(maxHeap.desapilarMax(), 1);
        assertEquals(maxHeap.desapilarMax(), 0);
        assertEquals(maxHeap.desapilarMax(), -5);
        assertTrue(maxHeap.vacio());
    }
    

    @Test
    public void heap_enlazado(){
        HeapEnlazado<Integer> maxHeap = new HeapEnlazado<Integer>(Comparator.naturalOrder());
        HeapEnlazado<Integer> minHeap = new HeapEnlazado<Integer>(Comparator.reverseOrder());
        maxHeap.inicialiar(minHeap);

        minHeap.apilar(5);
        minHeap.apilar(3);
        maxHeap.apilar(0);
        minHeap.apilar(8);
        minHeap.apilar(1);
        
        assertFalse(minHeap.vacio());
        assertFalse(maxHeap.vacio());
        assertEquals(minHeap.desapilarMax(), 0);
        assertEquals(maxHeap.consultarMax(), 8);
        assertEquals(minHeap.desapilarMax(), 1);
        assertEquals(maxHeap.desapilarMax(), 8);
        assertEquals(minHeap.consultarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 5);
        assertTrue(minHeap.vacio());
        assertTrue(maxHeap.vacio());
    }
    
    @Test
    public void array2heap_enlazado(){
        HeapEnlazado<Integer> maxHeap = new HeapEnlazado<Integer>(Comparator.naturalOrder());
        HeapEnlazado<Integer> minHeap = new HeapEnlazado<Integer>(Comparator.reverseOrder());
        Integer[] elems = {5,3,0,8,1};
        maxHeap.inicialiar(minHeap, elems);
        
        assertFalse(minHeap.vacio());
        assertFalse(maxHeap.vacio());
        assertEquals(minHeap.desapilarMax(), 0);
        assertEquals(maxHeap.consultarMax(), 8);
        assertEquals(minHeap.desapilarMax(), 1);
        assertEquals(maxHeap.desapilarMax(), 8);
        assertEquals(minHeap.consultarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 5);
        assertTrue(minHeap.vacio());
        assertTrue(maxHeap.vacio());
    }
    
    @Test
    public void max_heap_single_element(){
        Heap<Integer> maxHeap = new Heap<Integer>(Comparator.naturalOrder());
        maxHeap.apilar(10);

        assertEquals(maxHeap.consultarMax(),10);
        assertEquals(maxHeap.desapilarMax(),10);
        assertTrue(maxHeap.vacio());

    }

}
