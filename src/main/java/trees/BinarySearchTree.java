package trees;

import java.util.Iterator;

public interface BinarySearchTree<E> extends SearchTree<E> {

    BinaryTreeNode<E> leftRotate(BinaryTreeNode<E> node);

    BinaryTreeNode<E> rightRotate(BinaryTreeNode<E> node);

    BinaryTreeNode<E> leftRightRotate(BinaryTreeNode<E> node);

    BinaryTreeNode<E> rightLeftRotate(BinaryTreeNode<E> node);

    /**
     * Returns an iterator over the elements by preorder traversal.
     * @return an iterator over the elements by preorder traversal.
     */
    Iterator<E> preOrderIterator();

    /**
     * Returns an iterator over the elements by inorder traversal.
     * @return an iterator over the elements by inorder traversal.
     */
    Iterator<E> inOrderIterator();

    /**
     * Returns an iterator over the elements by postorder traversal.
     * @return an iterator over the elements by postorder traversal.
     */
    Iterator<E> postOrderIterator();


    /**
     * returns the value of the precursor node of this value,
     * if has no precursor node,returns null.
     * @return the value of the precursor node of this value
     */
    BinaryTreeNode<E> precursor(E value);

    /**
     * returns the value of the successor node of this value,
     * if has no successor node,returns null.
     * @return the value of the successor node of this value
     */
    BinaryTreeNode<E> successor(E value);


    interface BinaryTreeNode<E> extends Node<E> {
        BinaryTreeNode<E> getLeftChild();

        BinaryTreeNode<E> getRightChild();

        void setLeftChild(BinaryTreeNode<E> node);

        void setRightChild(BinaryTreeNode<E> node);
    }

}
