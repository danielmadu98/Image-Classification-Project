/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads2_image_cic;

public class TreeNode {
    //Tree Members
    private TreeNode LChild;
    private TreeNode RSibling;
    private HeapNode data;
    
    //Create a tree by defining its root node   
    public TreeNode(HeapNode d)
    {
        data = d;
    }
    
    /**
    * Add a child to a node (this node)
    * @param data the value of new node. You need to create a new TreeNode containing this data
    */
    public void AddChild(HeapNode data) {
        if(LChild == null){
            TreeNode tempNode = new TreeNode(data);
            LChild = tempNode;
            //LChild.parent = this;
        }
        else
        {
            TreeNode tempNode = LChild;
            while (tempNode.GetNextSibling() != null)
                tempNode = tempNode.RSibling;
            
            tempNode.RSibling = new TreeNode(data);
            //tempNode.RSibling.parent = this;
        }
    }
    
    
    
    public void AddChild(TreeNode parent, HeapNode data) {
        if (parent != null)
        {
            if (parent.LChild == null)
            {
                TreeNode tempNode = new TreeNode(data);
                parent.LChild = tempNode;
                //parent.LChild = this;
            }
            else
            {
                TreeNode tempNode = parent.LChild;
                while (tempNode.GetNextSibling() != null)
                    tempNode = tempNode.RSibling;
                TreeNode tempNode2 = new TreeNode(data);
                tempNode.RSibling = tempNode2;
            }
        }
    }
    
    public HeapNode GetData()
    {
        return this.data;
    }
    
    
    public TreeNode GetNext()
    {
        if (GetNextChild() == null)
        {
            return GetNextSibling();
        }
        else
        {
            return GetNextChild();
        }
    }
    /**
     * @return Return a TreeNode reference if this node has a child. If this node has more 
     * than one child, only return the first one. Return null if there is no child
     */
    public TreeNode GetNextChild() {
        return LChild;
    }
    
    /**
     * @return Return a TreeNode reference if this node has a sibling, if the node has
     * more than one sibling, only return the sibling on its right. Return null if 
     * There is no more sibling on its right hand side
     */
    public TreeNode GetNextSibling () {
        if (RSibling == null)
            return null;
        else
            return RSibling;
    }

    
    public TreeNode transverseCluster(TreeNode node)
    {
        if (node == null)
        {
            return null;
        }
        else
        {
            while (node != null)
            {
                if (node.data.weight > 28000.0)
                {
                    //node.LChild = null;
                    TreeNode tmpNode = node.RSibling;
                    node.RSibling = null;
                    return tmpNode;
                }
                else
                {
                    if (node.GetNextChild() != null)
                    {
                        if (node.GetNextChild().data.weight > 28000.0)
                        {
                            if (node.GetNextChild().data.visited == false)
                            {
                                TreeNode tmpNode = node;
                                //node.LChild = null;
                                //node.RSibling = null;
                                node.data.visited = true;
                                return tmpNode;
                            }
                            else
                            {
                                return transverseCluster(node.GetNextChild());
                            }
                        }
                        else
                        {
                            return transverseCluster(node.GetNextChild());
                        }
                    }
                    
                    else if (node.GetNextSibling() != null)
                    {
                        if (node.GetNextSibling().data.weight > 28000.0)
                        {
                            if (node.GetNextSibling().data.visited == false)
                            {
                                TreeNode tmpNode = node.GetNextSibling();
                                node.data.visited = true;
                                node.RSibling = null;
                                //tmpNode.LChild = tmpNode.RSibling;
                                //tmpNode.RSibling = null;
                                return tmpNode;
                            }
                            else
                            {
                                node = node.GetNextSibling();
                            }
                        }
                        else
                        {
                            node = node.GetNextSibling();
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
            }
            return node;
        }
    }
    
    public TreeNode search(HeapNode data)
    {
        if(this.data.equals(data))
            return this;
        else
        {
            TreeNode foundNode = null;
            foundNode = this.LChild.search(data);
            if(foundNode != null)
                return foundNode;
            else
            {
                foundNode = foundNode.GetNextSibling();
                while(foundNode != null)
                {
                    foundNode = this.LChild.RSibling.search(data);
                    if (foundNode != null)
                        return foundNode;
                }
                return null;
            }
        }
    }
    
    
}
