import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Here are some tests for the Doubly Linked List homework. Please let me know
 * if anything is wrong with the test :)
 *
 * @version 1.0
 * @author Vikrant K. Bathala
 */

public class DoublyLinkedListVBathalaTest {
    private static final int TIMEOUT = 200;
    private DoublyLinkedList<String> list;

    @Before
    public void setup() {
        list = new DoublyLinkedList<>();
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddIndexIllegalArgument() {
        list.addAtIndex(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFrontIllegalArgument() {
        list.addToFront(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddBackIllegalArgument() {
        list.addToBack(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddIndexOutOfBounds1() {
        list.addAtIndex(-1, "1a");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddIndexOutOfBounds2() {
        list.addAtIndex(list.size() + 1, "1a");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOutOfBounds1() {
        list.removeAtIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOutOfBounds2() {
        list.removeAtIndex(list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.addAtIndex(0, "0a"); // 0a
        list.addAtIndex(0, "1a"); // 1a, 0a
        list.addAtIndex(2, "2a"); // 1a, 0a, 2a
        list.addAtIndex(1, "3a"); // 1a, 3a, 0a, 2a
        list.addAtIndex(3, "4a"); // 1a, 3a, 0a, 2a, 4a
        list.clear();
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFrontEmptyList() {
        list.clear();
        assertNull(list.getHead());
        assertEquals(0, list.size());
        list.removeFromFront();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveBackEmptyList() {
        list.clear();
        assertNull(list.getHead());
        assertEquals(0, list.size());
        list.removeFromFront();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds1() {
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds2() {
        list.get(list.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastOccurrenceIllegalArgument() {
        list.removeLastOccurrence(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testLastOccurrenceNoElement() {
        list.clear();
        list.addAtIndex(0, "5a"); // 5a
        list.addAtIndex(0, "4a"); // 4a, 5a
        list.addAtIndex(0, "3a"); // 3a, 4a, 5a
        list.addAtIndex(0, "2a"); // 2a, 3a, 4a, 5a
        list.addAtIndex(0, "0a"); // 0a, 2a, 3a, 4a, 5a
        list.removeLastOccurrence("1a");
    }

    @Test(timeout = TIMEOUT)
    public void testAddIndex() {
        list.clear();
        list.addAtIndex(0, "3a"); // 3a
        list.addAtIndex(list.size(), "1a"); // 3a, 1a
        list.addAtIndex(1, "4a"); // 3a, 4a, 1a
        list.addAtIndex(2, "6a"); // 3a, 4a, 6a, 1a
        list.addAtIndex(3, "8a"); // 3a, 4a, 6a, 8a, 1a
        list.addAtIndex(5, "5a"); // 3a, 4a, 6a, 8a, 1a, 5a
        list.addAtIndex(4, "9a"); // 3a, 4a, 6a, 8a, 9a, 1a, 5a

        assertEquals("3a", list.get(0));
        assertEquals("4a", list.get(1));
        assertEquals("6a", list.get(2));
        assertEquals("8a", list.get(3));
        assertEquals("9a", list.get(4));
        assertEquals("1a", list.get(5));
        assertEquals("5a", list.get(6));
    }

    @Test(timeout = TIMEOUT)
    public void testAddFront() {
        list.clear();
        list.addToFront("1a"); // 1a

        assertEquals("1a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());
        assertEquals("1a", list.get(0));
        assertEquals(1, list.size());

        list.addToFront("0a"); // 0a, 1a
        assertEquals("0a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());
        assertEquals(list.getHead().getNext(), list.getTail());
        assertEquals(list.getTail().getPrevious(), list.getHead());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testAddBack() {
        list.clear();
        list.addToBack("0a"); // 0a

        assertEquals("0a", list.getTail().getData());
        assertEquals("0a", list.getHead().getData());
        assertEquals("0a", list.get(0));
        assertEquals(1, list.size());

        list.addToBack("1a"); // 0a, 1a
        assertEquals("1a", list.getTail().getData());
        assertEquals("0a", list.getHead().getData());
        assertEquals(list.getTail().getPrevious(), list.getHead());
        assertEquals(list.getHead().getNext(), list.getTail());
        assertNull(list.getTail().getNext());
        assertNull(list.getHead().getPrevious());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveIndex() {
        list.clear();
        list.addAtIndex(0, "3a"); // 3a
        list.addAtIndex(list.size(), "1a"); // 3a, 1a
        list.addAtIndex(1, "4a"); // 3a, 4a, 1a
        list.addAtIndex(2, "6a"); // 3a, 4a, 6a, 1a
        list.addAtIndex(3, "8a"); // 3a, 4a, 6a, 8a, 1a
        list.addAtIndex(5, "5a"); // 3a, 4a, 6a, 8a, 1a, 5a
        list.addAtIndex(4, "9a"); // 3a, 4a, 6a, 8a, 9a, 1a, 5a

        list.removeAtIndex(0); // 4a, 6a, 8a, 9a, 1a, 5a
        list.removeAtIndex(list.size() - 1); // 4a, 6a, 8a, 9a, 1a
        list.removeAtIndex(1); // 4a, 8a, 9a, 1a
        list.removeAtIndex(list.size() - 2); // 4a, 8a, 1a
        list.removeAtIndex(1); // 4a, 1a

        assertEquals("4a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("4a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());
        assertEquals(list.getHead().getNext(), list.getTail());
        assertEquals(list.getTail().getPrevious(), list.getHead());
        assertEquals(2, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFront() {
        list.addAtIndex(0, "1a"); // 1a
        list.addAtIndex(0, "0a"); // 0a, 1a

        assertEquals("0a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());

        list.removeFromFront(); // 1a

        assertEquals("1a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());
        assertEquals(list.getHead(), list.getTail());
        assertEquals(1, list.size());

        list.removeFromFront(); // empty
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveBack() {
        list.addAtIndex(0, "1a"); // 1a
        list.addAtIndex(0, "0a"); // 0a, 1a

        assertEquals("0a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());

        list.removeFromBack(); // 0a

        assertEquals("0a", list.getHead().getData());
        assertEquals("0a", list.getTail().getData());
        assertEquals(list.getHead(), list.getTail());
        assertEquals(1, list.size());

        list.removeFromBack(); // empty

        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testLastOccurrenceData() {
        list.clear();
        list.addAtIndex(0, new String("5a")); // 5a
        list.addAtIndex(0, new String("4a")); // 4a, 5a
        list.addAtIndex(0, new String("3a")); // 3a, 4a, 5a
        list.addAtIndex(0, new String("2a")); // 2a, 3a, 4a, 5a
        list.addAtIndex(0, new String("1a")); // 1a, 2a, 3a, 4a, 5a
        list.addAtIndex(0, new String("0a")); // 0a, 1a, 2a, 3a, 4a, 5a
        String input = new String("1a");
        String output = list.removeLastOccurrence(input);
        assertTrue(input.equals(output) && !(input == output));
    }

    @Test(timeout = TIMEOUT)
    public void testLastOccurrence() {
        list.addAtIndex(0, "1a"); // 1a
        list.addAtIndex(0, "0a"); // 0a, 1a

        assertEquals("0a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());

        list.removeLastOccurrence("0a"); // 1a

        assertEquals(list.getHead(), list.getTail());
        assertEquals("1a", list.getHead().getData());
        assertEquals("1a", list.getTail().getData());
        assertEquals(1, list.size());

        list.removeLastOccurrence("1a"); // empty

        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());

    }

    @Test (timeout = TIMEOUT)
    public void testToArrayEmpty() {
        list.clear();
        assertArrayEquals(new String[0], list.toArray());
    }

}
