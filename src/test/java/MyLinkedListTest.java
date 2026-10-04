import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void testAddAndGet() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testInsertAtHead() {
        MyLinkedList list = new MyLinkedList();

        list.add(20);
        list.add(0, 10);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void testRemove() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(20, list.remove(1));
        assertEquals(30, list.get(1));
    }

    @Test
    void testContains() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        assertTrue(list.contains(20));
        assertFalse(list.contains(100));
    }

    @Test
    void testDuplicates() {
        MyLinkedList list = new MyLinkedList();

        list.add(7);
        list.add(7);

        assertEquals(7, list.get(0));
        assertEquals(7, list.get(1));
    }

    @Test
    void testInvalidIndex() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(0)
        );
    }
}