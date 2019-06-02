package trees.behavior.delete;

import trees.AVLTree;
import trees.BinarySearchTree;
import trees.Tree;

public class AVLPhysicalDelete<E extends Comparable<E>> extends AbstractPhysicalDelete<E> {

    private BinarySearchTree<E> tree;

    @Override
    void personalizedDeletion(BinarySearchTree<E> tree, Tree.Node<E> node) {

        this.tree = tree;
        deleteCore((AVLTree.AVLTreeNode<E>) node);

    }


    private int height(BinarySearchTree.BinaryTreeNode<E> tree) {

        if (tree == null) {
            return 0;
        } else {
            return ((AVLTree.AVLTreeNode)tree).getHeight();
        }
    }
    private void deleteCore(AVLTree.AVLTreeNode<E> node) {

        if (node.getLeftChild() == null && node.getRightChild() == null) {
            // without any children
            noChildDelete(node);

        } else if (node.getLeftChild() != null && node.getRightChild() != null) {
            // with left child and right child
            twoChildrenDelete(node);
        } else {
            // with one child
            oneChildDelete(node);
        }

    }



    private void noChildDelete(AVLTree.AVLTreeNode<E> deleteNode) {
        if (deleteNode.getParent() == null || deleteNode == tree.getRoot()) {
            // also has no parent, means the tree only has this one node
            tree.setRoot(null);
            return;
        }
        AVLTree.AVLTreeNode<E> parent = ((AVLTree.AVLTreeNode<E>)deleteNode.getParent());

        int lh = height(parent.getLeftChild());
        int rh = height(parent.getRightChild());

        if (deleteNode == parent.getLeftChild()) {
            parent.setLeftChild(null);
            // change parent height
            if (lh == rh) {
                // the parent's height didn't change
                return;
            } else if (lh > rh) {
                parent.setHeight(parent.getHeight() - 1);
            } else {
                if (height(parent.getRightChild().getRightChild()) < height(parent.getRightChild().getLeftChild())) {
                    tree.rightLeftRotate(parent);
                } else {
                    tree.leftRotate(parent);
                }
            }
            deleteAdjustment((AVLTree.AVLTreeNode<E>) parent.getParent());

        } else {
            parent.setRightChild(null);
            // change parent height
            if (lh == rh) {
                // the parent's height didn't change
                return;
            } else if (lh < rh) {
                parent.setHeight(parent.getHeight() - 1);
            } else {
                if (height(parent.getLeftChild().getLeftChild()) < height(parent.getLeftChild().getRightChild())) {
                    tree.leftRightRotate(parent);
                } else {
                    tree.rightRotate(parent);
                }
            }
            deleteAdjustment((AVLTree.AVLTreeNode<E>) parent.getParent());

        }
//        deleteNode.setParent(null);
    }

    private void oneChildDelete(AVLTree.AVLTreeNode<E> deleteNode) {
        AVLTree.AVLTreeNode<E> parent = ((AVLTree.AVLTreeNode<E>)deleteNode.getParent());

        if (parent == null) {
            tree.setRoot(deleteNode.getLeftChild() != null ? deleteNode.getLeftChild() : deleteNode.getRightChild());
            return;
        }

        AVLTree.AVLTreeNode<E> child;
        if (deleteNode.getLeftChild() != null) {
            child = (AVLTree.AVLTreeNode<E>) deleteNode.getLeftChild();
        } else {
            child = (AVLTree.AVLTreeNode<E>) deleteNode.getRightChild();
        }

        deleteNode.setValue(child.getValue());
        deleteNode.setCount(1);

        noChildDelete(child);


    }

    private void twoChildrenDelete(AVLTree.AVLTreeNode<E> deleteNode) {
        BinarySearchTree.BinaryTreeNode<E> successor = tree.successor(deleteNode.getValue());
        deleteNode.setValue(successor.getValue());
        deleteNode.setCount(1);
        deleteCore((AVLTree.AVLTreeNode<E>) successor);
    }

    private void deleteAdjustment(AVLTree.AVLTreeNode<E> node) {
        if (node == null) {
            return;
        }

        int lh = height(node.getLeftChild());
        int rh = height(node.getRightChild());


        int heightDifference = Math.abs(lh -rh);

        if (heightDifference == 1) {
            node.setHeight(Math.max(lh, rh) + 1);
            return;
        } else if (heightDifference == 0) {
            node.setHeight(lh + 1);
            deleteAdjustment((AVLTree.AVLTreeNode<E>) node.getParent());
        } else if (heightDifference == 2) {
            BinarySearchTree.BinaryTreeNode<E> next;
            // need rotate
            if (lh > rh) {
                if (height(node.getLeftChild().getLeftChild()) < height(node.getLeftChild().getRightChild())) {
                    next = tree.leftRightRotate(node);
                } else {
                    next = tree.rightRotate(node);
                }
                // right rotate
                deleteAdjustment((AVLTree.AVLTreeNode<E>) next.getParent());
            } else {
                if (height(node.getRightChild().getRightChild()) < height(node.getRightChild().getLeftChild())) {
                    next = tree.rightLeftRotate(node);
                } else {
                    next = tree.leftRotate(node);
                }
                // left rotate
                deleteAdjustment((AVLTree.AVLTreeNode<E>) next.getParent());
            }

            lh = height(node.getLeftChild());
            rh = height(node.getRightChild());
            node.setHeight(Math.max(lh, rh) + 1);


        } else {
            throw new RuntimeException("heightDifference :" + heightDifference);
        }

    }

}
