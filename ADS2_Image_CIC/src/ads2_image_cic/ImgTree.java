package ads2_image_cic;

import java.util.Arrays;

public class ImgTree {
    
    /*Claim suitable underlaying data types and structures of tree*/
    
    private TreeNode root;
    
    ImgTree(HeapNode node){
        root = new TreeNode(node);
    }
    
    ImgTree(TreeNode node)
    {
        root = node;
    }
    
    //ImgTree
    
    // helper functions
    public TreeNode Search(HeapNode NodeName)
    {
        return root.search(NodeName);
    }
    
    public void AddChildAt(HeapNode parentName, HeapNode childName)
    {
        if (Search(parentName) != null)
            Search(parentName).AddChild(childName);
    }
    
    public void AddChildAt(TreeNode parentName, HeapNode childName)
    {
        root.AddChild(parentName, childName);
    }
    
    /**
     * You need to deploy the MST Clustering Algorithms introduced in the class.
     * 
     * The advantage of this algorithm is that the number of the clusters can be 
     * automatic obtained (un-supervised clustering) based on the statistic 
     * distribution of the weighted edges. After the segmentation, large weighted
     * edges will be removed which segment the big MST trees into several smaller
     * sub-trees. 
     * 
     * @param MSTInput MST tree generated from ImgGraph.MST()
     * @return Segmented sub-trees 
     */
    public static ImgTree[] MSTSegmentation(ImgTree MSTInput) {
        //TreeNode startNode = MSTInput.root.GetNextChild();
        ImgTree[] imgTree = new ImgTree[1];
        int i = 0;
        imgTree[i] = MSTInput;
        ImgTree[] tmpTree; 
        tmpTree = CreateClusters(imgTree, i, MSTInput.root.GetNext());
        //imgTree = new ImgTree[tmpTree.length-1];
        
        //for (int o = 1; o < tmpTree.length; o++)
        //{
          //  imgTree[o-1] = tmpTree[o];
        //}
        return tmpTree;
    }
    
    
    public static ImgTree[] CreateClusters(ImgTree[] imgTree, int index, TreeNode node)
    {
        if (node != null)
        {
            TreeNode startNode = node;
            index++;
            //if (startNode != null)
            //{
            TreeNode transversedNode = imgTree[index-1].root.transverseCluster(startNode);

            if (transversedNode != null)
            {
                imgTree = growArray(imgTree);
                imgTree[index] = new ImgTree(transversedNode);
                imgTree = CreateClusters(imgTree, index, transversedNode.GetNext());
            }
            //}

            
        }
        return imgTree;
    }
    
    
    public static ImgTree[] growArray(ImgTree[] oldArray){
        ImgTree[] newArray = new ImgTree[oldArray.length + 1];
        for(int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }
    
    public static int[] growArray(int[] oldArray)
    {
        int[] newArray = new int[oldArray.length + 1];
        for(int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }
    
    
    public static int[] transverseTree(TreeNode node, int index, int[] nodeIDs)
    {
        if (node.GetNextChild() != null){
            nodeIDs = transverseTree(node.GetNextChild(), index, nodeIDs);
            return nodeIDs;
        }
        else
        {
            nodeIDs = growArray(nodeIDs);
            nodeIDs[index] = node.GetData().index_2;
            
            index++;
            while (node.GetNextSibling() != null)
            {
                nodeIDs = transverseTree(node.GetNextSibling(), index, nodeIDs);
                return nodeIDs;
            }
        }
        return nodeIDs;
    }
    
    /**
     * Implement a suitable approach for tree traversal to gather all the nodes.
     * The nodes are the IDs of images and the output will be used to display
     * those images as a group. It is worth noting that the order of these nodes
     * are not important. You can choose any suitable traversal algorithms for this 
     * implementation. 
     * @param subTree the input tree structure
     * @return an array contains all the IDs in one cluster
     */
    public static int[] getAllNodesIDs (ImgTree subTree){
        int index = 0;
        int[] nodeIDs = new int[0];
        nodeIDs = transverseTree(subTree.root, index, nodeIDs);
        return nodeIDs; 
    }
    
    
    
    /**
     * This is an simulation generate random output to test the GUI display functions.
     * Replace this method by getAllNodesIDs after you finish the development.
     */    
    public static int[] getAllNodesIDsDEMO (ImgTree subTree){
        int[] output;
        int random  = (int)(java.lang.Math.random()*10)+1;
        output = new int[random];
        for (int i=0; i<random; i++)
            output[i] = (int)(java.lang.Math.random()*100);
        return output; 
    }
}
