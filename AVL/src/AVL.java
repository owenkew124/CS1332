import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Owen Kew
 * @userid okew3
 * @GTID 903592179
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection cannot be null");
        }
        Iterator<T> it = data.iterator();
        T next = (T) it.next();
        this.root = new AVLNode<T>(next);
        size++;
        while (it.hasNext()) {
            if (next == null) {
                throw new IllegalArgumentException("Data inside the collection cannot be null");
            }
            next = (T) it.next();
            this.add(next);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else {
            root = addHelper(data, root);
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> node = new AVLNode<T>(null);
        root = removeHelper(root, data, node);
        if (root != null) {
            rebalance(root);
        }
        size--;
        return node.getData();
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> node = new AVLNode<>(null);
        root = getHelper(root, data, node);
        return node.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return containsHelper(root, data);
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        ArrayList<T> list = new ArrayList<>();
        if (root != null) {
            list = deepestHelper(root, list);
        }
        return list;
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data1 or Data2 cannot be null");
        }
        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Data1 cannot be larger than Data2");
        }
        ArrayList<T> list = new ArrayList<>();
        sortHelper(root, list, data1, data2);
        return list;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
    /**
     * Helper method for the get method
     * @param curr the current node focused on in the tree
     * @param data the data to be gotten
     * @param node a dummy node to put the data from the gotten node into
     * @return a dummy node which contains the data that was searched for in the tree
     */
    private AVLNode<T> getHelper(AVLNode<T> curr, T data, AVLNode<T> node) {
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
     * Helper method for the contains method.
     * @param curr the current node focused on in the tree
     * @param data the data to be searched for
     * @return if the data is in the tree
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
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
     * Helper method for the add method
     * @param data the data to be added to the tree
     * @param curr the current node focused on in the tree
     * @return the current node in the tree
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo((T) curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        } else if (data.compareTo((T) curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        }

        return rebalance(curr);
    }

    /**
     * Helper method to rebalance a certain node/subtree
     *
     * @param curr the node/subtree which needs to be balanced
     * @return the new root node of the subtree
     */
    private AVLNode<T> rebalance(AVLNode<T> curr) {
        update(curr);
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
                curr = rotateRight(curr);
            } else {
                curr = rotateRight(curr);
            }
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight()));
                curr = rotateLeft(curr);
            } else {
                curr = rotateLeft(curr);
            }
        }
        return curr;
    }

    /**
     * Helper method which updates the passed-in node's height and balance factor
     *
     * @param curr the node which needs to be updated
     */
    private void update(AVLNode<T> curr) {
        int left = quickHeight(curr.getLeft());
        int right = quickHeight(curr.getRight());
        curr.setBalanceFactor(left - right);
        curr.setHeight(1 + Math.max(left, right));
    }

    /**
     * Helper method to find the height of a node
     * Used in the update method to get the heights of the node's children
     *
     * @param curr the node whose height needs to be found
     * @return the height of the node or -1 if the value is null
     */
    private int quickHeight(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return curr.getHeight();
        }
    }

    /**
     * Helper method which performs a left rotation
     *
     * @param node the current root node of the unbalanced subtree
     * @return the new root node of the rotated tree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> newRoot = node.getRight();
        AVLNode<T> replacedChild = newRoot.getLeft();
        newRoot.setLeft(node);
        node.setRight(replacedChild);
        update(node);
        update(newRoot);
        return newRoot;
    }

    /**
     * Helper method which performs a right rotation
     *
     * @param node the current root node of the unbalanced subtree
     * @return the new root node of the rotated tree
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> newRoot = node.getLeft();
        AVLNode<T> replacedChild = newRoot.getRight();
        newRoot.setRight(node);
        node.setLeft(replacedChild);
        update(node);
        update(newRoot);
        return newRoot;
    }
    /**
     * Helper method for the remove method.
     * @param curr the current node focused on in the tree
     * @param data the data to be removed
     * @param node a dummy node to put the data from the removed node into
     * @return a dummy node with the data from the removed node
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> node) {
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
                AVLNode<T> temp = new AVLNode<T>(null);
                curr.setRight(successor(curr.getRight(), temp));
                curr.setData(temp.getData());
                curr = rebalance(curr);
                return curr;
            }
        }
        if (data.compareTo((T) curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, node));
            curr = rebalance(curr);
        } else if (data.compareTo((T) curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, node));
            curr = rebalance(curr);
        }
        return curr;
    }

    /**
     * Helper method for the removeHelper method that finds the successor of a node.
     * @param curr the current node focused on in the tree
     * @param temp a temporary node that will hold the successor's data
     * @return the node to the right of the removed node
     */
    private AVLNode<T> successor(AVLNode<T> curr, AVLNode<T> temp) {
        if (curr.getLeft() == null) {
            temp.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successor(curr.getLeft(), temp));
            rebalance(curr);
        }
        return curr;
    }

    /**
     * Helper method for the deepestBranches method
     *
     * @param node the node currently being checked
     * @param list the list of the data in the deepest branch
     * @return the list of all the data in the deepest branches of the tree
     */
    private ArrayList<T> deepestHelper(AVLNode<T> node, ArrayList<T> list) {
        list.add(node.getData());
        if (node.getBalanceFactor() < 0) {
            deepestHelper(node.getRight(), list);
        } else if (node.getBalanceFactor() > 0) {
            deepestHelper(node.getLeft(), list);
        } else {
            if (node.getLeft() != null && node.getRight() != null) {
                deepestHelper(node.getLeft(), list);
                deepestHelper(node.getRight(), list);
            }
        }
        return list;
    }

    /**
     * Helper method for the sortedInBetween() method
     *
     * @param node node currently being compared
     * @param list list of the data in between data1 and data2
     * @param data1 the minimum value which data in the list must be larger than
     * @param data2 the maximum value which data in the list must be smaller than
     * @return list of data in between data1 and data2
     */
    private ArrayList<T> sortHelper(AVLNode<T> node, ArrayList<T> list, T data1, T data2) {
        if (node == null) {
            return list;
        }
        if (node.getData().compareTo(data1) > 0) {
            if (node.getData().compareTo(data2) < 0) {
                list = sortHelper(node.getLeft(), list, data1, data2);
                list.add(node.getData());
                list = sortHelper(node.getRight(), list, data1, data2);
            } else {
                list = sortHelper(node.getLeft(), list, data1, data2);
            }
        } else if (node.getData().compareTo(data1) < 0)  {
            list = sortHelper(node.getRight(), list, data1, data2);
        } else if (node.getData().compareTo(data1) == 0) {
            list = sortHelper(node.getRight(), list, data1, data2);
        } else if (node.getData().compareTo(data2) == 0) {
            list = sortHelper(node.getLeft(), list, data1, data2);
        }
        return list;
    }
}