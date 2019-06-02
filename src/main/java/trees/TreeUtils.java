package trees;


import java.util.Iterator;

public class TreeUtils {


    public static <E extends Comparable<E>> void checkTreapTree(TreapTree<E> treapTree) {
        testBaseCheckVaild(treapTree);
        checkTreap(treapTree);
    }

    public static <E extends Comparable<E>> void checkAVLTree(AVLTree<E> avlTree) {
        testBaseCheckVaild(avlTree);
        testAVL(avlTree);
    }



    private static <E extends Comparable<E>> void testBaseCheckVaild(BinarySearchTree<E> tree) {
        if (tree.getRoot() == null) {
            return;
        }
        int size = 0;
        int nodeCount = 0;
        Iterator<Tree.Node<E>> bfsIterator = tree.getBFSIterator();
        while (bfsIterator.hasNext()) {
            size += bfsIterator.next().getCount();
            nodeCount++;
        }
        if (size != tree.size()) {
            throw new RuntimeException("size值不匹配");
        }
        if (nodeCount != tree.nodeCount()) {
            throw new RuntimeException("nodeCount值不匹配");
        }
        testBaseCheckValidCore(((BinarySearchTree.BinaryTreeNode<E>)tree.getRoot()).getLeftChild());
        testBaseCheckValidCore(((BinarySearchTree.BinaryTreeNode<E>)tree.getRoot()).getRightChild());
    }

    private static <E extends Comparable<E>> boolean testBaseCheckValidCore(BinarySearchTree.BinaryTreeNode<E> node) {
        if (node == null) {
            return true;
        }
        BinarySearchTree.BinaryTreeNode<E> parent = (BinarySearchTree.BinaryTreeNode<E>) node.getParent();
        if (parent.getLeftChild() != node && parent.getRightChild() != node) {
            return false;
        }
        if (node == parent.getLeftChild() && node.getValue().compareTo(parent.getValue()) > 0) {
            return false;
        }
        if (node == parent.getRightChild() && node.getValue().compareTo(parent.getValue()) < 0) {
            return false;
        }

        return testBaseCheckValidCore(node.getLeftChild()) && testBaseCheckValidCore(node.getRightChild());
    }

    private static <E extends Comparable<E>> void checkTreap(TreapTree<E> treapTree) {
        if (treapTree == null) {
            return;
        }
        checkTreapCore((TreapTree.TreapTreeNode<E>) treapTree.getRoot());
    }

    private static <E extends Comparable<E>> void checkTreapCore(TreapTree.TreapTreeNode<E> node) {
        if (node == null) {
            return;
        }
        if (node.getLeftChild() != null) {
            if (((TreapTree.TreapTreeNode<E>) node.getLeftChild()).getFix() < node.getFix()) {
                throw new RuntimeException("左孩子fix值不满足规则");
            }
        }
        if (node.getRightChild() != null) {
            if (((TreapTree.TreapTreeNode<E>) node.getRightChild()).getFix() < node.getFix()) {
                throw new RuntimeException("右孩子fix值不满足规则");
            }
        }
        checkTreapCore((TreapTree.TreapTreeNode<E>) node.getLeftChild());
        checkTreapCore((TreapTree.TreapTreeNode<E>) node.getRightChild());
    }

    private static <E extends Comparable<E>> void testAVL(AVLTree<E> tree) {
        if (tree == null) {
            return;
        }
        testAVLCore((AVLTree.AVLTreeNode<E>) tree.getRoot());
    }

    private static <E extends Comparable<E>> void testAVLCore(AVLTree.AVLTreeNode<E> node) {
        if (node == null) {
            return;
        }
        if (Math.abs(height(node.getRightChild()) - height(node.getLeftChild())) >= 2) {
            throw new RuntimeException(node.getValue() + " 高度不平衡");
        }
        if (node.getHeight() != Math.max(height(node.getLeftChild()),height(node.getRightChild())) + 1) {
            throw new RuntimeException(node.getValue() + " 高度不正确");
        }
        testAVLCore((AVLTree.AVLTreeNode<E>) node.getLeftChild());
        testAVLCore((AVLTree.AVLTreeNode<E>) node.getRightChild());
    }

    private static <E extends Comparable<E>> int height(BinarySearchTree.BinaryTreeNode<E> tree) {

        if (tree == null) {
            return 0;
        } else {
            return ((AVLTree.AVLTreeNode)tree).height;
        }

    }

}
