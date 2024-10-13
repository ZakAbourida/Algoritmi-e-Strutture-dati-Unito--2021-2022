package src.myheap;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class MyHeap_Test {

    private MyHeap<Integer> minHeap;
    
    @Before
    public void initialize(){
        minHeap = new MyHeap<Integer>(true);
    }

    @Test
    public void testAddCompareSize() throws MyHeapException{

        minHeap.addItem(5);
        minHeap.addItem(-41);
        minHeap.addItem(14);
        minHeap.addItem(-89);
        minHeap.addItem(2);
        minHeap.addItem(-6);
        minHeap.addItem(-5);
        minHeap.addItem(9);
        minHeap.addItem(7);
        minHeap.addItem(4);
        minHeap.addItem(3);

        assertTrue(11 == minHeap.getSize());
    }

    @Test
    public void testNewHeapEmpty() throws MyHeapException{
        assertTrue(minHeap.isEmpty());
    }

    @Test
    public void testNewHeapSize() throws MyHeapException{
        assertTrue(minHeap.getSize() == 0);
    }

    @Test
    public void testAddRemOneIsEmpty() throws MyHeapException{

        minHeap.addItem(5);
        minHeap.removeRootItem();

        assertTrue(minHeap.isEmpty());
    }

    @Test
    public void testGetMin() throws MyHeapException{

        minHeap.addItem(5);
        minHeap.addItem(-41);
        minHeap.addItem(14);
        minHeap.addItem(-89);
        minHeap.addItem(2);
        minHeap.addItem(-6);
        minHeap.addItem(-5);
        minHeap.addItem(9);
        minHeap.addItem(7);
        minHeap.addItem(4);
        minHeap.addItem(3);

        int act = minHeap.removeRootItem();

        assertTrue(act == -89);
    }

    @Test
    public void testAddItemCompareTree() throws MyHeapException{

        minHeap.addItem(5);
        minHeap.addItem(-41);
        minHeap.addItem(14);
        minHeap.addItem(-89);
        minHeap.addItem(2);
        minHeap.addItem(-6);
        minHeap.addItem(-5);
        minHeap.addItem(9);
        minHeap.addItem(7);
        minHeap.addItem(4);
        minHeap.addItem(3);

        Integer[] exp = {-89, -41, -6, 5, 2, 14, -5, 9, 7, 4, 3};

        Object[] tmp = minHeap.toArray();
        Integer[] act = Arrays.copyOf(tmp, tmp.length, Integer[].class);

        assertArrayEquals(exp, act);
    }

    @Test
    public void testGetMinFromEmpty() throws MyHeapException{
   
        Integer act = minHeap.removeRootItem();
        assertTrue(act == null);
    }
    
    @Test
    public void testGetMinAfterModifiedMin() throws MyHeapException{

        minHeap.addItem(5);
        minHeap.addItem(-41);
        minHeap.addItem(14);
        minHeap.addItem(-89);
        minHeap.addItem(2);
        minHeap.addItem(-6);
        minHeap.addItem(-5);
        minHeap.addItem(9);
        minHeap.addItem(7);
        minHeap.addItem(4);
        minHeap.addItem(3);

        minHeap.changeItem(14, -90);

        int min = minHeap.removeRootItem();
        assertTrue(min == -90);
    }

    @Test
    public void testAddTwoEqualItems() throws MyHeapException{

        minHeap.addItem(5);
        minHeap.addItem(-41);
        minHeap.addItem(14);
        minHeap.addItem(-89);
        minHeap.addItem(2);
        minHeap.addItem(-6);
        minHeap.addItem(-5);
        minHeap.addItem(9);
        minHeap.addItem(7);
        minHeap.addItem(4);
        minHeap.addItem(3);

        
        MyHeapException myE = null;
        try {
            minHeap.addItem(-6);
        } catch (MyHeapException e) {
            myE = e;
        }

        assertTrue(myE != null && myE.getType() == MyHeapException.ITEM_ALREADY_EXIST);
    }

    @Test
    public void testChangeItemAlreadyExist() throws MyHeapException{

        minHeap.addItem(5);
        minHeap.addItem(-41);
        minHeap.addItem(14);
        minHeap.addItem(-89);
        minHeap.addItem(2);
        minHeap.addItem(-6);
        minHeap.addItem(-5);
        minHeap.addItem(9);
        minHeap.addItem(7);
        minHeap.addItem(4);
        minHeap.addItem(3);

        minHeap.changeItem(-41, -6);
        
        int n = 0;
        for (Integer i : minHeap.toList()){
            if(i == -6)
                n++;
        }

        assertEquals(2, n);

    }

    @Test
    public void testGetMinMultipleTimes() throws MyHeapException{
        
        minHeap.addItem(5);
        minHeap.addItem(-41);
        minHeap.addItem(14);
        minHeap.addItem(-89);
        minHeap.addItem(2);
        minHeap.addItem(-6);
        minHeap.addItem(-5);
        minHeap.addItem(9);
        minHeap.addItem(7);
        minHeap.addItem(4);
        minHeap.addItem(3);

        int minA = minHeap.removeRootItem(); //-89
        int minB = minHeap.removeRootItem(); //-41

        assertTrue(minA == -89 && minB == -41);
    }

}
