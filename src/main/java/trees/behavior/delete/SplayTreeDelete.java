package trees.behavior.delete;

import trees.BinarySearchTree;
import trees.SplayTree;
import trees.Tree;

public class SplayTreeDelete<E extends Comparable<E>> extends AbstractPhysicalDelete<E> {
    @Override
    void personalizedDeletion(BinarySearchTree<E> tree, Tree.Node<E> node) {
        BinarySearchTree.BinaryTreeNode<E> leftChild = ((BinarySearchTree.BinaryTreeNode<E>) node).getLeftChild();
        BinarySearchTree.BinaryTreeNode<E> rightChild = ((BinarySearchTree.BinaryTreeNode<E>) node).getRightChild();

        if (leftChild == null) {
            tree.setRoot(rightChild);
            if (rightChild != null) {
                rightChild.setParent(null);
            }
        } else {

            SplayTree<E> leftTree = new SplayTree<>();
            leftTree.setRoot(leftChild);
            leftChild.setParent(null);
            Tree.Node<E> newRoot = leftTree.search(leftTree.max());
            //newRoot.setParent(null);
            ((BinarySearchTree.BinaryTreeNode<E>) newRoot).setRightChild(rightChild);
            if (rightChild != null) {
                rightChild.setParent(newRoot);
            }
            tree.setRoot(newRoot);
        }
    }
}
