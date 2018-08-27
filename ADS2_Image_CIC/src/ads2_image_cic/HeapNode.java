/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads2_image_cic;

public class HeapNode {
    int index_1;
    int index_2;
    float weight;
    boolean visited;
    
    HeapNode(int i1, int i2, float w)
    {
        index_1 = i1;
        index_2 = i2;
        weight = w;
        visited = false;
    }
    
    HeapNode()
    {
        index_1 = -1;
        index_2 = -1;
        weight = -1;
        visited = false;
    }
    
    public float getWeight()
    {
        return weight;
    }
    
    public int getIndex_1()
    {
        return index_1;
    }
    
    public int getIndex_2()
    {
        return index_2;
    }
}
