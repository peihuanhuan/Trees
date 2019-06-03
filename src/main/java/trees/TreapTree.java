package trees;

import trees.behavior.delete.LogicDelete;
import trees.behavior.delete.TreapPhysicalDelete;

public class TreapTree<E extends Comparable<E>> extends AbstractBinarySearchTree<E> {

    private TreapTreeNode<E> root;


    public TreapTree() {
        super();
    }

    public TreapTree(DeleteType deleteType, InsertType insertType) {
        if (deleteType == DeleteType.LOGIC) {
            deleteNodeBehavior = new LogicDelete<>();
        } else {
            deleteNodeBehavior = new TreapPhysicalDelete<>();
        }
        this.insertType = insertType;
    }



    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public void setRoot(Node<E> root) {
        this.root = (TreapTreeNode<E>) root;
    }

    @Override
    public boolean insert(E value) {

        int fix = (int)(1 + Math.random() * (1000));
        TreapTreeNode<E> node = new TreapTreeNode<>();
        node.value = value;
        node.fix = fix;

        return insert(node);
    }

    private boolean insert(TreapTreeNode<E> node) {
        boolean neeedFix = insertValue(node);
        if (neeedFix) {
            fixSubTree(node);
        }
        return true;
    }






    //对插入的节点根据其fix进行修正
    //    @Override
    private void fixSubTree(Node<E> node) {
        if (node.getParent() == null) {
            return;
        }
        if (((TreapTreeNode)node).fix > ((TreapTreeNode)(node.getParent())).fix) {
            return;
        }
        if (node == ((TreapTreeNode)node.getParent()).getRightChild()) {
            leftRotate((BinaryTreeNode<E>) node.getParent());
            fixSubTree(node);
        } else if (node == ((TreapTreeNode)node.getParent()).getLeftChild()) {
            rightRotate((BinaryTreeNode<E>) node.getParent());
            fixSubTree(node);
        }
    }


    public static class TreapTreeNode<E> extends SimpleBinaryTreeNode<E> {
        int fix;

        public int getFix() {
            return fix;
        }
    }

}
