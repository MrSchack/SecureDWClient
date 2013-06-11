package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DimensionTree;

import javax.ide.model.java.source.tree.Tree;

public interface ManageDimension {

    public void updateDimension();

    public void getDimensionData();
    
    public DimensionTree<String> getDimensionTree();
}
