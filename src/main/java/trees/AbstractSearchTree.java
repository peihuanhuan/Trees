package trees;

import java.util.Iterator;

public abstract class AbstractSearchTree<E extends Comparable<E>> extends AbstractTree<E> implements SearchTree<E> {


    @Override
    public E max() {
        if (getRoot() == null) {
            return null;
        }
        E max = getRoot().getValue();
        Iterator<Node<E>> bfsIterator = getBFSIterator();
        while (bfsIterator.hasNext()) {
            Node<E> next = bfsIterator.next();
            if (next.getValue().compareTo(max) > 0) {
                max = next.getValue();
            }
        }
        return max;

    }

    @Override
    public E min() {
        if (getRoot() == null) {
            return null;
        }
        E min = getRoot().getValue();
        Iterator<Node<E>> bfsIterator = getBFSIterator();
        while (bfsIterator.hasNext()) {
            Node<E> next = bfsIterator.next();
            if (next.getValue().compareTo(min) < 0) {
                min = next.getValue();
            }
        }
        return min;

    }


}
