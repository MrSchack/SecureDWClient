package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DimensionTree;

import dke.extension.exception.SecureDWException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.SQLException;

import org.bouncycastle.crypto.CryptoException;

public interface ManageDimension {

    /**
     * Updates local dimension tables.
     */
    public void updateLocalDimension();

    /**
     * Inserts a new dimension member on the server and into local DB.
     */
    public void insertNewDimensionMember(DimensionObject dimObjectCryptoException) throws CryptoException,
                                                                                          NoSuchAlgorithmException,
                                                                                          InvalidKeySpecException;

    /**
     * Gets all local dimension members of a given dimension
     */
    public void getLocalDimensionData();

    /**
     *Returns a DimensionTree which is the representation of the relations between Dimensions
     * @return DimensionTree
     */
    public DimensionTree<String> getDimensionTree() throws SQLException,
                                                           SecureDWException;

    /**
     * Updates all local dimension tables.
     */
    public void updateLocalDimensions();
}
