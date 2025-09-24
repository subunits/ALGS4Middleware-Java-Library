
# ALGS4Middleware Java Library

**ALGS4Middleware** is a self-contained Java library implementing core ALGS4 algorithms for sorting, selection, and priority queues. It provides a unified middleware API for easy usage in any project.

## Features

- **Sorting**
  - HeapSort
  - MergeSort
  - QuickSort (randomized)
  - Supports ascending/descending via `Comparator`
- **Selection**
  - QuickSelect (find k-th smallest element)
- **Priority Queue**
  - MinPQ / MaxPQ
  - Custom comparator
  - Iterable

- Fully generic and dynamic
- ALGS4-style API
- Middleware layer for unified access

## Installation

1. Copy `ALGS4Middleware.java` into your Java project.
2. Include it in your package or default namespace.
3. Optionally, use `Main.java` as a demo.

## Usage

### Sorting

```java
Integer[] arr = {10,3,5,1,20,8};

// QuickSort ascending
ALGS4Middleware.Middleware.sort(arr, ALGS4Middleware.Middleware.SortType.QUICK);

// MergeSort descending
ALGS4Middleware.Middleware.sort(arr, Comparator.reverseOrder(), ALGS4Middleware.Middleware.SortType.MERGE);

// HeapSort ascending
ALGS4Middleware.Middleware.sort(arr, ALGS4Middleware.Middleware.SortType.HEAP);
```

### Selection (QuickSelect)

```java
Integer[] arr = {10,3,5,1,20,8};
Integer kth = ALGS4Middleware.Middleware.select(arr, 2); // 3rd smallest
```

### Priority Queue

```java
var minPQ = ALGS4Middleware.Middleware.minPQ(2);
minPQ.insert(10);
minPQ.insert(5);
minPQ.insert(20);
System.out.println(minPQ.peek()); // 5

// Iterating
for (int x : minPQ) System.out.print(x + " ");
```

### Custom Comparator

```java
var maxPQ = ALGS4Middleware.Middleware.priorityQueue(2, Comparator.reverseOrder());
maxPQ.insert(10);
maxPQ.insert(5);
maxPQ.insert(20);
System.out.println(maxPQ.peek()); // 20
```

## Notes

- `QuickSort` is randomized for better average performance.
- `QuickSelect` finds the k-th smallest element in O(n) expected time.
- Priority Queues are dynamic and resize automatically.
- All classes are generic and comparator-driven for flexibility.

## License

MIT License
