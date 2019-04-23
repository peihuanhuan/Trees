package trees;

import java.util.Iterator;

public class CheckValidUtils<E extends Comparable<E>> {

    public boolean checkTreapTree(TreapTree<E> treapTree) {
        boolean test = testBaseCheckVaild(treapTree);
        if (test) {
            return checkTreap(treapTree);
        }
        return false;
    }

    private boolean testBaseCheckVaild(BinarySearchTree<E> tree) {
        if (tree.getRoot() == null) {
            return true;
        }
        int size = 0;
        int nodeCount = 0;
        Iterator<Tree.Node<E>> bfsIterator = tree.getBFSIterator();
        while (bfsIterator.hasNext()) {
            size += bfsIterator.next().getCount();
            nodeCount++;
        }
        if (size != tree.size()) {
            return false;
        }
        if (nodeCount != tree.nodeCount()) {
            return false;
        }
        return testBaseCheckValidCore(((BinarySearchTree.BinaryTreeNode<E>)tree.getRoot()).getLeftChild())
                && testBaseCheckValidCore(((BinarySearchTree.BinaryTreeNode<E>)tree.getRoot()).getRightChild());
    }

    private boolean testBaseCheckValidCore(BinarySearchTree.BinaryTreeNode<E> node) {
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

    private boolean checkTreap(TreapTree<E> treapTree) {
        if (treapTree == null) {
            return true;
        }
        return checkTreapCore((TreapTree.TreapTreeNode<E>) treapTree.getRoot());
    }


    private boolean checkTreapCore(TreapTree.TreapTreeNode<E> node) {
        if (node == null) {
            return true;
        }
        if (node.getLeftChild() != null) {
            if (((TreapTree.TreapTreeNode<E>) node.getLeftChild()).getFix() < node.getFix()) {
                return false;
            }
        }
        if (node.getRightChild() != null) {
            if (((TreapTree.TreapTreeNode<E>) node.getRightChild()).getFix() < node.getFix()) {
                return false;
            }
        }
        return checkTreapCore((TreapTree.TreapTreeNode<E>) node.getLeftChild())
                && checkTreapCore((TreapTree.TreapTreeNode<E>) node.getRightChild());
    }
}
