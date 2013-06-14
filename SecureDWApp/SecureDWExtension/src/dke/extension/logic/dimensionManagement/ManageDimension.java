package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DimensionTree;

import dke.extension.exception.SecureDWException;

import java.sql.SQLException;

public interface ManageDimension {

    /**
     * Updates local dimension tables.
     */
    public void updateLocalDimension(DimensionObject obj)();

    /**
     * Inserts a new dimension member on the server and into local DB.
     */
    public void insertNewDimensionMember();

    /**
     * Gets all local dimension members of a given dimension
     */
    public void getLocalDimensionData();
    
    /**
     *Returns a DimensionTree which is the representation of the relations between Dimensions
     * @return DimensionTree
     */
    public DimensionTree<String> getDimensionTree() throws SQLException, SecureDWException;

    /**
     * Updates all local dimension tables.
     */
    public void updateLocalDimensions();
}
