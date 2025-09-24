import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Integer[] arr = {10,3,5,1,20,8};

        // QuickSort
        ALGS4Middleware.Middleware.sort(arr, ALGS4Middleware.Middleware.SortType.QUICK);
        System.out.println("QuickSort ascending: " + java.util.Arrays.toString(arr));

        // QuickSelect: 3rd smallest (index 2)
        Integer kth = ALGS4Middleware.Middleware.select(arr, 2);
        System.out.println("3rd smallest via QuickSelect: " + kth);

        // MergeSort descending
        ALGS4Middleware.Middleware.sort(arr, Comparator.reverseOrder(), ALGS4Middleware.Middleware.SortType.MERGE);
        System.out.println("MergeSort descending: " + java.util.Arrays.toString(arr));

        // HeapSort ascending
        ALGS4Middleware.Middleware.sort(arr, ALGS4Middleware.Middleware.SortType.HEAP);
        System.out.println("HeapSort ascending: " + java.util.Arrays.toString(arr));

        // PriorityQueue
        var minPQ = ALGS4Middleware.Middleware.minPQ(2);
        minPQ.insert(10); minPQ.insert(5); minPQ.insert(20);
        System.out.println("MinPQ peek: " + minPQ.peek());
        System.out.print("Iterating MinPQ: ");
        for (int x : minPQ) System.out.print(x + " ");
        System.out.println();
    }
}
