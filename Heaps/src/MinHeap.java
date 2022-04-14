import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Owen Kew
 * @version 1.0
 * @userid okew3
 * @GTID 903592179
 *
 * Collaborators:
 *
 * Resources:
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        this.size = 0;
        T[] bA = (T[]) new Comparable[INITIAL_CAPACITY];
        this.backingArray = bA;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("ArrayList cannot be null");
        }
        this.size = data.size();
        T[] bA = (T[]) new Comparable[data.size() * 2 + 1];
        this.backingArray = bA;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("The data in the Arraylist cannot be null");
            }
            bA[i + 1] = data.get(i);
        }
        for (int i = size/2; i > 0; i--) {
            buildHelper(backingArray[i], i);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null");
        }
        if (size == backingArray.length - 1) {
            T[] bA = (T[]) new Comparable[2 * backingArray.length];
            T[] oBA = backingArray;
            for (int i = 1; i <= size; i++) {
                bA[i] = oBA[i];
            }
            backingArray = bA;
            buildHelper(backingArray[1], 1);
        }
        size++;
        backingArray[size] = data;
        upHeap(data, size);
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty so nothing can be removed");
        }
        T removed = backingArray[1];
        T replace = backingArray[size];
        backingArray[1] = replace;
        backingArray[size] = null;
        size--;
        downHeap(replace, 1);
        return removed;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty so there is no min to get");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        this.size = 0;
        T[] bA = (T[]) new Comparable[INITIAL_CAPACITY];
        this.backingArray = bA;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Helper method for the up heap operation. Used to
     * maintain the order property during the add operation
     *
     * @param data the data needs its order check
     * @param index the index of the data that needs its order checked
     */
    private void upHeap(T data, int index) {
        if (index / 2 < 1) {
            return;
        }
        T parent = backingArray[index / 2];
        int parentInd = index / 2;
        if (data.compareTo(parent) < 0) {
            backingArray[index] = parent;
            backingArray[index / 2] = data;
            upHeap(data, parentInd);
        }
    }

    /**
     * Helper method for the down heap operation. Used to
     * maintain the order property during the remove operation.
     *
     * @param data the data that needs its order checked
     * @param index the index of the data that needs its order checked
     */
    private void downHeap(T data, int index) {

        if (backingArray[index * 2] == null && backingArray[index * 2 + 1] == null) {
            return;
        }
        if (backingArray[index * 2 + 1] == null) {
            T leftChild = backingArray[index * 2 ];
            int leftChildInd = index * 2;
            if (leftChild.compareTo(data) < 0) {
                backingArray[index] = leftChild;
                backingArray[leftChildInd] = data;
                downHeap(data, leftChildInd);
            }
        } else if (backingArray[index * 2 ] == null) {
            T rightChild = backingArray[index * 2 + 1];
            int rightChildInd = index * 2 + 1;
            if (rightChild.compareTo(data) < 0) {
                backingArray[index] = rightChild;
                backingArray[rightChildInd] = data;
                downHeap(data, rightChildInd);
            }
        } else {
            T rightChild = backingArray[index * 2 + 1];
            T leftChild = backingArray[index * 2];
            int rightChildInd = index * 2 + 1;
            int leftChildInd = index * 2;
            if (rightChild.compareTo(leftChild) < 0) {
                if (rightChild.compareTo(data) < 0) {
                    backingArray[index] = rightChild;
                    backingArray[rightChildInd] = data;
                    downHeap(data, rightChildInd);
                }
            } else {
                if (leftChild.compareTo(data) < 0) {
                    backingArray[index] = leftChild;
                    backingArray[leftChildInd] = data;
                    downHeap(data, leftChildInd);
                }
            }
        }
    }

    /**
     * Helper method for the build heap operation to help
     * maintain the order property of the heap
     *
     * @param data the data that needs its order checked
     * @param index the index of the data that needs its order checked
     */
    private void buildHelper(T data, int index) {
        if (index == 0) {
            return;
        }
        int left = index * 2;
        int right = index * 2 + 1;
        if (left <= size) {
            if (right <= size) {
                if (backingArray[left].compareTo(backingArray[right]) < 0) {
                    if (backingArray[left].compareTo(data) < 0) {
                        backingArray[index] = backingArray[left];
                        backingArray[left] = data;
                        buildHelper(backingArray[left], left);
                    }
                } else {
                    if (backingArray[right].compareTo(data) < 0) {
                        backingArray[index] = backingArray[right];
                        backingArray[right] = data;
                        buildHelper(backingArray[right], right);
                    }
                }
            } else {
                if (backingArray[left].compareTo(data) < 0) {
                    backingArray[index] = backingArray[left];
                    backingArray[left] = data;
                    buildHelper(backingArray[left], left);
                }
            }
        } else {
            if (right <= size) {
                if (backingArray[right].compareTo(data) < 0) {
                    backingArray[index] = backingArray[right];
                    backingArray[right] = data;
                    buildHelper(backingArray[right], right);
                }
            }
        }
    }
}
