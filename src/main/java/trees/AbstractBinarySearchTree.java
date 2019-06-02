package trees;

import trees.behavior.delete.LogicDelete;

import java.util.Iterator;
import java.util.Stack;

public abstract class AbstractBinarySearchTree<E extends Comparable<E>>
        extends AbstractSearchTree<E> implements BinarySearchTree<E> {


    AbstractBinarySearchTree() {
        deleteNodeBehavior = new LogicDelete<>();
        this.insertType = InsertType.SINGLE;
    }

    @Override
    public boolean delete(E value) {
        return deleteNodeBehavior.deleteNode(this,value);
    }


    /**
     * left rotate for node a
     *
     *    root                             root
     *     /                               /
     *    a                               c
     *   /  \                           /   \
     *  ?    c                         a     ?
     *     /   \                     /  \
     *    cl    ?                   ?   cl
     *
     *
     */
    @Override
    public BinaryTreeNode<E> leftRotate(BinaryTreeNode<E> a) {
        if (a == null || a.getRightChild() == null) {
            return null;
        }
        BinaryTreeNode<E> root = (BinaryTreeNode<E>) a.getParent();
        BinaryTreeNode<E> c = a.getRightChild();
        BinaryTreeNode<E> cl = c.getLeftChild();

        if (root != null) {
            if (a == root.getLeftChild()) {
                root.setLeftChild(c);
            } else if (a == root.getRightChild()) {
                root.setRightChild(c);
            }
        } else {
            setRoot(c);
        }
        c.setParent(root);
        a.setParent(c);
        a.setRightChild(cl);
        if (cl != null) {
            cl.setParent(a);
        }
        c.setLeftChild(a);

        personalizedLeftRotate(a);

        return c;
    }


    /**
     * right rotate for a
     *
     *           root                             root
     *           /                                /
     *          a                                b
     *         /  \                             /  \
     *        b    ?                           ?    a
     *       / \                                   / \
     *      ?  br                                br   ?
     *
     */
    @Override
    public BinaryTreeNode<E> rightRotate(BinaryTreeNode<E> a) {
        if (a == null || a.getLeftChild() == null) {
            return null;
        }
        BinaryTreeNode<E> root = (BinaryTreeNode<E>) a.getParent();
        BinaryTreeNode<E> b = a.getLeftChild();
        BinaryTreeNode<E> br = b.getRightChild();

        if (root != null) {
            if (a == root.getLeftChild()) {
                root.setLeftChild(b);
            } else if (a == root.getRightChild()) {
                root.setRightChild(b);
            }
        } else {
            setRoot(b);
        }
        b.setParent(root);
        a.setParent(b);
        a.setLeftChild(br);
        if (br != null) {
            br.setParent(a);
        }
        b.setRightChild(a);

        personalizedRightRotate(a);

        return b;
    }


    /**
     * left-right rotate for k3
     *
     *           root                         root                       root
     *            /                            /                          /
     *           k3                           k3                         k2
     *         /    \                       /    \                     /    \
     *        k1     ?       ===>          k2     ?     ===>         k1     k3
     *       /  \                         /  |                      /  \   /  \
     *      ?   k2                      k1    B                    ?   A  B    ?
     *         /  \                    /  \
     *        A   B                   ?    A
     *
     */
    @Override
    public final BinaryTreeNode<E> leftRightRotate(BinaryTreeNode<E> k3) {
        BinaryTreeNode<E> k1 = k3.getLeftChild();
        leftRotate(k1);
        rightRotate(k3);
        return (BinaryTreeNode<E>) k3.getParent();
    }

    /**
     * right-left rotate for k1
     *
     *           root                         root                       root
     *            /                            /                          /
     *           k1                           k1                         k2
     *         /    \                       /    \                     /    \
     *        ?     k3       ===>          ?     k2       ===>        k1     k3
     *            /   \                         /  \                 /  \   /  \
     *          k2     ?                       A   k3               ?   A  B    ?
     *         /  \                               /   \
     *        A   B                              B    ?
     *
     */
    @Override
    public final BinaryTreeNode<E> rightLeftRotate(BinaryTreeNode<E> k1) {
        BinaryTreeNode<E> k3 = k1.getRightChild();
        rightRotate(k3);
        leftRotate(k1);
        return (BinaryTreeNode<E>) k1.getParent();
    }

    // some trees may need personalized operate after rotate, such as avl tree,rb tree.
    // use it by override
    public void personalizedLeftRotate(BinaryTreeNode<E> node) {
        // empty;
    }

    // some trees may need personalized operate after rotate, such as avl tree,rb tree.
    // use it by override
    public void personalizedRightRotate(BinaryTreeNode<E> node) {
        // empty;
    }

    @Override
    public int depth() {
        return depthCore((BinaryTreeNode) getRoot());
    }

    private int depthCore(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(depthCore(node.getLeftChild()),depthCore(node.getRightChild())) + 1;
    }

    /**
     * search the tree node by the value.
     * it the tree do not has this value, returns null.
     * @return the node which it's value equals parameter value
     */
    @Override
    public Node<E> search(E value) {
        if (value == null) {
            return null;
        }
        BinaryTreeNode<E> node = (BinaryTreeNode<E>) getRoot();
        while (node != null) {
            if (node.getValue().equals(value)) {
                return node;
            }
            if (node.getValue().compareTo(value) > 0) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return null;
    }

    @Override
    public E max() {
        BinaryTreeNode<E> p = (BinaryTreeNode<E>) getRoot();
        if (p != null) {
            return maxCore(p).getValue();
        } else  {
            return null;
        }
    }

    private BinaryTreeNode<E> maxCore(BinaryTreeNode<E> root) {
        BinaryTreeNode<E> max = null;
        BinaryTreeNode<E> node = root;
        while (node != null) {
            max = node;
            node = node.getRightChild();
        }
        return max;
    }

    @Override
    public E min() {
        BinaryTreeNode<E> p = (BinaryTreeNode<E>) getRoot();
        if (p != null) {
            return minCore(p).getValue();
        } else  {
            return null;
        }
    }

    private BinaryTreeNode<E> minCore(BinaryTreeNode<E> root) {
        BinaryTreeNode<E> min = null;
        BinaryTreeNode<E> node = root;
        while (node != null) {
            min = node;
            node = node.getLeftChild();
        }
        return min;
    }


    /**
     * just insert the node by it's value, left child is smaller, right child is bigger
     * if don't need rotate, returns false
     */
//    @Override
    public boolean insertValue(Node<E> node) {
        if (getRoot() == null) {
            setRoot(node);
            size++;
            nodeCount++;
            return false;
        }
        return insertValueCore((BinaryTreeNode<E>) getRoot(), (BinaryTreeNode<E>) node);
    }

    private boolean insertValueCore(BinaryTreeNode<E> parent, BinaryTreeNode<E> node) {
        if (parent == null || node == null) {
            return false;
        }
        int com = node.getValue().compareTo(parent.getValue());
        if (com < 0) {
            if (parent.getLeftChild() != null) {
                return insertValueCore(parent.getLeftChild(), node);
            } else {
                parent.setLeftChild(node);
                node.setParent(parent);
                size++;
                nodeCount++;
                return true;
            }
        } else if (com > 0) {
            if (parent.getRightChild() != null) {
                return insertValueCore(parent.getRightChild(), node);
            } else {
                parent.setRightChild(node);
                node.setParent(parent);
                size++;
                nodeCount++;
                return true;
            }
        } else {
            if (getInsertType() == InsertType.MULTIPLE) {
                // the same value insert again
                parent.setCount(parent.getCount() + 1);
                size++;
            } else {
                if (parent.getCount() <= 0) {
                    parent.setCount(parent.getCount() + 1);
                    size++;
                }
            }

            return false;
        }
    }

    @Override
    public Iterator<Node<E>> getDFSIterator() {
        throw new RuntimeException("不支持dfs ，请选择 其余遍历方式");
    }

    @Override
    public Iterator<E> preOrderIterator() {
        return new PreOrderIterator();
    }

    @Override
    public Iterator<E> inOrderIterator() {
        return new InOrderIterator();
    }

    @Override
    public Iterator<E> postOrderIterator() {
        return new PostOrderIterator();
    }



    @Override
    public BinaryTreeNode<E> precursor(E value) {

        Node<E> node = search(value);
        if (node == null) {
            return null;
        }

        // if there is a left child, returns the largest node of the subtree rooted at the left child
        if (((BinaryTreeNode)node).getLeftChild() != null) {
            return maxCore(((BinaryTreeNode<E>) node).getLeftChild());
        }

        // if the node is the right child and the node has no left child, returns its parent
        if (node.getParent() != null && ((BinaryTreeNode)node.getParent()).getRightChild() == node) {
            return (BinaryTreeNode<E>) node.getParent();
        }

        // if the node is the left child and the node has no left child
        if (node.getParent() != null && ((BinaryTreeNode)node.getParent()).getLeftChild() == node) {
            BinaryTreeNode<E> p = (BinaryTreeNode<E>) node.getParent();
            while (p.getParent() != null && ((BinaryTreeNode)p.getParent()).getLeftChild() == p) {
                p = (BinaryTreeNode<E>) p.getParent();
            }
            return (BinaryTreeNode<E>) p.getParent();
        }
        return null;
    }


    @Override
    public BinaryTreeNode<E> successor(E value) {

        Node<E> node = search(value);
        if (node == null) {
            return null;
        }

        // if there is a right child, returns the smallest node of the subtree rooted at the right child
        if (((BinaryTreeNode<E>)node).getRightChild() != null) {
            return minCore(((BinaryTreeNode<E>) node).getRightChild());
        }

        // if the node is the left child and the node has no right child, returns its parent
        if (node.getParent() != null && ((BinaryTreeNode)node.getParent()).getLeftChild() == node) {
            return (BinaryTreeNode<E>) node.getParent();
        }

        // if the node is the right child and the node has no right child
        if (node.getParent() != null && ((BinaryTreeNode)node.getParent()).getRightChild() == node) {
            BinaryTreeNode<E> p = (BinaryTreeNode<E>) node.getParent();
            while (p.getParent() != null && ((BinaryTreeNode)p.getParent()).getRightChild() == p) {
                p = (BinaryTreeNode<E>) p.getParent();
            }
            return (BinaryTreeNode<E>) p.getParent();
        }
        return null;
    }


    public static class SimpleBinaryTreeNode<E> extends TreeNode<E> implements BinaryTreeNode<E> {


        @SuppressWarnings("unchecked")
        SimpleBinaryTreeNode() {
            this.children = new BinaryTreeNode[2];
        }

        @SuppressWarnings("unchecked")
        public SimpleBinaryTreeNode(E value) {
            super(value);
            this.children = new BinaryTreeNode[2];
        }

        @Override
        public BinaryTreeNode<E> getLeftChild() {
            return (BinaryTreeNode<E>) children[0];
        }

        @Override
        public BinaryTreeNode<E> getRightChild() {
            return (BinaryTreeNode<E>) children[1];
        }

        @Override
        public void setLeftChild(BinaryTreeNode<E> node) {
            children[0] = node;
        }

        @Override
        public void setRightChild(BinaryTreeNode<E> node) {
            children[1] = node;
        }


    }

    /**
     * Preorder traversal Iterator
     * The same value is only shown once
     * if used logic delete, the delete element whose getCount() returns 0 will not show
     *
     * use the variable count and size to prevent the this case :
     * the hasNext() returns true, but the next() returns null
     */
    private class PreOrderIterator implements Iterator<E> {

        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        BinaryTreeNode<E> p = (BinaryTreeNode<E>) getRoot();
        int count = 0;
        int size;

        PreOrderIterator() {
            size = nodeCount();
            Iterator<Node<E>> iterator = getBFSIterator();
            while (iterator.hasNext()) {
                Node<E> next = iterator.next();
                if (next.getCount() == 0) {
                    size--;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return count != size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            if (p != null) {
                BinaryTreeNode<E> result = p;
                stack.push(p);
                p = p.getLeftChild();
                if (result.getCount() == 0) {
                    return next();
                } else {
                    count++;
                    return result.getValue();
                }
            }
            if (!stack.isEmpty()) {
                p = stack.pop().getRightChild();
                return next();
            }
            return null;
        }

    }

    private class InOrderIterator implements  Iterator<E> {

        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        BinaryTreeNode<E> p = (BinaryTreeNode<E>) getRoot();
        int count = 0;
        int size;

        InOrderIterator() {
            size = nodeCount();
            Iterator<Node<E>> iterator = getBFSIterator();
            while (iterator.hasNext()) {
                Node<E> next = iterator.next();
                if (next.getCount() == 0) {
                    size--;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return count != size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            if (p != null) {
                stack.push(p);
                p = p.getLeftChild();
                return next();
            }
            if (!stack.isEmpty()) {
                p = stack.pop();
                E result = p.getValue();
                int c = p.getCount();
                p = p.getRightChild();
                if (c == 0) {
                    return next();
                } else {
                    count++;
                    return result;
                }
            }
            return null;
        }
    }

    private class PostOrderIterator implements Iterator<E> {

        Stack<BinaryTreeNode<E>> stack = new Stack<>();
        BinaryTreeNode<E> p = (BinaryTreeNode<E>) getRoot();
        BinaryTreeNode<E> q = null;
        boolean flag = false;

        int count = 0;
        int size;

        PostOrderIterator() {
            size = nodeCount();
            Iterator<Node<E>> iterator = getBFSIterator();
            while (iterator.hasNext()) {
                Node<E> next = iterator.next();
                if (next.getCount() == 0) {
                    size--;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return count != size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            while (p.getLeftChild() != null && !flag) {
                stack.push(p);
                p = p.getLeftChild();
            }
            if (p.getRightChild() == null || p.getRightChild() == q) {
                q = p;
                flag = true;
                if (!stack.isEmpty()) {
                    p = stack.pop();
                }
                if (q.getCount() == 0) {
                    return next();
                } else {
                    count++;
                    return q.getValue();
                }
            }
            stack.push(p);
            p = p.getRightChild();

            flag = false;
            return next();
        }
    }

}
