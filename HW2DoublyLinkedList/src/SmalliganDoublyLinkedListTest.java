import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * These tests are intended to be as comprehensive as is feasible.
 * Please let me know if there are any cases that are not covered.
 *
 * @author Jack Smalligan
 * @version 1.0
 */
public class SmalliganDoublyLinkedListTest {

    private DoublyLinkedList<Integer> list;
    private DoublyLinkedList<Data> removeLastList;

    private enum TestType {
        FRONT, BACK, INDEX, INDEX_FIRST, INDEX_SECOND, REMOVE_LAST, REMOVE_LAST_FIRST, REMOVE_LAST_SECOND
    }

    @Rule
    public Timeout globalTimeout = Timeout.millis(200);

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
        removeLastList = new DoublyLinkedList<>();
    }

    // -----------------------------------------------------
    // Miscellaneous tests: init, isEmpty, clear

    @Test
    public void testInitialization() {
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test
    public void isEmptyTest() {
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        list.addToFront(0);
        assertFalse(list.isEmpty());

        for (int i = 0; i < 10; i++) {
            list.addToFront(i);
            assertFalse(list.isEmpty());
        }
    }

    @Test
    public void clearTest() {
        list.addToFront(0);
        list.addToFront(0);
        assertEquals(2, list.size());
        assertNotNull(list.getHead());
        assertNotNull(list.getTail());

        list.clear();

        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
    }

    // End of miscellaneous tests
    // -----------------------------------------------------

    // -----------------------------------------------------
    // adding tests
    @Test
    public void addOneElementFront() {
        addOneElementTest(TestType.FRONT);
    }

    @Test
    public void addOneElementBack() {
        addOneElementTest(TestType.BACK);
    }

    @Test
    public void addOneElementIndex() {
        addOneElementTest(TestType.INDEX);
    }

    @Test
    public void testAddAtIndex() {

        // {}
        list.addAtIndex(0, 4);
        // {4}
        list.addAtIndex(1, 7);
        // {4, 7}
        list.addAtIndex(1, 6);
        // {4, 6, 7}
        list.addAtIndex(0, 3);
        // {3, 4, 6, 7}
        list.addAtIndex(0, 0);
        // {0, 3, 4, 6, 7}
        list.addAtIndex(3, 5);
        // {0, 3, 4, 5, 6, 7}
        list.addAtIndex(1, 1);
        // {0, 1, 3, 4, 5, 6, 7}
        list.addAtIndex(2, 2);
        // {0, 1, 2, 3, 4, 5, 6, 7}
        list.addAtIndex(8, 9);
        // {0, 1, 2, 3, 4, 5, 6, 7, 9}
        list.addAtIndex(8, 8);
        // {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}

        assertEquals(10, list.size());

        testThroughZeroNine();
    }

    @Test
    public void testAddToFront() {
        for (int i = 9; i >= 0; i--) {
            assertEquals(9 - i, list.size());
            list.addToFront(i);
        }

        testThroughZeroNine();
    }

    @Test
    public void testAddToBack() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, list.size());
            list.addToBack(i);
        }

        testThroughZeroNine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgAddFront() {
        list.addToFront(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgAddBack() {
        list.addToBack(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void badIndexAddNegative() {
        list.addAtIndex(-1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void badIndexAddEmpty() {
        list.addAtIndex(1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void badIndexAddNotEmpty() {
        list.addToBack(0);
        list.addAtIndex(1, 0);
        list.addAtIndex(3, 0);
    }

    // End of adding tests
    // -----------------------------------------------------

    // -----------------------------------------------------
    // remove Tests

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeNegative() {
        list.addAtIndex(0, 0);
        list.removeAtIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeOutOfBounds() {
        list.addAtIndex(0, 0);
        list.addAtIndex(0, 1);
        list.removeAtIndex(2);
    }

    @Test
    public void removeAtIndexMultiElementTest() {
        for (int i = 0; i < 10; i++) {
            list.addToBack(i);
        }

        // {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}

        list.addAtIndex(7, 7);
        // {0, 1, 2, 3, 4, 5, 6, 7, 7, 8, 9}
        assertEquals(11, list.size());
        assertEquals((Integer) 7, list.removeAtIndex(7));
        // {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        testThroughZeroNine();

        list.addAtIndex(3, 3);
        // {0, 1, 2, 3, 3, 4, 5, 6, 7, 8, 9}
        assertEquals(11, list.size());
        assertEquals((Integer) 3, list.removeAtIndex(3));
        // {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        testThroughZeroNine();

        list.addAtIndex(10, 9);
        assertEquals((Integer) 9, list.removeAtIndex(9)); // second to last index
        testThroughZeroNine();

        list.addAtIndex(0, 0);
        assertEquals((Integer) 0, list.removeAtIndex(1)); // second from front index
        testThroughZeroNine();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromFrontEmpty() {
        list.removeFromFront();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromBackEmpty() {
        list.removeFromBack();
    }

    @Test
    public void removeFromFrontMultiElementTest() {
        for (int i = 0; i < 10; i++) {
            list.addToBack(i);
        }

        list.addToFront(-1);
        assertEquals((Integer) (-1), list.removeFromFront());
        testThroughZeroNine();
    }

    @Test
    public void removeFromBackMultiElementTest() {
        for (int i = 0; i < 10; i++) {
            list.addToBack(i);
        }

        list.addToBack(10);
        assertEquals((Integer) 10, list.removeFromBack());
        testThroughZeroNine();
    }

    @Test
    public void removeSingleElementFront() {
        removeSingleElementList(TestType.FRONT);
    }

    @Test
    public void removeSingleElementBack() {
        removeSingleElementList(TestType.BACK);
    }

    @Test
    public void removeSingleElementIndex() {
        removeSingleElementList(TestType.INDEX);
    }

    @Test
    public void removeTwoElementsFront() {
        testTwoElementRemoval(TestType.FRONT);
    }

    @Test
    public void removeTwoElementsBack() {
        testTwoElementRemoval(TestType.BACK);
    }

    @Test
    public void removeTwoElementsIndexFirst() {
        testTwoElementRemoval(TestType.INDEX_FIRST);
    }

    @Test
    public void removeTwoElementsIndexSecond() {
        testTwoElementRemoval(TestType.INDEX_SECOND);
    }

    // End of remove tests
    // -----------------------------------------------------


    // -----------------------------------------------------
    // get() Tests

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegative() {
        list.addToFront(0);
        list.get(-1);
    }

    @Test
    public void testGetOutOfBounds() {
        list.addToFront(0);
        try {
            list.get(1);
            fail("IndexOutOfBounds not thrown for access at index = size = 1");
        } catch (IndexOutOfBoundsException e) { }

        list.addAtIndex(0, 1);

        try {
            list.get(2);
            fail("IndexOutOfBounds not thrown for access at index = size = 2");
        } catch (IndexOutOfBoundsException e) { }

    }

    @Test
    public void getFront() {
        list.addToFront(0);
        list.addToFront(1);
        list.addToFront(2);

        assertEquals((Integer) 2, list.get(0));
    }

    @Test
    public void getBack() {
        list.addToFront(0);
        list.addToFront(1);
        list.addToFront(2);

        assertEquals((Integer) 2, list.get(0));
    }

    @Test
    public void getWithTransverse() {
        for (int i = 0; i < 10; i++) {
            list.addToBack(i);
        }
        // this test will have gets that iterate from the front and back
        testGetZeroNine();
    }

    // End of get() tests
    // -----------------------------------------------------

    // -----------------------------------------------------
    // removeLastOccurrence tests

    @Test(expected = IllegalArgumentException.class)
    public void removeLastOccurrenceNull() {
        list.addToFront(0);

        list.removeLastOccurrence(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastNoSuchElementEmpty() {
        list.removeLastOccurrence(0);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastNoSuchElementNotEmpty() {
        list.addToFront(0);
        list.addAtIndex(0, 1);
        list.removeLastOccurrence(2);
    }

    @Test
    public void removeLastEnsureReferenceToContentsReturned() {
        for (int i = 0; i < 5; i++) {
            removeLastList.addToBack(new Data(i));
        }

        for (int i = 4; i >= 0; i--) {
            Data search = new Data(i);
            Data toBeFound = removeLastList.get(i);

            Data found = removeLastList.removeLastOccurrence(search);

            assertEquals(search, found);
            assertSame(found, toBeFound);
            assertNotSame(search, found);
        }

        assertTrue(removeLastList.isEmpty());
    }

    @Test
    public void removeLast() {
        for (int i = 0; i < 10; i++) {
            list.addToBack(i);
        }
        testThroughZeroNine();

        for (int i = 0; i < 10; i++) {
            list.addAtIndex(i, i);
            assertEquals((Integer) i, list.removeLastOccurrence(i));
            testThroughZeroNine();
        }

        for (int i = 0; i < 10; i++) {
            list.addToBack(i);
        }

        for (int i = 9; i >= 0; i--) {
            assertEquals((Integer) i, list.removeLastOccurrence(i));
        }
        testThroughZeroNine();

        // clear and do some more tests
        list.clear();

        list.addToFront(0);
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);

        list.addToFront(1);
        // 1, 3, 2, 1, 0
        assertEquals((Integer) 1, list.get(0));
        assertEquals((Integer) 3, list.get(1));
        assertEquals((Integer) 2, list.get(2));
        assertEquals((Integer) 1, list.get(3));
        assertEquals((Integer) 0, list.get(4));

        assertEquals((Integer) 1, list.removeLastOccurrence(1));

        // 1, 3, 2, 0
        assertEquals((Integer) 1, list.get(0));
        assertEquals((Integer) 3, list.get(1));
        assertEquals((Integer) 2, list.get(2));
        assertEquals((Integer) 0, list.get(3));

    }

    @Test
    public void removeLastAtTail() {
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(2);

        assertEquals((Integer) 2, list.removeLastOccurrence(2));

        assertEquals(list.getHead().getData(), (Integer) 0);
        assertEquals(list.getTail().getData(), (Integer) 1);
        assertSame(list.getHead().getNext(), list.getTail());
        assertSame(list.getTail().getPrevious(), list.getHead());
    }

    @Test
    public void removeLastAtHead() {
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(2);

        assertEquals((Integer) 0, list.removeLastOccurrence(0));

        assertEquals(list.getHead().getData(), (Integer) 1);
        assertEquals(list.getTail().getData(), (Integer) 2);
        assertSame(list.getHead().getNext(), list.getTail());
        assertSame(list.getTail().getPrevious(), list.getHead());
    }

    @Test
    public void removeLastTwoElements() {
        testTwoElementRemoval(TestType.REMOVE_LAST_FIRST);
        list.clear();
        testTwoElementRemoval(TestType.REMOVE_LAST_SECOND);
    }

    @Test
    public void removeLastOneElement() {
        removeSingleElementList(TestType.REMOVE_LAST);
    }

    // end of removeLastOccurrence tests
    // -----------------------------------------------------

    // -----------------------------------------------------
    // toArray() tests

    @Test
    public void testToArrayEmpty() {
        Integer[] expect = new Integer[0];
        assertArrayEquals(expect, list.toArray());
    }

    @Test
    public void testToArrayOneElement() {
        list.addToBack(1);
        Integer[] expect = new Integer[] {1};
        assertArrayEquals(expect, list.toArray());
    }

    @Test
    public void testToArray() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(i);
        }

        Integer[] expected = new Integer[] {0, 1, 2, 3, 4};
        assertArrayEquals(expected, list.toArray());

        list.addAtIndex(2, 5);
        expected = new Integer[] {0, 1, 5, 2, 3, 4};
        assertArrayEquals(expected, list.toArray());
    }

    // end of toArray() tests
    // -----------------------------------------------------

    // -----------------------------------------------------
    // Helper methods

    /**
     * Performs a test on adding a single element to the list
     * @param type Whether to add at front, back, or index
     */
    private void addOneElementTest(TestType type) {
        switch (type) {
        case FRONT:
            list.addToFront(0);
            break;
        case BACK:
            list.addToBack(0);
            break;
        case INDEX:
            list.addAtIndex(0, 0);
            break;
        default:
            throw new IllegalArgumentException("Bad type");
        }
        assertEquals(list.getHead(), list.getTail());
        assertNull(list.getHead().getNext());
        assertNull(list.getHead().getPrevious());
        assertEquals(1, list.size());
    }

    /**
     * Performs a test on removing the only element in a list of size 1
     *
     * @param type whether to remove from front, back, index
     */
    private void removeSingleElementList(TestType type) {
        list.addToFront(0);
        assertNotNull(list.getHead());
        assertEquals(list.getHead(), list.getHead());

        switch (type) {
        case FRONT:
            assertEquals((Integer) 0, list.removeFromFront());
            break;
        case BACK:
            assertEquals((Integer) 0, list.removeFromBack());
            break;
        case INDEX:
            assertEquals((Integer) 0, list.removeAtIndex(0));
            break;
        case REMOVE_LAST:
            assertEquals((Integer) 0, list.removeLastOccurrence(0));
            break;
        default:
            throw new IllegalArgumentException("Bad type");
        }

        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
    }

    /**
     * Performs a test on removing an element from a list with 2 elements;
     * checks head/tail afterwards
     *
     * @param type the type of test to do: front, back, index first, index second
     */
    private void testTwoElementRemoval(TestType type) {
        list.addToFront(0);
        list.addToBack(1);
        // {0, 1}
        assertNotEquals(list.getHead(), list.getTail());
        assertEquals(list.getHead().getNext(), list.getTail());
        assertEquals(list.getTail().getPrevious(), list.getHead());

        switch (type) {
        case FRONT:
            assertEquals((Integer) 0, list.removeFromFront());
            break;
        case BACK:
            assertEquals((Integer) 1, list.removeFromBack());
            break;
        case INDEX_FIRST:
            assertEquals((Integer) 0, list.removeAtIndex(0));
            System.out.println("removeatindex0");
            break;
        case INDEX_SECOND:
            assertEquals((Integer) 1, list.removeAtIndex(1));
            System.out.println("removeatindex1");
            break;
        case REMOVE_LAST_FIRST:
            assertEquals((Integer) 0, list.removeLastOccurrence(0));
            System.out.println("removeatlastoc0");
            break;
        case REMOVE_LAST_SECOND:
            assertEquals((Integer) 1, list.removeLastOccurrence(1));
            System.out.println("removeatlastoc1");
            break;
        default:
            throw new IllegalArgumentException("Bad type");
        }

        assertEquals(list.getHead(), list.getTail());
        assertNull(list.getHead().getNext());
        assertNull(list.getHead().getPrevious());
    }

    /**
     * This test iterates through from the front and the back,
     * checking the connections are correct for a list of the form:
     * head                                                 tail
     * |                                                    |
     * 0 <-> 1 <-> 2 <-> 3 <-> 4 <-> 5 <-> 6 <-> 7 -> 8 <-> 9
     *
     * Does not use get()/size(). Only uses the nodes' methods,
     * getHead(), and getTail()
     */
    private void testThroughZeroNine() {
        assertEquals(10, list.size());

        DoublyLinkedListNode<Integer> curr = list.getHead();

        // iterate forwards
        for (int i = 0; i < 9; i++) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getNext();
        }

        assertEquals((Integer) 9, curr.getData());
        assertEquals(list.getTail(), curr);

        // iterate backwards
        for (int i = 9; i > 0; i--) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getPrevious();
        }

        assertEquals((Integer) 0, curr.getData());
        assertEquals(list.getHead(), curr);
    }

    /**
     * This test iterates through from the front and the back,
     * checking the connections are correct for a list of the form:
     * {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
     *
     * Performs checks using get()
     */
    private void testGetZeroNine() {
        assertEquals(10, list.size());

        for (int i = 0; i < 10; i++) {
            assertEquals((Integer) i, list.get(i));
        }
    }

    // end of helper methods
    // -----------------------------------------------------

    // used to ensure that the last occurrence method returns the reference to data in the
    // list, not the one that is passed
    private static class Data {
        private final int val;

        /**
         * Construct with given val
         * @param val the value
         */
        public Data(int val) {
            this.val = val;
        }

        @Override
        public boolean equals(Object other) throws ClassCastException {
            return ((Data) other).val == val;
        }
    }
}
