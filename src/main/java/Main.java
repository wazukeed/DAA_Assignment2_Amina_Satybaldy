public class Main {

    public static void main(String[] args) {

        System.out.println("Dynamic Array:");
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }

        System.out.println("\nLinked List:");
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println("\nMin Heap:");
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        while (heap.size() > 0) {
            System.out.println(heap.extractMin());
        }
    }
}