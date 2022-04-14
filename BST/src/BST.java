import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection cannot be null");
        }
        Iterator it = data.iterator();
        T next = (T) it.next();
        this.root = new BSTNode<T>(next);
        while (it.hasNext()) {
            if (next == null) {
                throw new IllegalArgumentException("Data inside the collection cannot be null");
            }
            next = (T) it.next();
            this.add(next);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else {
            root = addHelper(data, root);
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> node = new BSTNode<>(null);
        root = removeHelper(root, data, node);
        size--;
        return node.getData();
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> node = new BSTNode<>(null);
        root = getHelper(root, data, node);
        return node.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return containsHelper(root, data);
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<T>();
        return preorderHelper(root, list);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<>();
        return inorderHelper(root, list);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<>();
        return postorderHelper(root, list);
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new LinkedList<T>();
        if (root == null) {
            return list;
        }
        Queue<BSTNode<T>> queue = new LinkedList<>();
        BSTNode curr = root;
        queue.add(root);
        BSTNode<T> next = new BSTNode<>(null);
        while (!(queue.isEmpty())) {
            next = queue.remove();
            if (next.getLeft() != null) {
                queue.add(next.getLeft());
            }
            if (next.getRight() != null) {
                queue.add(next.getRight());
            }
            list.add(next.getData());
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        int height = heightHelper(root);
        return height;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("k cannot be larger than the size of the tree");
        }
        BSTNode<T> curr = root;
        List<T> list = new LinkedList<T>();
        list = kLargestHelper(root, list, k);
        return list;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Helper method for the preorder method.
     * @param curr the current node in the tree
     * @param list the list where all the nodes will be added to
     * @return the list of the nodes in preorder
     */
    private List<T> preorderHelper(BSTNode curr, List<T> list) {
        if (curr == null) {
            return list;
        }
        list.add((T) curr.getData());
        preorderHelper(curr.getLeft(), list);
        preorderHelper(curr.getRight(), list);
        return list;
    }

    /**
     * Helper method for the postorder method.
     * @param curr the current node in the tree
     * @param list the list where all the nodes will be added to
     * @return the list of the nodes in postorder
     */
    private List<T> postorderHelper(BSTNode curr, List<T> list) {
        if (curr == null) {
            return list;
        }
        postorderHelper(curr.getLeft(), list);
        postorderHelper(curr.getRight(), list);
        list.add((T) curr.getData());
        return list;
    }

    /**
     * Helper method for the inorder method.
     * @param curr the current node in the tree
     * @param list the list where all the nodes will be added to
     * @return the list of the nodes in order
     */
    private List<T> inorderHelper(BSTNode curr, List<T> list) {
        if (curr == null) {
            return list;
        }
        inorderHelper(curr.getLeft(), list);
        list.add((T) curr.getData());
        inorderHelper(curr.getRight(), list);
        return list;
    }

    /**
     * Helper method for the add method
     * @param data the data to be added to the tree
     * @param curr the current node focused on in the tree
     * @return the current node in the tree
     */
    private BSTNode<T> addHelper(T data, BSTNode curr) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo((T) curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        } else if (data.compareTo((T) curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        }
        return curr;
    }

    /**
     * Helper method for the get method
     * @param curr the current node focused on in the tree
     * @param data the data to be gotten
     * @param node a dummy node to put the data from the gotten node into
     * @return a dummy node which contains the data that was searched for in the tree
     */
    private BSTNode<T> getHelper(BSTNode curr, T data, BSTNode node) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }
        if (data.compareTo((T) curr.getData()) == 0) {
            node.setData(curr.getData());
            return curr;
        }
        if (data.compareTo((T) curr.getData()) < 0) {
            curr.setLeft(getHelper(curr.getLeft(), data, node));
        } else if (data.compareTo((T) curr.getData()) > 0) {
            curr.setRight(getHelper(curr.getRight(), data, node));
        }
        return curr;
    }

    /**
     * Helper method for the height method.
     * @param curr the current node focused on in the tree
     * @return the height of the tree
     */
    private int heightHelper(BSTNode curr) {
        if (curr == null) {
            return -1;
        } else {
            int left = heightHelper(curr.getLeft());
            int right = heightHelper(curr.getRight());
            if (left > right) {
                return left + 1;
            } else {
                return right + 1;
            }
        }
    }

    /**
     * Helper method for the remove method.
     * @param curr the current node focused on in the tree
     * @param data the data to be removed
     * @param node a dummy node to put the data from the removed node into
     * @return a dummy node with the data from the removed node
     */
    private BSTNode<T> removeHelper(BSTNode curr, T data, BSTNode node) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the Tree");
        }
        if (data.compareTo((T) curr.getData()) == 0) {
            node.setData(curr.getData());
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getRight() == null && curr.getLeft() != null) {
                return curr.getLeft();
            } else if (curr.getRight() == null && curr.getLeft() != null) {
                return curr.getRight();
            } else {
                BSTNode temp = new BSTNode(null);
                curr.setRight(successor(curr.getRight(), temp));
                curr.setData(temp.getData());
                return curr;
            }
        }
        if (data.compareTo((T) curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, node));
        } else if (data.compareTo((T) curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, node));
        }
        return curr;
    }

    /**
     * Helper method for the removeHelper method that finds the successor of a node.
     * @param curr the current node focused on in the tree
     * @param temp a temporary node that will hold the successor's data
     * @return the node to the right of the removed node
     */
    private BSTNode<T> successor(BSTNode<T> curr, BSTNode<T> temp) {
        if (curr.getLeft() == null) {
            temp.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successor(curr.getLeft(), temp));
        }
        return curr;
    }

    /**
     * Helper method for the contains method.
     * @param curr the current node focused on in the tree
     * @param data the data to be searched for
     * @return if the data is in the tree
     */
    private boolean containsHelper(BSTNode curr, T data) {
        if (curr == null) {
            return false;
        } else {
            if (data.compareTo((T) curr.getData()) < 0) {
                return containsHelper(curr.getLeft(), data);
            } else if (data.compareTo((T) curr.getData()) > 0) {
                return containsHelper(curr.getRight(), data);
            }
        }
        return true;
    }

    /**
     * Helper method for the kLargest method.
     * @param curr the current node focused on in the tree
     * @param list the list that will contain the k largest nodes
     * @param k the number of nodes needed to be included in the list
     * @return the list of the k largest nodes
     */
    private List<T> kLargestHelper(BSTNode curr, List<T> list, int k) {
        if (curr == null) {
            return list;
        }
        kLargestHelper(curr.getRight(), list, k);
        if (list.size() == k) {
            return list;
        }
        list.add(0, (T) curr.getData());
        kLargestHelper(curr.getLeft(), list, k);
        return list;
    }
}
