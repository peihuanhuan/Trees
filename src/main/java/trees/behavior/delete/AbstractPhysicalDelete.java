package trees.behavior.delete;

import trees.AbstractTree;
import trees.BinarySearchTree;
import trees.Tree;
import trees.PrivateTreesHelper;

public abstract class AbstractPhysicalDelete<E> implements DeleteNodeBehavior<E> {

    @Override
    public final boolean deleteNode(BinarySearchTree<E> tree, E value) {
        Tree.Node<E> node = tree.search(value);
        if (node == null) {
            return false;
        }
        if (node.getCount() >= 1) {
            node.setCount(node.getCount() - 1);
            PrivateTreesHelper.treeSizeMin((AbstractTree) tree);
        }
        // 需要进行物理删除
        if (node.getCount() == 0) {
            personalizedDeletion(tree,node);
            PrivateTreesHelper.treeNodeSizeMin((AbstractTree) tree);
        }
        return true;
    }

    //个性化删除，各自🌲不同实现
    abstract void personalizedDeletion(BinarySearchTree<E> tree,Tree.Node<E> node);

}
