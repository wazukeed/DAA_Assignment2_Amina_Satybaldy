import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    void testAddAndGet() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void testInsertAtIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);
        array.add(1, 20);

        assertEquals(20, array.get(1));
    }

    @Test
    void testRemove() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(20, array.remove(1));
        assertEquals(30, array.get(1));
        assertEquals(2, array.size());
    }

    @Test
    void testContains() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        assertTrue(array.contains(10));
        assertFalse(array.contains(50));
    }

    @Test
    void testDuplicates() {
        DynamicArray array = new DynamicArray();

        array.add(5);
        array.add(5);

        assertEquals(5, array.get(0));
        assertEquals(5, array.get(1));
    }

    @Test
    void testInvalidIndex() {
        DynamicArray array = new DynamicArray();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(0)
        );
    }
}