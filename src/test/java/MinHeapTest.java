import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void testPeekMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.peekMin());
    }

    @Test
    void testExtractMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
    }

    @Test
    void testDuplicates() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void testSortedOutput() {
        MinHeap heap = new MinHeap();

        heap.insert(40);
        heap.insert(10);
        heap.insert(30);
        heap.insert(20);
        heap.insert(5);

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }

    @Test
    void testEmptyHeap() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }
}