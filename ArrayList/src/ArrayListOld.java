import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
/**
 * Your implementation of an ArrayList.
 *
 * @author Owen Kew
 * @version 1.0
 * @userid okew3
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayListOld<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayListOld() {
        this.size = 0;
        Object[] bArray = new Object[INITIAL_CAPACITY];
        this.backingArray = (T[]) bArray;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *When Resizing only travese one time through the array
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (size == backingArray.length - 1) {
            T[] oldBack = backingArray;
            this.resize();
            if (index == size) {
                for (int i = 0; i < size; i++) {
                    backingArray[i] = oldBack[i];
                }
                backingArray[size] = data;
            } else if (index == 0) {
                backingArray[0] = data;
                for (int j = 1; j < size; j++) {
                    backingArray[j] = oldBack[j];
                }
            } else {
                for (int k = 0; k < index; k++) {
                    backingArray[k] = oldBack[k];
                }
                backingArray[index] = data;
                for (int l = index; l <= size; l++) {
                    backingArray[l] = oldBack[l];
                }
            }
            size++;
        } else {
            if (index == size) {
                backingArray[size] = data;
                size++;
            } else {
                T newData = data;
                T oldObj = backingArray[index];
                for (int i = index; i < size - 1; i++) {
                    backingArray[i] = newData;
                    newData = oldObj;
                    oldObj = backingArray[i + 1];
                }
                size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        T newData = data;
        T oldObj = backingArray[0];
        for (int i = 0; i <= size; i++) {
                if (i == size - 1 && size == backingArray.length - 1) {
                    T[] oldBack = backingArray;
                    this.resize();
                    backingArray[0] = data;
                    for (int j = 1; j < size; j++) {
                        backingArray[j] = oldBack[j];
                    }
                } else {
                    backingArray[i] = newData;
                    newData = oldObj;
                    oldObj = backingArray[i + 1];
                }
        }
        this.size = size + 1;
        if (data == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (size == backingArray.length) {
            T[] oldBack = backingArray;
            this.resize();
            for (int i = 0; i < size; i++) {
                backingArray[i] = oldBack[i];
            }
            backingArray[size] = data;
            size++;
        } else {
            backingArray[size] = data;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T oldObj  = backingArray[index];
        backingArray[index] = null;
        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
            backingArray[i + 1] = null;
        }
        size--;
        return oldObj;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty() == true) {
            throw new NoSuchElementException();
        }
        T oldObj  = backingArray[0];
        backingArray[0] = null;
        for (int i = 0; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
            backingArray[i + 1] = null;
        }
        size--;
        return oldObj;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.isEmpty() == true) {
            throw new NoSuchElementException();
        }
        T oldObj = backingArray[--size];
        backingArray[size] = null;
        return oldObj;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (this.get(0) == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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
    private void resize() {
        this.backingArray = (T[]) new Object[backingArray.length * 2];
    }
    public String toString() {
        String elements = "";
        for (int i = 0; i < size; i++) {
            elements = elements + ", " + backingArray[i];
        }
        return String.format("%s is the array. %d is the size. The backing array length is %d", elements, size, backingArray.length);
    }
}
