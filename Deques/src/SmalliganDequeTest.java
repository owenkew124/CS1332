import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.concurrent.ConcurrentLinkedDeque;

import static org.junit.Assert.assertEquals;

public class SmalliganDequeTest {

    private ArrayDeque<Integer> arrayDeque;
    private LinkedDeque<Integer> linkedDeque;

    private ConcurrentLinkedDeque<Integer> expected;

    @Before
    public void setUp() {
        arrayDeque = new ArrayDeque<>();
        linkedDeque = new LinkedDeque<>();
        expected = new ConcurrentLinkedDeque<>();
    }

    private void checkArrayDeque() {
        for (int i = 0; i < 1000000; i++) {
            double r = Math.random();
            if (r > 0.75) {
                arrayDeque.addFirst(i);
                expected.addFirst(i);
                assertEquals(expected.getFirst(), arrayDeque.getFirst());
            } else if (r > 0.5) {
                arrayDeque.addLast(i);
                expected.addLast(i);
                assertEquals(expected.getLast(), arrayDeque.getLast());
            } else if (r > 0.25 && arrayDeque.size() > 0) {
                assertEquals(expected.removeFirst(), arrayDeque.removeFirst());
            } else if (arrayDeque.size() > 0) {
                assertEquals(expected.removeLast(), arrayDeque.removeLast());
            }
        }
    }

    private void checkLinkedDeque() {
        for (int i = 0; i < 1000000; i++) {
            double r = Math.random();
            if (r > 0.75) {
                linkedDeque.addFirst(i);
                expected.addFirst(i);
                assertEquals(expected.getFirst(), linkedDeque.getFirst());
            } else if (r > 0.5) {
                linkedDeque.addLast(i);
                expected.addLast(i);
                assertEquals(expected.getLast(), linkedDeque.getLast());
            } else if (r > 0.25 && linkedDeque.size() > 0) {
                assertEquals(expected.removeFirst(), linkedDeque.removeFirst());
            } else if (linkedDeque.size() > 0) {
                assertEquals(expected.removeLast(), linkedDeque.removeLast());
            }
        }
    }

    @Test(timeout = 20000)
    public void checkArrayDequeRun() {
        for (int i = 0; i < 100; i++) {
            arrayDeque = new ArrayDeque<>();
            expected = new ConcurrentLinkedDeque<>();
            checkArrayDeque();
        }
    }

    @Test(timeout = 20000)
    public void checkLinkedDequeRun() {
        for (int i = 0; i < 100; i++) {
            linkedDeque = new LinkedDeque<>();
            expected = new ConcurrentLinkedDeque<>();
            checkLinkedDeque();
        }
    }

}
