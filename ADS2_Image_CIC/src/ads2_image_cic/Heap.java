/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads2_image_cic;



public class Heap {    
    private HeapNode[] heapArray;
    private int size;
    private int capacity = 1;
    //int[] _dist, int[] _hPos
    Heap()
    {
        heapArray = new HeapNode[capacity];
        size = 0;
    }
    /*
    public String tree2HeapArray(Tree root)
    {
        //TreeNode[] TreeList = root.B
        return null;
    }*/
    // pops the lowest value from heap
    public HeapNode poll()
    {
        if (size != 0)
        {
            HeapNode item = heapArray[0];
            heapArray[0] = heapArray[size - 1];
            size--;
            heapifyDown();
            return item;
        }
        return null;
    }
    
    public void push(float item, int i1, int i2)
    {
        ensureExtraCapacity();
        HeapNode tempNode = new HeapNode(i1, i2, item);
        heapArray[size] = tempNode;
        size++;
        heapifyDown();
    }
    
    public void heapifyDown()
    {
        int index = 0;
        while (hasLeftChild(index))
        {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && rightChild(index) < leftChild(index))
            {
                smallerChildIndex = getRightChildIndex(index);
            }
            
            
            if (heapArray[index].getWeight() < heapArray[smallerChildIndex].getWeight())
            {
                break;
            }
            else
            {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
            //if (!(heapArray[index] < heapArray[smallerChildIndex]))
            //{}
        }
    }
    
    public void heapifyUp()
    {
        int index = size - 1;
        
        //dist[0] = Integer.MIN_VALUE;
        while(hasParent(index) && parent(index) > heapArray[index].getWeight())
        {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }
    
    // helper functions
    
    public HeapNode[] getHeapNodes()
    {
        return heapArray;
    }
    
    public HeapNode getNode(int index)
    {
        return  heapArray[index];
    }
    
    public boolean IsEmpty()
    {
        return (size == 0);
    }
    
    public HeapNode peek()
    {
        return heapArray[0];
    }    
    
    private void ensureExtraCapacity()
    {
        if (size == capacity)
        {
            capacity *= 2;
            HeapNode[] tempArray = heapArray;
            heapArray = new HeapNode[capacity];
            
            for (int i = 0; i < (capacity/2); i++)
                heapArray[i] = tempArray[i];
        }
    }
    
    private void swap(int i1, int i2)
    {
        float temp = heapArray[i1].getWeight();
        heapArray[i1].weight = heapArray[i2].getWeight();
        heapArray[i2].weight = temp;
    }
    
    private int getLeftChildIndex(int parentIndex)
    {
        return 2 * parentIndex + 1;
    }
    
    private int getRightChildIndex(int parentIndex)
    {
        return 2 * parentIndex + 2;
    }
    
    private int getParentIndex(int childIndex)
    {
        return (childIndex - 1)/2;
    }
    
    private boolean hasLeftChild(int index)
    {
        return getLeftChildIndex(index) < size;
    }
    
    private boolean hasRightChild(int index)
    {
        return getRightChildIndex(index) < size;
    }
    
    private boolean hasParent(int index)
    {
        return getParentIndex(index) >= 0;
    }
    
    private float leftChild(int index)
    {
        return heapArray[getLeftChildIndex(index)].getWeight();
    }
    
    private float rightChild(int index)
    {
        return heapArray[getRightChildIndex(index)].getWeight();
    }
    
    private float parent(int index)
    {
        return heapArray[getParentIndex(index)].getWeight();
    }
}
