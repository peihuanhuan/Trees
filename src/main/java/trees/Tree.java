package trees;

import java.util.Iterator;

public interface Tree<E> {

    /**
     * returns the root of tree
     * @return the root of tree
     */
    Node<E> getRoot();

    /**
     * set the root of tree
     * @param root new root
     */
    void setRoot(Node<E> root);

    /**
     * returns the Node whose value equals the parameter value in the tree.
     * it the tree do not has this value, returns null.
     * @param value value
     * @return the Node whose value equals the parameter value
     */
    Node<E> search(E value);

    /**
     * insert thr value to the tree.
     * @param value the value you want insert
     * @return success or fail
     */
    boolean insert(E value);

    /**
     * delete the value in the tree
     * @param value the value you want delete
     * @return success or fail
     */
    boolean delete(E value);

    /**
     * returns the type of delete
     * @see DeleteType
     * @return logic or pyhsical delete
     */
    DeleteType getDeleteType();

    /**
     * returns the type of insert
     * @see InsertType
     * @return single or multiple insert
     */
    InsertType getInsertType();

    enum DeleteType {
        /**
         * when a node is deleted, it will be physically deleted
         */
        PHYSICAL,
        /**
         * When deleting a node, it is just a logical delete,
         * use getCount() to determine whether it is deleted.
         * @see Node#getCount()
         */
        LOGIC
    }

    enum InsertType {
        /**
         * duplicate values are not allowed in the tree
         */
        SINGLE,
        /**
         * allow duplicate values in the tree and have a count flag
         * @see Node#getCount()
         */
        MULTIPLE
    }

    /**
     * returns the number of real physical nodes. if the tree is null,returns 0
     * @return the number of real physical nodes
     */
    int nodeCount();

    /**
     * returns the number of tree node
     * if the tree contains more than Integer.MAX_VALUE elements,returns Integer.MAX_VALUE
     * @return the number of tree node
     */
    int size();

    /**
     * returns the depth of tree. if the tree is null,returns 0
     * @return the depth of tree
     */
    int depth();

    /**
     * returns the max element in the tree. if the tree is null,returns null.
     * @return the max element int the tree
     */
    E max();

    /**
     * returns the min element in the tree. if the tree is null,returns 0
     * @return the min element int the tree
     */
    E min();


    /**
     * get the iterator of depth-first traversal
     * @return the iterator of depth-first traversal
     */
    Iterator<Node<E>> getDFSIterator();


    /**
     * get the iterator of breadth-first traversal
     * @return the iterator of breadth-first traversal
     */
    Iterator<Node<E>> getBFSIterator();



    interface Node<E> {
        /**
         * returns the value of the node
         * @return the value of the node
         */
        E getValue();

        /**
         * set the value of node
         */
        void setValue(E value);

        /**
         * returns the parent of the node
         * @return the parent of the node
         */
        Node<E> getParent();

        /**
         * set the parent of the node
         * @param node the parent
         */
        void setParent(Node<E> node);

        /**
         * Get an iterator that traverses the child from left to right
         * @return an iterator that traverses the child from left to right
         */
        Iterator<Node<E>> getPreChildrenIterator();

        /**
         * Get an iterator that can traverse the child from right to left
         * @return an iterator that can traverse the child from right to left
         */
        Iterator<Node<E>> getPostChildrenIterator();

        /**
         * returns the children
         * @return children nodes
         */
        Node<E>[] getChildren();

        /**
         * returns the children number
         * @return children number
         */
        int getChildrenNum();

        /**
         * returns the count number of this value
         * if the tree using a combination of {@link InsertType#SINGLE} and {@link DeleteType#LOGIC}, it only can be 0 or 1;
         * if the tree using a combination of {@link InsertType#MULTIPLE} and {@link DeleteType#LOGIC}, it only can be any number greater than or equal to 0;
         * if the tree using a combination of {@link InsertType#SINGLE} and {@link DeleteType#PHYSICAL}, it's useless, will always be 1;
         * if the tree using a combination of {@link InsertType#MULTIPLE} and {@link DeleteType#PHYSICAL}, it only can be any number greater than or equal to 1;
         * @return the count of this value
         */
        int getCount();

        /**
         * set the count number if this value
         * @param count count
         */
        void setCount(int count);
    }


}
