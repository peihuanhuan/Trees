package trees;


public interface SearchTree<E> extends Tree<E> {

    boolean insertValue(Node<E> node);

    void fixSubTree(Node<E> node);
}
