package trees;

import trees.behavior.delete.AVLPhysicalDelete;
import trees.behavior.delete.LogicDelete;


public class AVLTree<E extends Comparable<E>> extends AbstractBinarySearchTree<E> {

    private AVLTreeNode<E> root;

    public AVLTree() {
        super();
    }


    public AVLTree(DeleteType deleteType,InsertType insertType) {
        if (deleteType == DeleteType.LOGIC) {
            deleteNodeBehavior = new LogicDelete<>();
        } else {
            deleteNodeBehavior = new AVLPhysicalDelete<>();
        }
        this.insertType = insertType;
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public void setRoot(Node<E> root) {
        this.root = (AVLTreeNode<E>) root;
    }

    @Override
    public boolean insert(E value) {
        insert(value,root);
        return true;
    }



    private AVLTreeNode<E> insert(E value, AVLTreeNode<E> node) {
        if (node == null) {
            node = new AVLTreeNode<>(value);
            if (root == null) {
                root = node;
            }
            nodeCount++;
            size++;
        } else {
            int com = value.compareTo(node.value);

            if (com > 0) {
                // right
                AVLTreeNode<E> right = insert(value, (AVLTreeNode<E>) node.getRightChild());
                node.setRightChild(right);
                right.setParent(node);

                if (height(node.getRightChild()) - height(node.getLeftChild()) == 2) {
                    if (value.compareTo(node.getRightChild().getValue()) > 0) {
                        node = (AVLTreeNode<E>) leftRotate(node);
                    } else {
                        node = (AVLTreeNode<E>) rightLeftRotate(node);
                    }
                }

            } else if (com < 0) {
                // left
                AVLTreeNode<E> left = insert(value, (AVLTreeNode<E>) node.getLeftChild());
                node.setLeftChild(left);
                left.setParent(node);

                if (height(node.getLeftChild()) - height(node.getRightChild()) == 2) {
                    if (value.compareTo(node.getLeftChild().getValue()) < 0) {
                        node = (AVLTreeNode<E>) rightRotate(node);
                    } else {
                        node = (AVLTreeNode<E>) leftRightRotate(node);
                    }
                }
            } else {
                if (getInsertType() == InsertType.MULTIPLE) {
                    // the same value insert again
                    node.setCount(node.getCount() + 1);
                    size++;
                } else {
                    if (node.getCount() <= 0) {
                        node.setCount(node.getCount() + 1);
                        size++;
                    }
                }
            }
        }
        // height the height
        node.height = Math.max(height(node.getLeftChild()),height(node.getRightChild())) + 1;

        return node;
    }


    private int treeGetBalanceFactor(AVLTreeNode<E> root) {
        if (root == null) {
            return 0;
        } else {
            return ((AVLTreeNode<E>)root.getLeftChild()).height - ((AVLTreeNode<E>)root.getRightChild()).height;
        }
    }


    private int height(BinaryTreeNode<E> tree) {

        if (tree == null) {
            return 0;
        } else {
            return ((AVLTreeNode)tree).height;
        }
    }

    private void changeHeight(AVLTreeNode<E> node) {
        node.height = Math.max(height(node.getLeftChild()),height(node.getRightChild())) + 1;
        AVLTreeNode<E> parent = ((AVLTreeNode<E>)node.parent);
        parent.height = Math.max(height(parent.getLeftChild()),height(parent.getRightChild())) + 1;
    }

    // after left rotate, avl tree node needs change its height
    @Override
    public void personalizedLeftRotate(BinaryTreeNode<E> node) {
        changeHeight((AVLTreeNode<E>) node);
    }

    @Override
    public void personalizedRightRotate(BinaryTreeNode<E> node) {
        changeHeight((AVLTreeNode<E>) node);
    }



    public static class AVLTreeNode<E> extends SimpleBinaryTreeNode<E> {
        // 以此节点为根的子树的高度
        int height;

        AVLTreeNode(E value) {
            super(value);
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

}
