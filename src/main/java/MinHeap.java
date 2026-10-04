public class MinHeap {

    private int[] heap;
    private int size;
    private final Metrics metrics;

    public MinHeap() {
        heap = new int[10];
        metrics = new Metrics();
    }

    public void insert(int value) {
        if (size == heap.length) {
            grow();
        }

        heap[size] = value;
        metrics.moves++;

        int index = size;
        size++;

        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.steps += 2;
            metrics.comparisons++;

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        metrics.steps++;
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        int min = heap[0];
        metrics.steps++;

        heap[0] = heap[size - 1];
        metrics.steps++;
        metrics.moves++;

        size--;

        int index = 0;

        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.steps += 2;
                metrics.comparisons++;

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                metrics.steps += 2;
                metrics.comparisons++;

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }

        return min;
    }

    private void swap(int first, int second) {
        int temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;

        metrics.steps += 2;
        metrics.moves += 2;
    }

    private void grow() {
        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
            metrics.steps++;
            metrics.moves++;
        }

        heap = newHeap;
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}