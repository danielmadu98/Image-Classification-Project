package ads2_image_cic;

public class ImgGraph {
    
    /*Claim suitable underlaying data types and stucutres to hold images in a graph*/    
    private float[][] AdM;
    private float[] visited;
    
    
    private Heap heap; // Creates a binary heap that uses a priority queue
    
    
    private int noOfEdges = 1;
    private int graphSize;
    
    /*Initialise the graph based on the data structure you choose*/
    ImgGraph()
    {
        
    }
    
    ImgGraph(int numOfV){
        if(numOfV > 0)
        {
            heap = new Heap();
            graphSize = numOfV;
            AdM = new float[numOfV][numOfV];
            visited = new float[numOfV];
            for (int i = 0; i < numOfV; i++)
                visited[i] = 0;
        }
        else
            System.err.println("Number of Vertices should be larger than zero");
    }
    
    /**
     * In this function, the weighted edges are generated on-the-fly from 
     * peer-to-peer image similarity comparing algorithms. This function will be
     * called constantly by the GUI functions.
     * 
     * @param vertexID_1 Vertex 1, it use image IDs as references so the data type is int.
     * @param vertexID_2 Vertex 2, same as Vertex 1. Remember that the IDs are the index numbers of the image array.
     * @param edge
     * 
     * This method do not return any new graphs. You need to define and update 
     * the underlying data structures in this method.
     */     
    public void AddNodesEdges (int vertexID_1, int vertexID_2, float edge){
        if(AdM == null)
        {
            System.err.println("The matrix is not initialised correctly");
            return;
        }
        if(AdM.length<=vertexID_1 || AdM.length<=vertexID_2)
        {
            System.err.println("Index is larger than the number of vertices");
            return;
        }
        
        if(vertexID_1 == vertexID_2)
        {
            System.err.println("Two indices should be different");
            return;
        }
        
        if(edge > 0.0)
        {
            AdM[vertexID_1][vertexID_2] = edge;
            AdM[vertexID_2][vertexID_1] = edge;
            heap.push(edge, vertexID_1, vertexID_2);
        }
        else
            System.err.println("The weight should be larger than zero");
    }
    
    
    /**
     * There are many popular algorithms to generate Minimum Spanning Trees. It is
     * up to you to choose a suitable algorithm for this specific application. 
     * Remember the Minimum Spanning Tree needs to be segmented later so you may 
     * wish to optimise certain steps in this methods for the segmentation tasks.
     * You can create as many help functions as you wish to finish this task. For example,
     * if you use Kruskal’s Algorithm, you may need to introduce some suitable sorting
     * algorithms, and if you use Prim’s Algorithm, you need to have a priority 
     * queue structure for the implementation. 
     * 
     * Also remember that the solution of minimum spanning tree is not unique so you
     * results may look different if you use different algorithms.
     * 
     * @return ImgTree: A tree data contains generated MST
     */
    public ImgTree GetMST() {               
        int parent[] = new int[graphSize];
        // Key values used to pick minimum weight edge in cut
        float key[] = new float [graphSize];
        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[graphSize];
 
        for (int i = 0; i < graphSize; i++)
        {
            key[i] = Integer.MAX_VALUE;;
            mstSet[i] = false;
        }
        
        key[0] = 0;
        parent[0] = -1;
        HeapNode[] nodeArray = new HeapNode[graphSize];
        int numOfNodes = 0;
        while (!(heap.IsEmpty()) && (numOfNodes < graphSize))
        {
            HeapNode node = heap.poll();
            int u = node.index_1;
            mstSet[u] = true;
            for (int v = 0; v < graphSize; v++)
            {
                if ((mstSet[v] == false) && (AdM[u][v] <  key[v]))
                {
                    if ((AdM[u][v] != 0.0))
                    {
                        parent[v] = u;
                        key[v] = AdM[u][v];
                        heap.push(AdM[u][v], u, v);
                    }
                }
            }
        }
        printMST(parent, graphSize, AdM, nodeArray);
        TreeNode tmpNode = new TreeNode(nodeArray[0]);
        ImgTree tree = new ImgTree(tmpNode);        
        for (int i = 1; i < nodeArray.length; i++)
        {
            tree.AddChildAt(tmpNode, nodeArray[i]);
        }
        
        return tree;
        
        /*  WORKING VERSION!!!!!!!!!!!!!!
        //ImgTree tree = new ImgTree();
        // Array to store constructed MST
        int parent[] = new int[graphSize];      // CHANGE THIS TO INCOPORATE IMGTREE
        // Key values used to pick minimum weight edge in cut
        float key[] = new float [graphSize];
        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[graphSize];
 
        for (int i = 0; i < graphSize; i++)
        {
            key[i] = Integer.MAX_VALUE;;
            mstSet[i] = false;
        }
 
        key[0] = 0;
        parent[0] = -1;
        
        HeapNode[] nodeArray = new HeapNode[graphSize];
        int index = 0;
        for (int count = 0; count < graphSize-1; count++)
        {
            int u = minKey(key, mstSet);
            mstSet[u] = true;
            for (int v = 0; v < graphSize; v++)
                // This if statement keeps breaking the program
                if ((AdM[u][v] != 0.0) && (mstSet[v] == false) && (AdM[u][v] <  key[v]))
                {
                    parent[v]  = u;
                    key[v] = AdM[u][v];
                    //HeapNode tempNode = new HeapNode(u, v, AdM[u][v]);
                    //treeNode.AddChild(tempNode);
                    //nodeArray[index] = tempNode;
                    index++;
                    //tree.AddChildAt(heapNode, tempNode);
                    //System.out.println("Edge Found: " + u + "->" + v + "Min:" + key[v]);
                }
        }
        // print the constructed MST
        printMST(parent, graphSize, AdM, nodeArray, mstSet);
        TreeNode tmpNode = new TreeNode(nodeArray[0]);
        ImgTree tree = new ImgTree(tmpNode);        
        for (int i = 1; i < nodeArray.length; i++)
        {
            tree.AddChildAt(tmpNode, nodeArray[i]);
        }
        

        return tree;
        */
    }    
    
    
    /*You can add more helper functions below*/
    
    // A utility function to print the constructed MST stored in
    // nodeArray[]
    void printMST(int parent[], int n, float graph[][], HeapNode[] nodeArray)
    {
        System.out.println("Edge   Weight");
        for (int i = 1; i < n; i++)
        {
            System.out.println(parent[i]+" - "+ i+"    "+
                               graph[i][parent[i]]);            
            HeapNode tempNode = new HeapNode(parent[i], i, graph[i][parent[i]]);
            nodeArray[i] = tempNode;
        }
    }
    
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(float key[], Boolean mstSet[])
    {
        float min = Integer.MAX_VALUE;;
        int min_index = -1;
 
        for (int v = 0; v < graphSize; v++)
            if ((mstSet[v] == false) && (key[v] < min))
            {
                min = key[v];
                min_index = v;
            }
        if (min_index != -1)
            mstSet[min_index] = true;
        
        return min_index;
    }
    
    public boolean IsAdjacent(int vertexID_1, int vertexID_2)
    {
        if(AdM == null)
        {
            System.err.println("The matrix is not initialised correctly");
            return false;
        }
        if(AdM.length<=vertexID_1 || AdM.length<=vertexID_2)
        {
            System.err.println("Index is larger than the number of vertices");
            return false;
        }
        if(vertexID_1 == vertexID_2)
        {
            System.err.println("Two indices should be different");
            return false;
        }
        return AdM[vertexID_1][vertexID_2] != 0;
    }
    
    public void DFS(int startID)
    {
        visited[startID] = 1;
        System.out.println(startID);
        
        for (int i=0; i < AdM.length; i++)
        {
            if(IsAdjacent(startID, i) && visited[i] != 1)
            {
                System.out.println("->");
                DFS(i);
            }
        }
    }
}
