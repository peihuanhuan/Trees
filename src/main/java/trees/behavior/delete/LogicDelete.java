package trees.behavior.delete;

import trees.AbstractTree;
import trees.BinarySearchTree;
import trees.PrivateTreesHelper;
import trees.Tree;

public class LogicDelete<E> implements DeleteNodeBehavior<E> {
    @Override
    public boolean deleteNode(BinarySearchTree<E> tree, E value) {
        Tree.Node<E> node = tree.search(value);
        if (node == null || node.getCount() <= 0) {
            return false;
        } else {
            node.setCount(node.getCount() - 1);
            PrivateTreesHelper.treeSizeMin((AbstractTree) tree);
            return true;
        }
    }

}
