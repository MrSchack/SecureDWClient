package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DataDictionary;
import dke.extension.data.dimension.DimensionNode;
import dke.extension.data.dimension.DimensionTree;

import java.util.LinkedList;
import java.util.List;

import javax.ide.model.java.source.tree.Tree;

public class ManageDimensionImpl implements ManageDimension {

    public ManageDimensionImpl() {
        super();
    }

    public void updateDimension() {
    }

    public void getDimensionData() {
    }

    public DimensionTree<String> getDimensionTree() {
        DataDictionary d = new DataDictionary();
        String factTableName = d.getFactTableName();
        
        DimensionTree<String> tree = new DimensionTree<String>(factTableName);
        DimensionNode<String> root = tree.getRoot();
        root.setAttributes(d.getDimensionAttributes(root.getName()));
        root.setChildren(getDimensionNodes(root.getName()));
        
        return tree;
    }
    
   public List<DimensionNode<String>> getDimensionNodes(String name) {
      DataDictionary d = new DataDictionary();
      List<DimensionNode<String>> children = new LinkedList<DimensionNode<String>>();
      List<String> dimensionNames = new LinkedList<String>(d.getDimensionList(name));
      
      for(String s: dimensionNames){
        DimensionNode<String> n = new DimensionNode<String>();
        n.setName(s);
        n.setAttributes(d.getDimensionAttributes(s));
        n.setChildren(getDimensionNodes(s));
        children.add(n);
      }
       
      return children;
   }
}
