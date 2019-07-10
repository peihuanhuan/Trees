package trees;

import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static trees.Tree.DeleteType.PHYSICAL;
import static trees.Tree.InsertType.SINGLE;


public class SplayTreeTest {

    @Test
    public void findTest() {

        SplayTree<Integer> splayTree = new SplayTree<>();
        for (int i = 4; i >= 1; i--) {
            splayTree.insert(i);
        }

        for (int i = 1; i < 5; i++) {
            Tree.Node<Integer> node = splayTree.search(i);
            if (node == null) {
                throw new RuntimeException("no this value");
            }
            if (node.getValue() != i) {
                throw new RuntimeException("this node is not root after searched");
            }
        }
    }

    @Test
    public void delete() {
        SplayTree<Integer> splayTree = new SplayTree<>(SINGLE, PHYSICAL);

        for (int i = 4; i >= 1; i--) {
            splayTree.insert(i);
        }

        for (int i = 1; i < 5; i++) {
            Tree.Node<Integer> node = splayTree.search(i);
            if (node == null) {
                throw new RuntimeException("no this value");
            }
            if (node.getValue() != i) {
                throw new RuntimeException("this node is not root after searched");
            }
        }

        splayTree.delete(3);
    }

    @Test
    public void delete2() {
        SplayTree<Integer> splayTree = new SplayTree<>(SINGLE, PHYSICAL);

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 6000; i++) {
            set.add(new Random().nextInt());
        }

        //String str = "50, 99, 23, 8, 27, 46";
        //List<Integer> set = Arrays.stream(str.split(", ")).map(Integer::valueOf).collect(Collectors.toList());

        set.forEach(i -> {
            splayTree.insert(i);
            TreeUtils.checkSplayTree(splayTree);
        });

        int depth = splayTree.depth();
        System.out.println("\t\t\t depth" +splayTree.depth());

        long cnt = 0;
        long totalDepth = 0;
        for (int ii = 0; ii < 100; ii++) {

            for (Integer integer : set) {
                if (lessTrue()) {
                    continue;
                }
                cnt++;
                Tree.Node<Integer> search = splayTree.search(integer);
                if (search == null) {
                    throw new RuntimeException("no this value");
                }
                if (splayTree.getRoot() != search) {
                    throw new RuntimeException("this node is not root after searched");
                }
                int depth1 = splayTree.depth();
                totalDepth += depth1;
                System.out.println("search " + integer + "\t\t\t depth" + depth1);
            }
        }


        for (int i : set) {
            splayTree.delete(i);
            TreeUtils.checkSplayTree(splayTree);
            //System.out.println("delete" + i + "\t\t\t depth" +splayTree.depth());
        }


        System.out.println(set);
        System.out.println("before " + depth + "average " + totalDepth / cnt);
    }

    private boolean lessTrue() {
        return new Random().nextFloat() < 0.8;
    }


}