public class DynamicArray {

    private int[] data;
    private int size;
    private final Metrics metrics;

    public DynamicArray() {
        data = new int[10];
        size = 0;
        metrics = new Metrics();
    }

    public void add(int value) {
        if (size == data.length) {
            grow();
        }

        data[size] = value;
        metrics.moves++;
        size++;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == data.length) {
            grow();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            metrics.steps++;
            metrics.moves++;
        }

        data[index] = value;
        metrics.moves++;
        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removed = data[index];
        metrics.steps++;

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            metrics.steps++;
            metrics.moves++;
        }

        size--;
        return removed;
    }

    public int get(int index) {
        checkIndex(index);

        metrics.steps++;
        return data[index];
    }

    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            metrics.steps++;
            metrics.comparisons++;

            if (data[i] == value) {
                return true;
            }
        }

        return false;
    }

    private void grow() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            metrics.steps++;
            metrics.moves++;
        }

        data = newData;
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