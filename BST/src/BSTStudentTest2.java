import java.util.ArrayList;
/**
 * This is a basic set of unit tests for BST.
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
public class BSTStudentTest2 {
    public static void main(String[] args) {
        ArrayList arr = new ArrayList();
        arr.add(50);
        arr.add(75);
        arr.add(25);
        arr.add(63);
        arr.add(88);
        arr.add(13);
        arr.add(37);
        arr.add(99);
        arr.add(2);
        BST bst = new BST(arr);

        //bst.printRootAnd();
        //System.out.println(bst.inorder() + " inorder");
        //System.out.println(bst.postorder() + " postorder");
        //System.out.println(bst.kLargest(6) + " klargest");
        //System.out.println(bst.levelorder() + " level");
        //System.out.println(bst.contains(13) + " contains");
        //System.out.println(bst.contains(99) + " contains");
        //System.out.println(bst.contains(13) + " contains");
        //System.out.println(bst.contains(14) + " contains");
        //System.out.println(bst.preorder()+ " preorder");
        //System.out.println(bst.get(99) + " get");
        //System.out.println(bst.preorder()+ " preorder");
        //System.out.println(bst.height() + " height");
        //System.out.println(bst.getRoot());
        System.out.println(bst.preorder()+ " preorder");
        bst.remove(25);
        System.out.println(bst.preorder()+ " preorder");

    }
}