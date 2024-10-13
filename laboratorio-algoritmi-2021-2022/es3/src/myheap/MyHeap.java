package src.myheap;

import java.util.Vector;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

public class MyHeap<T extends Comparable<T>> {
    private boolean isMin;
    private Vector<T> heap;
    private HashMap<T, Integer> map;
    private int size;

    /**
    * constructor
    * @param isMin = true if it's a min heap, false if is max heap
    * @return new MyHeap Object reference
    */
    public MyHeap(boolean isMin){
        this.isMin = isMin;
        heap = new Vector<T>(0);
        map = new HashMap<>();
        size = 0;
    }

    /**
     * 
     * @param item is the <T> item of wich to get the parent
     * @return the parent <T> item
     */
    public T getParent(T item) throws MyHeapException{
        if(item == null)
            throw new MyHeapException("getParent - null argument", MyHeapException.INVALID_ARGUMENT);
        return heap.get( getParentIndex(map.get(item)) -1 );
    }
    /**
     * 
     * @param index is the index of the item of which to get the parent
     * @return the index of the parent (-1 if given index is not valid, the parent of the root is himself)
     * @throws MyHeapException
     */
    private int getParentIndex(int index) throws MyHeapException{
        if(! (1<=index && index<=heap.size()))
            throw new MyHeapException("getParentIndex - invalid argument", MyHeapException.INVALID_ARGUMENT);
        if(index == 1)
            return index;
        return (int) Math.floor(index/2);
    }

    /**
     * 
     * @param item is the <T> item of wich to get the left child
     * @return the child <T> item
     */
    public T getLeftChild(T item) throws MyHeapException{
        if(item == null)
            throw new MyHeapException("getLeftChild - null argument", MyHeapException.INVALID_ARGUMENT);
        return heap.get( getLeftChildIndex(map.get(item)) -1 );
    }
    /**
     * 
     * @param index is the index of the item of which to get the left child
     * @return the index of the left child (-1 id the given index is not valid, -2 if there is no left child)
     * @throws MyHeapException
     */
    public int getLeftChildIndex(int index) throws MyHeapException{
        if(! (1<=index && index<=heap.size()))
            throw new MyHeapException("getLeftChildIndex - given \"index\" param out of range", MyHeapException.INVALID_ARGUMENT);
        if(2*index<=getSize())
            return 2*index;
        else
            throw new MyHeapException("getLeftChildIndex - child index exceed heap size", MyHeapException.NO_LEFT_CHILD);
    }

    /**
     * 
     * @param item is the <T> item of wich to get the rigth child
     * @return the child <T> item
     */
    public T getRightChild(T item) throws MyHeapException{
        if(item == null)
            throw new MyHeapException("getRightChild - null argument", MyHeapException.INVALID_ARGUMENT);
        return heap.get( getRightChildIndex(map.get(item)) -1 );
    }
    /**
     * 
     * @param index is the index of the item of which to get the right child
     * @return the index of the right child (-1 id the given index is not valid, -2 if there is no right child)
     * @throws MyHeapException
     */
    public int getRightChildIndex(int index) throws MyHeapException{
        if(! (1<=index && index<=heap.size()))
            throw new MyHeapException("getRightChildIndex - given \"index\" param out of range", MyHeapException.INVALID_ARGUMENT);
        if(2*index+1<=getSize())
            return 2*index+1;
        else
           throw new MyHeapException("getRightChildIndex - child index exceed heap size", MyHeapException.NO_RIGHT_CHILD);
    }

    /**
     * 
     * @return the number of element in the heap
     */
    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return getSize() == 0;
    }

    /**
     * 
     * @param item is the <T> item to add in the heap
     * @throws Exception
     * @throws MyHeapException
     */
    public void addItem(T item) throws MyHeapException{
        if(item == null)
            throw new MyHeapException("addItem - null argument" + MyHeapException.INVALID_ARGUMENT);
        if( map.get(item) != null)
            throw new MyHeapException("addItem - item already exist", MyHeapException.ITEM_ALREADY_EXIST);

        heap.add(item);
        size++;
        map.put(item, getSize());

        int i = getSize();    //it's at least = 1
        while(i > 1 && (isMin ? compareWithParent(i)<0 : compareWithParent(i)>0)){
            swap(i, getParentIndex(i));
            i = getParentIndex(i);
        }

    }

    /**
     * remove the root element, that is the min element if it's a min heap or the max element if it's a max heap
     * @return the <T> element extracted, null if heap is empty
     * @throws MyHeapException
     */
    public T removeRootItem() throws MyHeapException{
        if(getSize()==0)
            return null;
        if(getSize()==1){
            T ret = heap.remove(0);
            size--;
            map.remove(ret);
            return ret;
        }

        swap(1, getSize());

        T ret = heap.remove(heap.size()-1);
        map.remove(ret);
        size--;

        heapify(1);

        return ret;
    }
    //----------------------------------------------------------------//
    private void heapify(int index) throws MyHeapException{
        if(!(1<=index && index<=heap.size()))
            return;

        //get current item
        T tmp_i = heap.get(index-1);
        //System.out.println("size="+getSize()+" actual_size="+heap.size());

        //get left child
        T tmp_left;
        try {
            int lft_ch = getLeftChildIndex(index);
            //System.out.println("left_child_index="+lft_ch);
            tmp_left = heap.get(lft_ch-1);
        } catch (MyHeapException e) {
            if( e.getType() == MyHeapException.NO_LEFT_CHILD)
                tmp_left = null;
            else
                throw e;
        }
        
        //get right child
        T tmp_right;
        try{
            int rgt_ch = getRightChildIndex(index);
            //System.out.println("right_child_index="+rgt_ch);
            tmp_right = heap.get(rgt_ch-1);
        } catch (MyHeapException e) {
            if( e.getType() == MyHeapException.NO_RIGHT_CHILD)
                tmp_right = null;
            else
                throw e;
        } 

        //get index of min/max item between current_item/left_child/right_child
        T m_tmp;
        m_tmp = isMin ?
            min(tmp_i, min(tmp_left, tmp_right)) :
            max(tmp_i, max(tmp_left, tmp_right));
        int m = map.get(m_tmp);

        if(m != index){
            swap(m, index);
            heapify(m);
        }
    }

    /**
     * modify the item at given index with the new given <T> item
     * @param index is the index of the item to swap
     * @param item is the new <T> item to swap in
     * @throws MyHeapException
     */
    public void changeItem(T oldItem, T newItem) throws MyHeapException{
        if(oldItem == null || newItem == null)
            throw new MyHeapException("changeItem - null argument", MyHeapException.INVALID_ARGUMENT);
        
        int index = map.remove(oldItem);
        map.put(newItem, index);
        heap.set(index-1, newItem);
        
        //index = map.get(newItem);

        if(isMin){
            //while H[i] < his parent
            boolean toRoot = false;
            while(compareWithParent(index) < 0){
                //swap H[i] with his parent
                swap(index, getParentIndex(index));
                index = getParentIndex(index);
                toRoot = true;
            }
            if(toRoot)
                return;
            
            //while a child is < H[i]
            while(true){
                //get left child
                int lft_ch;
                try{
                    lft_ch = getLeftChildIndex(index);
                } catch (MyHeapException e) {
                    if( e.getType() == MyHeapException.NO_LEFT_CHILD)
                        lft_ch = -1;
                    else
                        throw e;
                }
                //get right child
                int rgt_ch;
                try{
                    rgt_ch = getRightChildIndex(index);
                } catch (MyHeapException e) {
                    if( e.getType() == MyHeapException.NO_RIGHT_CHILD)
                        rgt_ch = -1;
                    else
                        throw e;
                }

                //if no child is lower
                if((lft_ch == -1 || compareWithParent(lft_ch) > 0) && (rgt_ch == -1 || compareWithParent(rgt_ch) > 0))
                    break;
                
                //swap H[i] with his lower child
                int i;
                i = min(lft_ch, rgt_ch);
                swap(index, i);
            }
        }
        else{
            boolean toRoot = false;
            //while H[i] > his parent
            while(compareWithParent(index) > 0){
                //swap H[i] with his parent
                swap(index, getParentIndex(index));
                index = getParentIndex(index);
                toRoot = true;
            }
            if(toRoot)
                return;
            
            //while a child is > H[i]
            while(true){
                //get left child
                int lft_ch;
                try{
                    lft_ch = getLeftChildIndex(index);
                } catch (MyHeapException e) {
                    if( e.getType() == MyHeapException.NO_LEFT_CHILD)
                        lft_ch = -1;
                    else
                        throw e;
                }
                //get right child
                int rgt_ch;
                try{
                    rgt_ch = getRightChildIndex(index);
                } catch (MyHeapException e) {
                    if( e.getType() == MyHeapException.NO_RIGHT_CHILD)
                        rgt_ch = -1;
                    else
                        throw e;
                }

                //if no child is greater
                if((lft_ch == -1 || compareWithParent(lft_ch) < 0) && (rgt_ch == -1 || compareWithParent(rgt_ch) < 0))
                    break;
                
                //swap H[i] with his greater child
                int i;
                i = max(lft_ch, rgt_ch);
                swap(index, i);
            }
        }
    }

    /**
     * swap the two item given by their index
     * @param indexA index of one of the two elements to swap
     * @param indexB index of the other element to swap
     * @throws MyHeapException
     */
    private void swap(int indexA, int indexB) throws MyHeapException{
        if(indexA < 1 || indexA > getSize() || indexB < 1 || indexB > getSize())
            throw new MyHeapException("swap - invalid argument", MyHeapException.INVALID_ARGUMENT);

        T A = heap.get(indexA-1);
        T B = heap.get(indexB-1);
        heap.set(indexA-1, heap.get(indexB-1));
        heap.set(indexB-1, A);
        map.replace(A, indexB);
        map.replace(B, indexA);
    }

    @SuppressWarnings("unused")
    /**
     * compare the item given with his parent
     * @param item is the <T> item to compare
     * @return the value of the item.compareTo(parent)
     * @throws MyHeapException
     */
    private int compareWithParent(T item) throws MyHeapException{
        if(item == null)
            throw new MyHeapException("compareWithParent(<T>) - null argument", MyHeapException.INVALID_ARGUMENT);
        if(map.get(item) == null)
            throw new MyHeapException("compareWithParent(<T>) - invalid argument", MyHeapException.INVALID_ARGUMENT);
        int parentIndex = getParentIndex( map.get(item) );
        return item.compareTo( heap.get(parentIndex-1) );
    }
    /**
     * compare the item given ny his index with his parent
     * @param itemIndex is the index of the  item to compare
     * @return the value of the item.compareTo(parent)
     * @throws MyHeapException
     */
    private int compareWithParent(int itemIndex) throws MyHeapException{
        if(itemIndex < 1 || itemIndex > getSize()){
            System.out.println("compareWithParent("+itemIndex+")");
            throw new MyHeapException("compareWithParent - invalid argument", MyHeapException.INVALID_ARGUMENT);
        }
        int parentIndex = getParentIndex( itemIndex );
        return heap.get( itemIndex-1 ).compareTo( heap.get(parentIndex-1) );
    }

    /**
     * print the heap as each level on one line and the item tabulated between eachother
     * @throws MyHeapException
     */
    public void printHeap() throws MyHeapException{
        int i = 1;
        for (T item : heap) {
            if(isPowerOfTwo(i))
                System.err.println("");
            System.out.print(item.toString()+"|"+item.hashCode() + "\t");
            i++;
        }
        System.out.println("");
    }
    public void printHashMap() throws MyHeapException{
        Set<Map.Entry<T, Integer>> set = map.entrySet();
        System.out.println("HashMap:");
        for (Entry<T,Integer> entry : set) {
            System.out.println("< "+entry.toString()+" >");
        }
    }

    public Object[] toArray(){
        return heap.toArray();
    }
    public List<T> toList(){
        return Collections.list(heap.elements());
    }

    public boolean contains(T item){
        return map.containsKey(item);
    }

    private boolean isPowerOfTwo(int n) throws MyHeapException{
        return (int)(Math.ceil((Math.log(n) / Math.log(2))))
            == (int)(Math.floor(((Math.log(n) / Math.log(2)))));
    }

    private T min(T a, T b){
        if(a == null)
            return b;
        if(b == null)
            return a;
        return a.compareTo(b) < 0 ? a : b;
    }
    private int min(int a, int b) throws MyHeapException{
        if((a > getSize() || a < 1) && (b > getSize() || b < 1))
            throw new MyHeapException("min - invalid argument", MyHeapException.INVALID_ARGUMENT);
        if(a > getSize() || a < 1)
            return b;
        if(b > getSize() || b < 1)
            return a;
        return heap.get(a-1).compareTo(heap.get(b-1)) < 0 ? a : b;
    }
    private T max(T a, T b){
        if(a == null)
            return b;
        if(b == null)
            return a;
        return a.compareTo(b) > 0 ? a : b;
    }
    private int max(int a, int b) throws MyHeapException{
        if((a > getSize() || a < 1) && (b > getSize() || b < 1))
            throw new MyHeapException("min - invalid argument", MyHeapException.INVALID_ARGUMENT);
        if(a > getSize())
            return b;
        if(b > getSize())
            return a;
        return heap.get(a-1).compareTo(heap.get(b-1)) > 0 ? a : b;
    }
}

