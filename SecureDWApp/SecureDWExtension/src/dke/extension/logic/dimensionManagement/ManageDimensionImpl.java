package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DataDictionary;
import dke.extension.data.dimension.DimensionNode;
import dke.extension.data.dimension.DimensionTree;

import java.util.LinkedList;
import java.util.List;

import javax.ide.model.java.source.tree.Tree;

public class ManageDimensionImpl implements ManageDimension {
    private DataDictionary dataDictionary;
    
    public ManageDimensionImpl() {
        super();
        dataDictionary = new DataDictionary();
    }

    public void updateDimension() {
    }

    public void getDimensionData() {
    }
    
    /**
     * This method builds the DimensionTree and calls another method to do this recursively
     * @return DimensionTree
     */
    public DimensionTree<String> getDimensionTree() {
        String factTableName = dataDictionary.getFactTableName();
        
        DimensionTree<String> tree = new DimensionTree<String>(factTableName);
        DimensionNode<String> root = tree.getRoot();
        root.setAttributes(dataDictionary.getDimensionAttributes(root.getName()));
        root.setChildren(getDimensionNodes(root.getName())); //here the recursive method is called
        
        return tree;
    }
    
    /**
     * This is the recursive method to build a DimensionTree
     * @param name is the name of the "Parent"Dimension
     * @return a List of children. If there dont exist any children, the returned list is empty.
     */
   public List<DimensionNode<String>> getDimensionNodes(String name) {
      List<DimensionNode<String>> children = new LinkedList<DimensionNode<String>>();
      List<String> dimensionNames = new LinkedList<String>(dataDictionary.getDimensionList(name)); 
      //gets the List of Dimensions which are children of the dimension with the name stored in name
      
      for(String s: dimensionNames){
        DimensionNode<String> n = new DimensionNode<String>();
        n.setName(s);
        n.setAttributes(dataDictionary.getDimensionAttributes(s));
          
        n.setChildren(getDimensionNodes(s));//this is the recursive call to fill the children with children who have children
        children.add(n);
      }
       
      return children;
   }
}
