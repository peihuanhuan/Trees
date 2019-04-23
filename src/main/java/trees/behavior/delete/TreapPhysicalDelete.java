package trees.behavior.delete;

import trees.BinarySearchTree;
import trees.TreapTree;
import trees.Tree;

public class TreapPhysicalDelete<E> extends AbstractPhysicalDelete<E> {

    private BinarySearchTree<E> tree;

    @Override
    void personalizedDeletion(BinarySearchTree<E> tree, Tree.Node<E> node) {
        this.tree = tree;
        deleteCore((TreapTree.TreapTreeNode<E>) node);
    }

    private void deleteCore(TreapTree.TreapTreeNode<E> node) {
        // without any child
        if (node.getRightChild() == null && node.getLeftChild() == null) {
            // also has no parent, means the tree only has this one node
            if (node == tree.getRoot() || node.getParent() == null) {
                tree.setRoot(null);
                return;
            }
            if (node == ((TreapTree.TreapTreeNode<E>)node.getParent()).getLeftChild()) {
                ((TreapTree.TreapTreeNode<E>)node.getParent()).setLeftChild(null);
                node.setParent(null);
            } else {
                ((TreapTree.TreapTreeNode<E>)node.getParent()).setRightChild(null);
                node.setParent(null);
            }
        } else if (node.getLeftChild() != null && node.getRightChild() != null) {
            // with two children, need rotate
            if (((TreapTree.TreapTreeNode)node.getLeftChild()).getFix() < ((TreapTree.TreapTreeNode)node.getRightChild()).getFix()) {
                // left child's fix smaller than the right, need right rotate.
                tree.rightRotate(node);
            } else {
                tree.leftRotate(node);
            }
            deleteCore(node);
        } else if (node.getRightChild() == null) {
            // has left child, without right child
            if (node == tree.getRoot() || node.getParent() == null) {
                // if has no parent, means the tree only has two nodes
                tree.setRoot(node.getLeftChild());
                return;
            }
            if (node == ((TreapTree.TreapTreeNode<E>)node.getParent()).getLeftChild()) {
                ((TreapTree.TreapTreeNode<E>)node.getParent()).setLeftChild(node.getLeftChild());
            } else {
                ((TreapTree.TreapTreeNode<E>)node.getParent()).setRightChild(node.getLeftChild());
            }
            node.getLeftChild().setParent(node.getParent());
            node.setLeftChild(null);
            node.setParent(null);
        } else if (node.getLeftChild() == null) {
            // has right child, without left child
            if (node == tree.getRoot() || node.getParent() == null) {
                // if has no parent, means the tree only has two nodes
                tree.setRoot(node.getRightChild());
                return;
            }
            if (node == ((TreapTree.TreapTreeNode<E>)node.getParent()).getLeftChild()) {
                ((TreapTree.TreapTreeNode<E>)node.getParent()).setLeftChild(node.getRightChild());
            } else {
                ((TreapTree.TreapTreeNode<E>)node.getParent()).setRightChild(node.getRightChild());
            }
            node.getRightChild().setParent(node.getParent());
            node.setRightChild(null);
            node.setParent(null);
        }
    }
}
