package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DimensionTree;

import dke.extension.exception.SecureDWException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.SQLException;

import java.util.Map;
import java.util.List;


import org.bouncycastle.crypto.CryptoException;

public interface ManageDimension {

    /**
     * Updates local dimension tables.
     */
    public void updateLocalDimension(DimensionObject obj) throws SQLException,
                                                                 SecureDWException;

    /**
     * Inserts a new dimension member on the server and into local DB.
     */
    public void insertNewDimensionMember(DimensionObject dimObjectCryptoException) throws CryptoException,
                                                                                          NoSuchAlgorithmException,
                                                                                          InvalidKeySpecException,
                                                                                          SQLException,
                                                                                          SecureDWException;

    /**
     * Gets all local dimension members of a given dimension
     */
    public Map<String, String> getDimensionList() throws SQLException;

    /**
     *Returns a DimensionTree which is the representation of the relations between Dimensions
     * @return DimensionTree
     */
    public DimensionTree<String> getDimensionTree() throws SQLException,
                                                           SecureDWException;

    /**
     * Updates all local dimension tables.
     */
    public void updateLocalDimensions() throws SQLException, SecureDWException;


    /**
     * @param dimensionName
     * @return
     */
    public List<String> getDimensionAttributes(String dimensionName) throws SQLException,
                                                                            SecureDWException;

    /**
     * Generates an encrypted DimensionObject of a given decrypted DimensionObject.
     *
     * @param dimObject
     * @return
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws SQLException
     * @throws SecureDWException
     */
    public DimensionObject generateEncryptedDimensionObject(DimensionObject dimObject) throws CryptoException,
                                                                                              NoSuchAlgorithmException,
                                                                                              InvalidKeySpecException,
                                                                                              SQLException,
                                                                                              SecureDWException;
}
