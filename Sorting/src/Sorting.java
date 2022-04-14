import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Owen kew
 * @version 1.0
 * @userid okew3
 * @GTID 903592179
 *
 * Collaborators:
 *
 * Resources:
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int j = i + 1;
            while (j != 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                T replace = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = replace;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        }
        int startInd = 0;
        int endInd = arr.length - 1;
        boolean swapsMade = true;
        while (swapsMade) {
            swapsMade = false;
            int j = endInd;
            for (int i = startInd; i <= j - 1; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T swapped = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = swapped;
                    swapsMade = true;
                    endInd = i;
                }
            }
            if (swapsMade) {
                swapsMade = false;
                int k = startInd;
                for (int i = endInd; i > k; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T swapped = arr[i - 1];
                        arr[i - 1] = arr[i];
                        arr[i] = swapped;
                        swapsMade = true;
                        startInd = i;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        }
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int midInd = length / 2;
        T[] right;
        if (length % 2 == 0) {
            right = (T[]) new Object[midInd];
        } else {
            right = (T[]) new Object[midInd + 1];
        }
        T[] left = (T[]) new Object[midInd];
        int k = 0;
        for (int i = midInd; i < arr.length; i++) {
            right[k] = arr[i];
            k++;
        }
        k = 0;
        for (int i = 0; i < midInd; i++) {
            left[k] = arr[i];
            k++;
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int leftInd = 0;
        int rightInd = 0;
        while (leftInd != left.length && rightInd != right.length) {
            if (comparator.compare(left[leftInd], right[rightInd]) <= 0) {
                arr[leftInd + rightInd] = left[leftInd];
                leftInd++;
            } else {
                arr[leftInd + rightInd] = right[rightInd];
                rightInd++;
            }
        }
        while (leftInd != left.length) {
            arr[leftInd + rightInd] = left[leftInd];
            leftInd++;
        }
        while (rightInd != right.length) {
            arr[leftInd + rightInd] = right[rightInd];
            rightInd++;
        }


    }


    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("The array cannot be null");
        }
        int end = arr.length;
        int start = 0;
        quickSortHelper(arr, start, end, comparator, rand);
    }

    /**
     * Helper method for the quicksort sorting algorithm
     *
     * @param arr the array that needs to be sorted
     * @param start the current starting index for the quicksort algorithm
     * @param end the current end index for the quicksort algorithm
     * @param comparator the Comparator used to compare the data in the array
     * @param rand the Random object used to select pivots
     * @param <T> data type to sort
     */
    private static <T> void quickSortHelper(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if ((end - start) <= 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start) + start;
        T pivot = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = pivot;
        int i = start + 1;
        int j = end - 1;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                T replace = arr[j];
                arr[j] = arr[i];
                arr[i] = replace;
                i++;
                j--;
            }
        }
        T p = arr[start];
        arr[start] = arr[j];
        arr[j] = p;
        quickSortHelper(arr, start, j, comparator, rand);
        quickSortHelper(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null");
        }
        int max = 0;
        for (int value : arr) {
            if (Math.abs(value) > max) {
                max = value;
            }
        }
        int k = 0;
        while (max != 0) {
            max = max / 10;
            k++;
        }
        LinkedList<Integer>[] buckets = new LinkedList[20];
        for (int i = 0; i < 20; i++) {
            buckets[i] = new LinkedList<>();
        }
        int curr = 1;
        for (int i = 0; i <= k; i++) {
            for (int value : arr) {
                int bucket = (value / curr) % 10;
                buckets[bucket + 9].addLast(value);
            }
            curr = curr * 10;
            int index = 0;
            for (int bucket = 0; bucket <= 19; bucket++) {
                while (!(buckets[bucket].isEmpty())) {
                    arr[index] = buckets[bucket].removeFirst();
                    index++;
                }
            }



        }

    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];
        int i = 0;
        while (heap.peek() != null) {
            arr[i] = heap.poll();
            i++;
        }
        return arr;
    }

}
