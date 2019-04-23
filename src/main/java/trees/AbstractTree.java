package trees;

import trees.behavior.delete.DeleteNodeBehavior;
import trees.behavior.delete.LogicDelete;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class AbstractTree<E> implements Tree<E> {

    int nodeCount;
    int size;

    DeleteNodeBehavior<E> deleteNodeBehavior;
    InsertType insertType;



    @Override
    public final DeleteType getDeleteType() {
        if (deleteNodeBehavior instanceof LogicDelete) {
            return DeleteType.LOGIC;
        } else {
            return DeleteType.PHYSICAL;
        }
    }

    @Override
    public InsertType getInsertType() {
        return insertType;
    }


    @Override
    public int nodeCount() {
        return nodeCount;
    }

    @Override
    public final int size() {
        return size;
    }



    @Override
    public Node<E> search(E value) {
        if (getRoot() == null || value == null) {
            return null;
        }
        Iterator<Node<E>> bfsIterator = getBFSIterator();
        while (bfsIterator.hasNext()) {
            Node<E> node = bfsIterator.next();
            if (node.getValue().equals(value)) {
                if (node.getCount() > 0) {
                    return node;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public final int depth() {
        return depthCore(getRoot());
    }

    private int depthCore(Node<E> node) {
        if (node == null) {
            return 0;
        }
        int depth = 0;
        Iterator<Node<E>> childrenIterator = node.getPreChildrenIterator();
        if (childrenIterator.hasNext()) {
            depth = Math.max(depthCore(childrenIterator.next()), depth) + 1;
        }
        return depth;
    }





    @Override
    public Iterator<Node<E>> getDFSIterator() {
        return new DFSIterator();
    }

    @Override
    public Iterator<Node<E>> getBFSIterator() {
        return new BFSIterator();
    }


    private class DFSIterator implements Iterator<Node<E>> {

        Stack<Node<E>> nodeStack = new Stack<>();
        Stack<Integer> posStack = new Stack<>();

        boolean fisrt = true;


        @Override
        public boolean hasNext() {
            if (getRoot() == null) {
                return false;
            }
            if (fisrt) {
                return true;
            }
            return !nodeStack.isEmpty();
        }

        @Override
        public Node<E> next() {
            if (!hasNext()) {
                return null;
            }
            if (fisrt) {
                fisrt = false;
                nodeStack.push(getRoot());
                posStack.push(0);
                return getRoot();
            } else {
                Node<E> node = nodeStack.peek();
                int postion = posStack.peek();
                if (postion < node.getChildrenNum()) {
                    Node<E> result = node.getChildren()[postion++];


                    posStack.pop();
                    posStack.push(postion);

                    nodeStack.push(result);
                    posStack.push(0);
                    return result;
                } else {
                    nodeStack.pop();
                    posStack.pop();
                    return next();
                }
            }

        }
    }

    private class BFSIterator implements Iterator<Node<E>> {

        Queue<Node<E>> queue = new LinkedList<>();

        BFSIterator() {
            if (getRoot() != null) {
                queue.add(getRoot());
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Node<E> next() {
            if (hasNext()) {
                Node<E> head = queue.poll();
                if (head == null) {
                    return null;
                }
                Iterator<Node<E>> iterator = head.getPreChildrenIterator();
                while (iterator.hasNext()) {
                    Node<E> child = iterator.next();
                    if (child != null) {
                        queue.add(child);
                    }
                }
                return head;
            } else {
                return null;
            }
        }
    }


    public static class TreeNode<E> implements Tree.Node<E> {

        Node<E> parent;
        E value;
        Node<E>[] children;
        int count = 1;


        @Override
        public E getValue() {
            return value;
        }

        @Override
        public Node<E> getParent() {
            return parent;
        }

        @Override
        public void setParent(Node<E> node) {
            parent = node;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public Iterator<Node<E>> getPreChildrenIterator() {
            return new PreChildrenIterator();
        }

        @Override
        public Iterator<Node<E>> getPostChildrenIterator() {
            return new PostChildrenIterator();
        }

        @Override
        public Node<E>[] getChildren() {
            return children;
        }

        @Override
        public int getChildrenNum() {
            return children != null ? children.length : 0;
        }

        private class PreChildrenIterator implements Iterator<Node<E>> {
            int count;

            @Override
            public boolean hasNext() {
                return children != null && count < children.length;
            }

            @Override
            public Node<E> next() {
                if (hasNext()) {
                    return children[count++];
                } else {
                    return null;
                }
            }
        }

        private class PostChildrenIterator implements Iterator<Node<E>> {
            int count;

            PostChildrenIterator() {
                if (children != null) {
                    count = children.length - 1;
                }
            }

            @Override
            public boolean hasNext() {
                return children != null && count >= 0;
            }

            @Override
            public Node<E> next() {
                if (hasNext()) {
                    return children[count--];
                } else {
                    return null;
                }
            }
        }
    }




}
