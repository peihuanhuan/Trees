package trees;

import trees.behavior.delete.LogicDelete;
import trees.behavior.delete.SplayTreeDelete;

public class SplayTree<E extends Comparable<E>> extends AbstractBinarySearchTree<E> {

    private SimpleBinaryTreeNode<E> root;


    public SplayTree() {
        super();
    }

    public SplayTree(InsertType insertType, DeleteType deleteType) {
        if (deleteType == DeleteType.LOGIC) {
            deleteNodeBehavior = new LogicDelete<>();
        } else {
            deleteNodeBehavior = new SplayTreeDelete<>();
        }
        this.insertType = insertType;
    }

    @Override
    public Node<E> search(E value) {
        SimpleBinaryTreeNode<E> node = (SimpleBinaryTreeNode<E>) super.search(value);

        adjust(node);

        return node;
    }

    private void adjust(SimpleBinaryTreeNode<E> node) {
        if (node == null) {
            return;
        }
        // if it is already the root node
        if (node == getRoot()) {
            return;
        }
        SimpleBinaryTreeNode<E> np = (SimpleBinaryTreeNode<E>) node.getParent();
        if (node.getParent().getParent() == null) {
            // if has no grandpa
            if (node == np.getLeftChild()) {
                rightRotate(np);
            } else {
                leftRotate(np);
            }
        } else {
            // if has grandpa
            SimpleBinaryTreeNode<E> npp = (SimpleBinaryTreeNode<E>) np.getParent();
            if (node == np.getLeftChild()) {
                if (np == npp.getLeftChild()) {
                    rightRotate(npp);
                    rightRotate(np);
                } else {
                    rightLeftRotate(npp);
                }
            } else {
                if (np == npp.getRightChild()) {
                    leftRotate(npp);
                    leftRotate(np);
                } else {
                    leftRightRotate(npp);
                }
            }
            adjust(node);
        }
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public void setRoot(Node<E> root) {
        this.root = (SimpleBinaryTreeNode<E>) root;
    }

    @Override
    public boolean insert(E value) {
        SimpleBinaryTreeNode<E> node = new SimpleBinaryTreeNode<>(value);
        insertValue(node);
        return true;
    }
}
