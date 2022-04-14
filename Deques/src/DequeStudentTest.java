

/**
 * This is a basic set of unit tests for ArrayDeque and LinkedDeque.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class DequeStudentTest {
    public static void main(String[] args) {
        ArrayDeque<String> s = new ArrayDeque<>();
        s.addFirst("0");
        System.out.println(s.toString());
        s.addFirst("1");
        System.out.println(s.toString());
        s.addFirst("2");
        System.out.println(s.toString());
        s.addFirst("3");
        System.out.println(s.toString());
        s.addFirst("4");
        System.out.println(s.toString());
        s.addFirst("5");
        System.out.println(s.toString());
        s.addFirst("6");
        System.out.println(s.toString());
        s.addFirst("7");
        System.out.println(s.toString());
        s.addFirst("8");
        System.out.println(s.toString());
        s.addLast("9");
        System.out.println(s.toString());
        s.removeFirst();
        System.out.println(s.toString());
        s.removeFirst();
        System.out.println(s.toString());
        s.removeFirst();
        System.out.println(s.toString());
        s.removeFirst();
        System.out.println(s.toString());
        s.removeFirst();
        System.out.println(s.toString());
        s.removeFirst();
        System.out.println(s.toString());
        s.removeFirst();
        s.removeFirst();
        s.removeFirst();
        System.out.println(s.toString());
        s.addLast("10");
        s.addLast("11");
        s.removeLast();
        s.removeLast();
        System.out.println(s.toString());


    }
}
