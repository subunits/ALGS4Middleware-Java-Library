import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class ALGS4Middleware {

    /** --------------------- ALGS4 Core --------------------- */
    public static class ALGS4 {

        private static final Random RANDOM = new Random();

        /** ---------------- Priority Queue ---------------- */
        public static class PriorityQueue<Key> implements Iterable<Key> {
            private Key[] pq;
            private int n;
            private Comparator<? super Key> comparator;

            @SuppressWarnings("unchecked")
            public PriorityQueue(int initCapacity, Comparator<? super Key> comparator) {
                pq = (Key[]) new Object[initCapacity + 1];
                n = 0;
                this.comparator = comparator;
            }

            public void insert(Key x) {
                if (n == pq.length - 1) resize(2 * pq.length);
                pq[++n] = x;
                swim(n);
            }

            public Key delRoot() {
                if (isEmpty()) throw new RuntimeException("Priority queue underflow");
                Key root = pq[1];
                exch(1, n--);
                sink(1);
                pq[n + 1] = null;
                if (n > 0 && n == (pq.length - 1)/4) resize(pq.length/2);
                return root;
            }

            public Key peek() {
                if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
                return pq[1];
            }

            public boolean isEmpty() { return n == 0; }
            public int size() { return n; }

            private void swim(int k) {
                while (k > 1 && greater(k/2, k)) {
                    exch(k, k/2);
                    k = k/2;
                }
            }

            private void sink(int k) {
                while (2*k <= n) {
                    int j = 2*k;
                    if (j < n && greater(j, j+1)) j++;
                    if (!greater(k, j)) break;
                    exch(k, j);
                    k = j;
                }
            }

            private boolean greater(int i, int j) {
                return comparator.compare(pq[i], pq[j]) > 0;
            }

            private void exch(int i, int j) {
                Key temp = pq[i];
                pq[i] = pq[j];
                pq[j] = temp;
            }

            @SuppressWarnings("unchecked")
            private void resize(int capacity) {
                Key[] temp = (Key[]) new Object[capacity];
                for (int i = 1; i <= n; i++) temp[i] = pq[i];
                pq = temp;
            }

            @Override
            public Iterator<Key> iterator() {
                return new HeapIterator();
            }

            private class HeapIterator implements Iterator<Key> {
                private PriorityQueue<Key> copy;
                public HeapIterator() {
                    copy = new PriorityQueue<>(n, comparator);
                    for (int i = 1; i <= n; i++) copy.insert(pq[i]);
                }
                public boolean hasNext() { return !copy.isEmpty(); }
                public Key next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return copy.delRoot();
                }
            }
        }

        /** ---------------- HeapSort ---------------- */
        public static <Key> void heapSort(Key[] arr, Comparator<? super Key> comparator) {
            int n = arr.length;
            for (int k = n/2; k >= 1; k--) sink(arr, k, n, comparator);
            while (n > 1) {
                exch(arr, 1, n--);
                sink(arr, 1, n, comparator);
            }
        }

        private static <Key> void sink(Key[] arr, int k, int n, Comparator<? super Key> comparator) {
            while (2*k <= n) {
                int j = 2*k;
                if (j < n && greater(arr, j, j+1, comparator)) j++;
                if (!greater(arr, k, j, comparator)) break;
                exch(arr, k, j);
                k = j;
            }
        }

        private static <Key> boolean greater(Key[] arr, int i, int j, Comparator<? super Key> comparator) {
            return comparator.compare(arr[i-1], arr[j-1]) > 0;
        }

        private static <Key> void exch(Key[] arr, int i, int j) {
            Key temp = arr[i-1];
            arr[i-1] = arr[j-1];
            arr[j-1] = temp;
        }

        /** ---------------- MergeSort ---------------- */
        public static <Key> void mergeSort(Key[] a, Comparator<? super Key> comparator) {
            @SuppressWarnings("unchecked")
            Key[] aux = (Key[]) new Object[a.length];
            mergeSort(a, aux, 0, a.length - 1, comparator);
        }

        private static <Key> void mergeSort(Key[] a, Key[] aux, int lo, int hi, Comparator<? super Key> comparator) {
            if (hi <= lo) return;
            int mid = lo + (hi - lo)/2;
            mergeSort(a, aux, lo, mid, comparator);
            mergeSort(a, aux, mid+1, hi, comparator);
            merge(a, aux, lo, mid, hi, comparator);
        }

        private static <Key> void merge(Key[] a, Key[] aux, int lo, int mid, int hi, Comparator<? super Key> comparator) {
            for (int k = lo; k <= hi; k++) aux[k] = a[k];
            int i = lo, j = mid+1;
            for (int k = lo; k <= hi; k++) {
                if (i > mid) a[k] = aux[j++];
                else if (j > hi) a[k] = aux[i++];
                else if (comparator.compare(aux[j], aux[i]) < 0) a[k] = aux[j++];
                else a[k] = aux[i++];
            }
        }

        /** ---------------- QuickSort ---------------- */
        public static <Key> void quickSort(Key[] a, Comparator<? super Key> comparator) {
            shuffle(a);
            quickSort(a, 0, a.length-1, comparator);
        }

        private static <Key> void quickSort(Key[] a, int lo, int hi, Comparator<? super Key> comparator) {
            if (hi <= lo) return;
            int j = partition(a, lo, hi, comparator);
            quickSort(a, lo, j-1, comparator);
            quickSort(a, j+1, hi, comparator);
        }

        private static <Key> int partition(Key[] a, int lo, int hi, Comparator<? super Key> comparator) {
            Key pivot = a[lo];
            int i = lo+1, j = hi;
            while (true) {
                while (i <= hi && comparator.compare(a[i], pivot) < 0) i++;
                while (comparator.compare(a[j], pivot) > 0) j--;
                if (i >= j) break;
                exch(a, i++, j--);
            }
            exch(a, lo, j);
            return j;
        }

        /** Shuffle for randomized QuickSort/QuickSelect */
        private static <Key> void shuffle(Key[] a) {
            Random random = new Random();
            for (int i = a.length-1; i > 0; i--) {
                int r = random.nextInt(i+1);
                Key temp = a[i]; a[i] = a[r]; a[r] = temp;
            }
        }

        /** ---------------- QuickSelect ---------------- */
        public static <Key> Key quickSelect(Key[] a, int k, Comparator<? super Key> comparator) {
            if (k < 0 || k >= a.length) throw new IllegalArgumentException("Index out of bounds");
            shuffle(a);
            int lo = 0, hi = a.length-1;
            while (hi > lo) {
                int j = partition(a, lo, hi, comparator);
                if (j < k) lo = j+1;
                else if (j > k) hi = j-1;
                else return a[k];
            }
            return a[k];
        }
    }

    /** --------------------- Middleware --------------------- */
    public static class Middleware {

        public enum SortType { HEAP, MERGE, QUICK }

        public static <Key> void sort(Key[] arr, Comparator<? super Key> comparator, SortType type) {
            switch(type) {
                case HEAP -> ALGS4.heapSort(arr, comparator);
                case MERGE -> ALGS4.mergeSort(arr, comparator);
                case QUICK -> ALGS4.quickSort(arr, comparator);
                default -> throw new IllegalArgumentException("Unsupported SortType");
            }
        }

        public static <Key extends Comparable<Key>> void sort(Key[] arr, SortType type) {
            sort(arr, Comparator.naturalOrder(), type);
        }

        public static <Key> Key select(Key[] arr, int k, Comparator<? super Key> comparator) {
            return ALGS4.quickSelect(arr, k, comparator);
        }

        public static <Key extends Comparable<Key>> Key select(Key[] arr, int k) {
            return select(arr, k, Comparator.naturalOrder());
        }

        public static <Key> ALGS4.PriorityQueue<Key> priorityQueue(int initCapacity, Comparator<? super Key> comparator) {
            return new ALGS4.PriorityQueue<>(initCapacity, comparator);
        }

        public static <Key extends Comparable<Key>> ALGS4.PriorityQueue<Key> minPQ(int initCapacity) {
            return new ALGS4.PriorityQueue<>(initCapacity, Comparator.naturalOrder());
        }

        public static <Key extends Comparable<Key>> ALGS4.PriorityQueue<Key> maxPQ(int initCapacity) {
            return new ALGS4.PriorityQueue<>(initCapacity, Comparator.reverseOrder());
        }
    }
}
