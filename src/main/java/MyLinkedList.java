public class MyLinkedList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private final Metrics metrics;

    public MyLinkedList() {
        metrics = new Metrics();
    }

    public void add(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
            metrics.moves += 2;
        } else {
            tail.next = newNode;
            tail = newNode;
            metrics.moves += 2;
        }

        size++;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(value);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
            metrics.moves += 2;

            if (size == 0) {
                tail = newNode;
                metrics.moves++;
            }
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
            metrics.moves += 2;
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.steps++;
            }

            newNode.next = current.next;
            current.next = newNode;
            metrics.moves += 2;
        }

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        if (index == 0) {
            int value = head.value;
            metrics.steps++;

            head = head.next;
            metrics.moves++;

            size--;

            if (size == 0) {
                tail = null;
                metrics.moves++;
            }

            return value;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.steps++;
        }

        Node removed = current.next;
        int value = removed.value;
        metrics.steps++;

        current.next = removed.next;
        metrics.moves++;

        if (removed == tail) {
            tail = current;
            metrics.moves++;
        }

        size--;
        return value;
    }

    public int get(int index) {
        checkIndex(index);

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.steps++;
        }

        metrics.steps++;
        return current.value;
    }

    public boolean contains(int value) {
        Node current = head;

        while (current != null) {
            metrics.steps++;
            metrics.comparisons++;

            if (current.value == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}