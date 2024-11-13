package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

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

        assertFalse(maxHeap.vacia());
        assertEquals(maxHeap.desapilarMax(), 8);
        assertEquals(maxHeap.desapilarMax(), 5);
        assertEquals(maxHeap.desapilarMax(), 3);
        assertEquals(maxHeap.desapilarMax(), 1);
        assertTrue(maxHeap.vacia());
    }

    @Test
    public void min_heap(){
        Heap<Integer> minHeap = new Heap<Integer>(Comparator.reverseOrder());
        minHeap.apilar(5);
        minHeap.apilar(3);
        minHeap.apilar(8);
        minHeap.apilar(1);

        assertFalse(minHeap.vacia());
        assertEquals(minHeap.desapilarMax(), 1);
        assertEquals(minHeap.desapilarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 5);
        assertEquals(minHeap.desapilarMax(), 8);
        assertTrue(minHeap.vacia());
    }
    

    @Test
    public void heap_enlazado(){
        HeapEnlazado<Integer> maxHeap = new HeapEnlazado<Integer>(Comparator.naturalOrder());
        HeapEnlazado<Integer> minHeap = new HeapEnlazado<Integer>(Comparator.reverseOrder());
        maxHeap.enlazar(minHeap);
        minHeap.enlazar(maxHeap);

        minHeap.apilar(5);
        minHeap.apilar(3);
        maxHeap.apilar(0);
        minHeap.apilar(8);
        minHeap.apilar(1);
        
        assertFalse(minHeap.vacia());
        assertFalse(maxHeap.vacia());
        assertEquals(minHeap.desapilarMax(), 0);
        assertEquals(maxHeap.consultarMax(), 8);
        assertEquals(minHeap.desapilarMax(), 1);
        assertEquals(maxHeap.desapilarMax(), 8);
        assertEquals(minHeap.consultarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 3);
        assertEquals(minHeap.desapilarMax(), 5);
        assertTrue(minHeap.vacia());
        assertTrue(maxHeap.vacia());
    }
    

}
