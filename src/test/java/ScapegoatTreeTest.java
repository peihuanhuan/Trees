import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import trees.ScapegoatTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Slf4j
public class ScapegoatTreeTest {

    @Test
    public void test() {


        boolean flag = true;
        while (flag) {
            ScapegoatTree<Integer> tree = new ScapegoatTree<>();

            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < 1500; i++) {
                list.add(new Random().nextInt());
            }
            for (int i : list) {

                tree.insert(i);
                if (i % 50 == 0) {
                    boolean b = checkSize(tree.getRoot(), tree.getAlpha());
                    if (!b) {
                        System.out.println(list.toString());
                        return;
                    }
                }
            }
        }
    }



    private boolean checkSize(ScapegoatTree.ScapegoatTreeNode root, double alpha) {
        if (root == null) {
            return true;
        }
        Integer left = Optional.ofNullable(root.getLeftChild()).map(ScapegoatTree.ScapegoatTreeNode::getSize).orElse(0);
        Integer right = Optional.ofNullable(root.getRightChild()).map(ScapegoatTree.ScapegoatTreeNode::getSize).orElse(0);
        if (root.getSize() != left + right + 1) {
            System.out.println(root.getValue() + "size: " + root.getSize() + " left " + left + " right " + right);
            return false;
        }
        if (left > (int)(root.getSize() * alpha) || left < (int)(root.getSize() * (1 - alpha))) {
            System.out.println( root.getValue() +" 不平衡");
            return false;
        }
        return checkSize(root.getLeftChild(), alpha) && checkSize(root.getRightChild(), alpha);
    }
}
