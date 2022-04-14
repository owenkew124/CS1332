import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Owen Kew
 * @version 1.0
 * @userid okew3
 * @GTID 903592179
 *
 * Collaborators: None
 *
 * Resources: None
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index cannot be negative or larger than the size of the list");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else if (index == size) {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }  else {
            if (index <= size / 2) {
                DoublyLinkedListNode<T> curr = head;
                int i = 0;
                while (i < index - 1) {
                    curr = curr.getNext();
                    i++;
                }
                DoublyLinkedListNode<T> next = curr.getNext();
                next.setPrevious(newNode);
                curr.setNext(newNode);
                newNode.setNext(next);
                newNode.setPrevious(curr);
            } else {
                int iFromBck = size - index;
                DoublyLinkedListNode<T> curr = tail;
                int i = 0;
                while (i < iFromBck - 1) {
                    curr = curr.getPrevious();
                    i++;
                }
                DoublyLinkedListNode<T> prev = curr.getPrevious();
                prev.setNext(newNode);
                curr.setPrevious(newNode);
                newNode.setNext(curr);
                newNode.setPrevious(prev);
            }
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;

    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (tail == null && head == null) {
            tail = newNode;
            head = newNode;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is either negative or larger than the size");
        }
        DoublyLinkedListNode<T> removed;
        if (size == 1) {
            removed = head;
            head = null;
            tail = null;
            size--;
            return removed.getData();
        } else if (index == 0) {
            removed = head;
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return removed.getData();
        } else if (index == size - 1) {
            removed = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return removed.getData();
        } else {
            if (index <= size / 2) {
                DoublyLinkedListNode<T> curr = head;
                int i = 0;
                while (i < index) {
                    curr = curr.getNext();
                    i++;
                }
                removed = curr;
                DoublyLinkedListNode<T> next = curr.getNext();
                DoublyLinkedListNode<T> prev = curr.getPrevious();
                next.setPrevious(prev);
                prev.setNext(next);
                size--;
                return removed.getData();
            } else {
                int iFromBck = size - index;
                DoublyLinkedListNode<T> curr = tail;
                int i = 0;
                while (i < iFromBck - 1) {
                    curr = curr.getPrevious();
                    i++;
                }
                removed = curr;
                DoublyLinkedListNode<T> prev = curr.getPrevious();
                DoublyLinkedListNode<T> next = curr.getNext();
                prev.setNext(next);
                next.setPrevious(prev);
                size--;
                return removed.getData();
            }
        }

    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty so nothing can be removed");
        }
        DoublyLinkedListNode<T> removed = head;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return removed.getData();
        } else {
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return removed.getData();
        }
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
        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty so nothing can be removed");
        }
        DoublyLinkedListNode<T> removed = tail;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return removed.getData();
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return removed.getData();
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is either negative or larger than the size");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null && tail == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null");
        }
        DoublyLinkedListNode<T> curr = tail;
        for (int i = 0; i < size; i++) {
            if (curr.getData().equals(data)) {
                if (size == 1) {
                    head = null;
                    tail = null;
                    size--;
                    return curr.getData();
                } else if (curr == head) {
                    head = head.getNext();
                    head.setPrevious(null);
                    size--;
                    return curr.getData();
                } else if (curr == tail) {
                    tail = tail.getPrevious();
                    tail.setNext(null);
                    size--;
                    return curr.getData();
                } else {
                    DoublyLinkedListNode<T> prev = curr.getPrevious();
                    DoublyLinkedListNode<T> next = curr.getNext();
                    prev.setNext(next);
                    next.setPrevious(prev);
                    size--;
                    return curr.getData();
                }
            } else {
                curr = curr.getPrevious();

            }
        }

        throw new NoSuchElementException("The data is not found in the list");
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}
