package trees.behavior.delete;

import trees.BinarySearchTree;

public interface DeleteNodeBehavior<E> {
    boolean deleteNode(BinarySearchTree<E> tree, E value);
}
