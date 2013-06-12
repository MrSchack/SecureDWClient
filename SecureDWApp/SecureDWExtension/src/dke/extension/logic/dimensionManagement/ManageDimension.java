package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DimensionTree;

import javax.ide.model.java.source.tree.Tree;

public interface ManageDimension {

    public void updateDimension();

    public void getDimensionData();
    
    /**
     *Returns a DimensionTree which is the representation of the relations between Dimensions
     * @return DimensionTree
     */
    public DimensionTree<String> getDimensionTree();
}
