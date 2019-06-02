import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import trees.AVLTree;
import trees.CheckValidUtils;
import trees.TreapTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static trees.Tree.DeleteType.LOGIC;
import static trees.Tree.DeleteType.PHYSICAL;
import static trees.Tree.InsertType.MULTIPLE;
import static trees.Tree.InsertType.SINGLE;
import static trees.TreeUtils.checkAVLTree;
import static trees.TreeUtils.checkTreapTree;

@Slf4j
public class AVLTreeTest {



    @Test
    public void test() {
        AVLTree<Integer> avlTree = new AVLTree<>(null, null);
        TreapTree<Integer> treapTree = new TreapTree<>(null,null);
        Random random = new Random();
        int time = 10000000;
        long avltime = 0;
        long treaptime = 0;
        for (int i = 0 ;i < time; i++) {
            int nextInt = random.nextInt();
            long time1 = System.currentTimeMillis();
            avlTree.insert(nextInt);
            long time2 = System.currentTimeMillis();
            avltime += (time2 -time1);

            treapTree.insert(nextInt);
            long time3 = System.currentTimeMillis();

            treaptime += (time3 - time2);
        }
        CheckValidUtils<Integer> checkValidUtils = new CheckValidUtils<>();
        checkAVLTree(avlTree);
        checkTreapTree(treapTree);

        System.out.println("AVLTree depth:" + avlTree.depth());
        System.out.println("AVLTree insert time:" + avltime);

        System.out.println("TreapTree depth:" + treapTree.depth());
        System.out.println("TreapTree insert time:" + treaptime);

    }

    @Test
    public void deleteTest() {

        AVLTree<Integer> avlTree = new AVLTree<>(PHYSICAL, SINGLE);
        deleteTest(avlTree);

        avlTree = new AVLTree<>(PHYSICAL, MULTIPLE);
        deleteTest(avlTree);

        avlTree = new AVLTree<>(LOGIC, SINGLE);
        deleteTest(avlTree);

        avlTree = new AVLTree<>(LOGIC, MULTIPLE);
        deleteTest(avlTree);

    }

    private void deleteTest(AVLTree<Integer> avlTree) {

        int count = new Random().nextInt(10000000);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int n = new Random().nextInt(10000000);
            list.add(n);
            avlTree.insert(n);
            if (System.currentTimeMillis()%3 == 0) {
//                checkAVLTree(avlTree);
            }

        }

        checkAVLTree(avlTree);


        for (int a = 0; a < list.size();a++) {
            avlTree.delete(list.get(a));

            if (System.currentTimeMillis() % 3 == 0) {
//                checkAVLTree(avlTree);
            }

        }

        checkAVLTree(avlTree);

    }

}
