import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;

    public static void main(String[] args) throws Exception {

        File folder = new File("results");

        if (!folder.exists()) {
            folder.mkdir();
        }

        PrintWriter writer = new PrintWriter("results/results.csv");

        writer.println(
                "workload,variant,structure,n,time_ms,steps,moves,comparisons"
        );

        for (int n : SIZES) {
            System.out.println("Running n = " + n);

            runW1(writer, n);
            runW2(writer, n);
            runW3(writer, n, 0, "head");
            runW3(writer, n, n / 2, "middle");
            runW4(writer, n);
        }

        writer.close();

        System.out.println("Benchmark finished.");
        System.out.println("Results saved to results/results.csv");
    }

    // -------------------------------------------------
    // Create the same random data
    // -------------------------------------------------

    private static int[] createData(int n) {
        Random random = new Random(42);
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt(1_000_000);
        }

        return values;
    }

    // -------------------------------------------------
    // Calculate median time
    // -------------------------------------------------

    private static double median(long[] times) {
        Arrays.sort(times);
        return times[times.length / 2] / 1_000_000.0;
    }

    // -------------------------------------------------
    // W1 - Random Access
    // -------------------------------------------------

    private static void runW1(PrintWriter writer, int n) {

        int[] values = createData(n);

        DynamicArray array = new DynamicArray();
        MyLinkedList list = new MyLinkedList();

        for (int value : values) {
            array.add(value);
            list.add(value);
        }

        int[] indexes = new int[10000];
        Random random = new Random(42);

        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = random.nextInt(n);
        }

        // Warm-up
        for (int index : indexes) {
            array.get(index);
        }

        array.getMetrics().reset();

        long[] arrayTimes = new long[RUNS];

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            for (int index : indexes) {
                array.get(index);
            }

            long end = System.nanoTime();

            arrayTimes[run] = end - start;
        }

        Metrics am = array.getMetrics();

        writer.println(
                "W1,-,DynamicArray," + n + "," +
                        median(arrayTimes) + "," +
                        am.steps + "," +
                        am.moves + "," +
                        am.comparisons
        );

        // LinkedList warm-up
        for (int index : indexes) {
            list.get(index);
        }

        list.getMetrics().reset();

        long[] listTimes = new long[RUNS];

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            for (int index : indexes) {
                list.get(index);
            }

            long end = System.nanoTime();

            listTimes[run] = end - start;
        }

        Metrics lm = list.getMetrics();

        writer.println(
                "W1,-,MyLinkedList," + n + "," +
                        median(listTimes) + "," +
                        lm.steps + "," +
                        lm.moves + "," +
                        lm.comparisons
        );
    }

    // -------------------------------------------------
    // W2 - Search
    // -------------------------------------------------

    private static void runW2(PrintWriter writer, int n) {

        int[] values = createData(n);

        DynamicArray array = new DynamicArray();
        MyLinkedList list = new MyLinkedList();

        for (int value : values) {
            array.add(value);
            list.add(value);
        }

        int[] queries = new int[1000];

        // Half present
        for (int i = 0; i < 500; i++) {
            queries[i] = values[i % n];
        }

        // Half absent
        for (int i = 500; i < 1000; i++) {
            queries[i] = -1 - i;
        }

        // DynamicArray warm-up
        for (int query : queries) {
            array.contains(query);
        }

        array.getMetrics().reset();

        long[] arrayTimes = new long[RUNS];

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            for (int query : queries) {
                array.contains(query);
            }

            long end = System.nanoTime();

            arrayTimes[run] = end - start;
        }

        Metrics am = array.getMetrics();

        writer.println(
                "W2,-,DynamicArray," + n + "," +
                        median(arrayTimes) + "," +
                        am.steps + "," +
                        am.moves + "," +
                        am.comparisons
        );

        // LinkedList warm-up
        for (int query : queries) {
            list.contains(query);
        }

        list.getMetrics().reset();

        long[] listTimes = new long[RUNS];

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();

            for (int query : queries) {
                list.contains(query);
            }

            long end = System.nanoTime();

            listTimes[run] = end - start;
        }

        Metrics lm = list.getMetrics();

        writer.println(
                "W2,-,MyLinkedList," + n + "," +
                        median(listTimes) + "," +
                        lm.steps + "," +
                        lm.moves + "," +
                        lm.comparisons
        );
    }

    // -------------------------------------------------
    // W3 - Insert & Remove
    // -------------------------------------------------

    private static void runW3(
            PrintWriter writer,
            int n,
            int index,
            String variant
    ) {

        int[] values = createData(n);

        // DynamicArray
        long[] arrayTimes = new long[RUNS];

        long arraySteps = 0;
        long arrayMoves = 0;
        long arrayComparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            DynamicArray array = new DynamicArray();

            for (int value : values) {
                array.add(value);
            }

            array.getMetrics().reset();

            int currentIndex = Math.min(index, array.size());

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                array.add(currentIndex, i);
            }

            for (int i = 0; i < 1000; i++) {
                array.remove(currentIndex);
            }

            long end = System.nanoTime();

            arrayTimes[run] = end - start;

            if (run == RUNS - 1) {
                Metrics m = array.getMetrics();
                arraySteps = m.steps;
                arrayMoves = m.moves;
                arrayComparisons = m.comparisons;
            }
        }

        writer.println(
                "W3," + variant + ",DynamicArray," + n + "," +
                        median(arrayTimes) + "," +
                        arraySteps + "," +
                        arrayMoves + "," +
                        arrayComparisons
        );

        // MyLinkedList
        long[] listTimes = new long[RUNS];

        long listSteps = 0;
        long listMoves = 0;
        long listComparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            MyLinkedList list = new MyLinkedList();

            for (int value : values) {
                list.add(value);
            }

            list.getMetrics().reset();

            int currentIndex = Math.min(index, list.size());

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                list.add(currentIndex, i);
            }

            for (int i = 0; i < 1000; i++) {
                list.remove(currentIndex);
            }

            long end = System.nanoTime();

            listTimes[run] = end - start;

            if (run == RUNS - 1) {
                Metrics m = list.getMetrics();
                listSteps = m.steps;
                listMoves = m.moves;
                listComparisons = m.comparisons;
            }
        }

        writer.println(
                "W3," + variant + ",MyLinkedList," + n + "," +
                        median(listTimes) + "," +
                        listSteps + "," +
                        listMoves + "," +
                        listComparisons
        );
    }

    // -------------------------------------------------
    // W4 - Priority Processing
    // -------------------------------------------------

    private static void runW4(PrintWriter writer, int n) {

        int[] values = createData(n);

        long[] times = new long[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            MinHeap heap = new MinHeap();

            heap.getMetrics().reset();

            long start = System.nanoTime();

            for (int value : values) {
                heap.insert(value);
            }

            int previous = Integer.MIN_VALUE;

            while (heap.size() > 0) {

                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException(
                            "Heap returned values in wrong order"
                    );
                }

                previous = current;
            }

            long end = System.nanoTime();

            times[run] = end - start;

            if (run == RUNS - 1) {
                Metrics m = heap.getMetrics();
                steps = m.steps;
                moves = m.moves;
                comparisons = m.comparisons;
            }
        }

        writer.println(
                "W4,-,MinHeap," + n + "," +
                        median(times) + "," +
                        steps + "," +
                        moves + "," +
                        comparisons
        );
    }
}