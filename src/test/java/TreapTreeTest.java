import org.junit.Assert;
import org.junit.Test;
import trees.CheckValidUtils;
import trees.TreapTree;
import trees.Tree;

import java.util.Iterator;

public class TreapTreeTest {

    /**
     * -----------测试点----------
     * 最大值        删除
     * 最小值        查找已删除
     * size个数
     * check验证
     * search
     * dfs
     * bfs
     * 前序
     * 中序
     * 后序
     */

    private CheckValidUtils<Integer> checkValidUtils = new CheckValidUtils<>();

    public void insertTest(TreapTree<Integer> treapTree) {
        int count = (int) (Math.random()*100000);
        for (int i = 1; i <= count; i++) {
            treapTree.insert(i);
        }
        Assert.assertEquals(count,treapTree.size());
        Assert.assertEquals(new Integer(1),treapTree.min());
        Assert.assertEquals(new Integer(count),treapTree.max());
        int min = count - 999999999;
        int max = count + 9999999;
        int size = count + 2;
        treapTree.insert(min);
        treapTree.insert(max);

        Assert.assertEquals(new Integer(min),treapTree.min());
        Assert.assertEquals(new Integer(max),treapTree.max());
        Assert.assertTrue(checkValidUtils.checkTreapTree(treapTree));
        for (int i = 1; i<= count; i++) {
            Tree.Node<Integer> node = treapTree.search(i);
            Assert.assertNotNull(node);
        }
        Assert.assertNotNull(treapTree.search(min));
        Assert.assertNotNull(treapTree.search(max));

        int i = 0;
        Iterator<Tree.Node<Integer>> bfsIterator = treapTree.getBFSIterator();
        while (bfsIterator.hasNext()) {
            Assert.assertNotNull(bfsIterator.next());
            i++;
        }
        Assert.assertEquals(size, i);

        i = 0;
        Iterator it = treapTree.inOrderIterator();
        while (it.hasNext()) {
            Assert.assertNotNull(it.next());
            i++;
        }
        Assert.assertEquals(size, i);

        i = 0;
        it = treapTree.preOrderIterator();
        while (it.hasNext()) {
            Assert.assertNotNull(it.next());
            i++;
        }
        Assert.assertEquals(size, i);

        i = 0;
        it = treapTree.postOrderIterator();
        while (it.hasNext()) {
            Assert.assertNotNull(it.next());
            i++;
        }
        Assert.assertEquals(size, i);

        for (i = 1; i <= count ; i += 2) {
            treapTree.insert(i);
            treapTree.insert(i);
            treapTree.insert(i);
            if (treapTree.getInsertType()== Tree.InsertType.MULTIPLE) {
                size += 3;
            }
        }
        Assert.assertEquals(size,treapTree.size());

        for (i = 1; i <= count ; i += 3) {
            treapTree.insert(i);
            treapTree.insert(i);
            if (treapTree.getInsertType()== Tree.InsertType.MULTIPLE) {
                size += 2;
            }
        }
        Assert.assertEquals(size,treapTree.size());

        for (i = 1; i <= count ; i += 4) {
            treapTree.insert(i);
            treapTree.insert(i);
            if (treapTree.getInsertType()== Tree.InsertType.MULTIPLE) {
                size += 2;
            }
        }
        Assert.assertEquals(size,treapTree.size());

        for (i = 1; i <= count ; i += 5) {
            treapTree.insert(i);
            treapTree.insert(i);
            treapTree.insert(i);
            treapTree.insert(i);
            if (treapTree.getInsertType()== Tree.InsertType.MULTIPLE) {
                size += 4;
            }
        }
        Assert.assertEquals(size,treapTree.size());

        for (i = 1; i <= count ; i += 50) {
            treapTree.insert(i);
            treapTree.insert(i);
            if (treapTree.getInsertType()== Tree.InsertType.MULTIPLE) {
                size += 2;
            }
        }
        Assert.assertEquals(size,treapTree.size());
        Assert.assertEquals(count + 2,treapTree.nodeCount());

    }

    @Test
    public void baseTest() {
        for (int i =0 ; i<100 ;i++) {
            TreapTree<Integer> treapTree = new TreapTree<>();
            insertTest(treapTree);
            treapTree = new TreapTree<>(Tree.DeleteType.LOGIC, Tree.InsertType.MULTIPLE);
            insertTest(treapTree);
            treapTree = new TreapTree<>(Tree.DeleteType.PHYSICAL, Tree.InsertType.SINGLE);
            insertTest(treapTree);
            treapTree = new TreapTree<>(Tree.DeleteType.PHYSICAL, Tree.InsertType.MULTIPLE);
            insertTest(treapTree);
        }
    }

    private void singleLogicDeleteTest(TreapTree<Integer> treapTree) {
        int count = (int) (Math.random()*100000);
        for (int i = 1; i <= count; i++) {
            treapTree.insert(i);
        }
        int size = count;
        for (int i = 1; i <= count; i+=2) {
            Assert.assertTrue(treapTree.delete(i));
            size--;
        }
        Assert.assertEquals(size,treapTree.size());
        Assert.assertEquals(count,treapTree.nodeCount());
        Assert.assertTrue(checkValidUtils.checkTreapTree(treapTree));

    }

    private void MultipleLogicDeleteTest(TreapTree<Integer> treapTree) {
        int count = (int) (Math.random()*100000);
        for (int i = 1; i <= count; i++) {
            treapTree.insert(i);
            treapTree.insert(i);
            treapTree.insert(i);
        }
        int size = count * 3;
        for (int i = 1; i <= count / 2; i+=2) {
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertTrue(treapTree.delete(i));
            size--;
            size--;
        }
        for (int i = count / 2 + 3; i <= count; i+=2) {
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertFalse(treapTree.delete(i));
            size--;
            size--;
            size--;
        }
        Assert.assertEquals(size,treapTree.size());
        Assert.assertEquals(count,treapTree.nodeCount());
        Assert.assertTrue(checkValidUtils.checkTreapTree(treapTree));
    }

    private void MultiplePhysicalDeleteTest(TreapTree<Integer> treapTree) {
        int count = (int) (Math.random()*100000);
        for (int i = 1; i <= count; i++) {
            treapTree.insert(i);
            treapTree.insert(i);
            treapTree.insert(i);
        }
        int size = count * 3;
        int nodeCount = count;
        for (int i = 1; i <= count / 2; i+=3) {
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertTrue(treapTree.delete(i));
            size--;
            size--;
        }
        for (int i = count / 2 + 3; i <= count; i+=4) {
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertTrue(treapTree.delete(i));
            Assert.assertFalse(treapTree.delete(i));
            size--;
            size--;
            size--;
            nodeCount--;
        }
        Assert.assertTrue(checkValidUtils.checkTreapTree(treapTree));
        Assert.assertEquals(size,treapTree.size());
        Assert.assertEquals(nodeCount,treapTree.nodeCount());
    }

    private void singlePhysicalDeleteTest(TreapTree<Integer> treapTree) {
        int count = (int) (Math.random()*100000);
        for (int i = 1; i <= count; i++) {
            treapTree.insert(i);
        }

        int size = count;
        for (int i = 1; i <= count; i++) {
            Assert.assertTrue(treapTree.delete(i));
            size--;
        }
        Assert.assertTrue(checkValidUtils.checkTreapTree(treapTree));
        Assert.assertEquals(size,treapTree.size());
        Assert.assertEquals(size,treapTree.nodeCount());


        count = (int) (Math.random()*100000);
        for (int i = 1; i <= count; i++) {
            treapTree.insert(i);
        }

        size = count;
        for (int i = count; i >=1; i--) {
            Assert.assertTrue(treapTree.delete(i));
            size--;
        }
        Assert.assertTrue(checkValidUtils.checkTreapTree(treapTree));
        Assert.assertEquals(size,treapTree.size());
        Assert.assertEquals(size,treapTree.nodeCount());

    }

    @Test
    public void DeleteTest() {
        for (int i =0 ; i<100 ;i++) {

            TreapTree<Integer> treapTree = new TreapTree<>();
            singleLogicDeleteTest(treapTree);

            treapTree = new TreapTree<>(Tree.DeleteType.PHYSICAL, Tree.InsertType.SINGLE);
            singlePhysicalDeleteTest(treapTree);

            treapTree = new TreapTree<>(Tree.DeleteType.LOGIC, Tree.InsertType.MULTIPLE);
            MultipleLogicDeleteTest(treapTree);

            treapTree = new TreapTree<>(Tree.DeleteType.PHYSICAL, Tree.InsertType.MULTIPLE);
            MultiplePhysicalDeleteTest(treapTree);
        }
    }



    @Test
    public void general() {
        TreapTree<Integer> treapTree = new TreapTree<>();
        int count = (int) (Math.random()*100000);

        for (int i = 1;i <= count; i++) {
            treapTree.insert(i);
        }

        for (int i = 1; i <= count; i++) {
            Assert.assertNotNull(treapTree.search(i));
        }
        for (int i = 2;i <= count; i++) {
            Assert.assertEquals(i - 1, treapTree.precursor(i).getValue().intValue());
            treapTree.precursor(i);
        }
        Assert.assertNull(treapTree.precursor(1));

        for (int i = 1;i < count; i++) {
            Assert.assertEquals(i + 1, treapTree.successor(i).getValue().intValue());
        }

        Assert.assertNull(treapTree.successor(count));


        for (int i = 1; i <= count; i++) {
            Assert.assertNotNull(treapTree.search(i));
        }


        int size = count;
        for (int i = 1; i <= count; i+=3) {
            treapTree.delete(i);
            size--;
        }

        Assert.assertEquals(size,treapTree.size());

        Iterator<Integer> iterator = treapTree.preOrderIterator();
        int i = 0;
        while (iterator.hasNext()) {
            Assert.assertNotNull(iterator.next());
            i++;
        }
        Assert.assertEquals(size,i);

        iterator = treapTree.inOrderIterator();
        i = 0;
        while (iterator.hasNext()) {
            Assert.assertNotNull(iterator.next());
            i++;
        }
        Assert.assertEquals(size,i);

        iterator = treapTree.postOrderIterator();
        i = 0;
        while (iterator.hasNext()) {
            Assert.assertNotNull(iterator.next());
            i++;
        }
        Assert.assertEquals(size,i);

    }

}
