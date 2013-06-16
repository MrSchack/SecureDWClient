package dke.extension.logic.dimensionManagement;

import dke.extension.data.dbConnection.DBManagerImpl;
import dke.extension.data.dimension.DataDictionary;
import dke.extension.data.dimension.DimensionNode;
import dke.extension.data.dimension.DimensionTree;

import dke.extension.exception.SecureDWException;

import dke.extension.logging.MyLogger;

import dke.extension.logic.crypto.AESCryptEngineImpl;
import dke.extension.logic.crypto.CastObjectTo;
import dke.extension.logic.crypto.CryptEngine;

import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.SQLException;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bouncycastle.crypto.CryptoException;

public class ManageDimensionImpl implements ManageDimension {
    private DataDictionary dataDictionary;
    private CryptEngine cryptEngine;

    public ManageDimensionImpl() {
        super();
        dataDictionary = new DataDictionary();
    }

    public void updateLocalDimension() {
        //TODO: update local DB
        //TODO: generate BIX and store it locally
    }

    public void insertNewDimensionMember(DimensionObject dimObject) throws CryptoException,
                                                                           NoSuchAlgorithmException,
                                                                           InvalidKeySpecException {
        //TODO: update server dimension table
        this.updateLocalDimension();

        MyLogger.logMessage("Inserting new dimension members...");

        String dataType = "";
        String cryptColumnName = "";
        String stringValue = "";
        int integerValue = 0;

        DimensionObject cryptDimObject =
            this.generateEncryptedDimensionObject(dimObject);

        if (cryptDimObject.getDimensionMembers() != null) {

            /*
             * TODO
            * call DB methods for storing
            * 1) remote
            * 2) local
            */

            /*
            DBManagerImpl dbManager = new DBManagerImpl();
            try {
                dbManager.insertDimensionMembers(cryptDimObject);
            } catch (SQLException e) {
                MyLogger.logMessage(e.getStackTrace().toString());
                MyLogger.logMessage(e.getSQLState().toString());
                MyLogger.logMessage(e.getMessage());
            } catch (Exception e) {
                MyLogger.logMessage(e.getMessage());
            }
            */

            try {
                dataDictionary.insertDimensionMembers(dimObject);
            } catch (SQLException e) {
                MyLogger.logMessage(e.getMessage());
            } catch (SecureDWException e) {
                MyLogger.logMessage(e.getMessage());
            }
        }
    }

    /**
     * @param dimObject
     * @return DimensionObject with encrypted values
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public DimensionObject generateEncryptedDimensionObject(DimensionObject dimObject) throws CryptoException,
                                                                                              NoSuchAlgorithmException,
                                                                                              InvalidKeySpecException {
        DimensionObject cryptDimObject = new DimensionObject(true);
        cryptEngine = new AESCryptEngineImpl();

        String dataType = "";
        String cryptColumnName = "";

        for (String columnName : dimObject.getDimensionMembers().keySet()) {
            //MyLogger.logMessage(columnName);

            try {
                dataType =
                        dataDictionary.getDataType(dimObject.getDimensionName(),
                                                   columnName);
                cryptColumnName =
                        dataDictionary.getEncryptedColumnName(dimObject.getDimensionName(),
                                                              columnName);
                String cryptTableName =
                    dataDictionary.getEncryptedTablename(dimObject.getDimensionName());
                cryptDimObject.setDimensionName(cryptTableName);

                MyLogger.logMessage("crypt columnname:" + cryptColumnName);


            } catch (SQLException e) {
                MyLogger.logMessage(e.toString());
            } catch (SecureDWException e) {
                MyLogger.logMessage(e.toString());
            }

            // casting objects to specific datatypes & enrypting
            if (dataType != null) {
                if (dataType.equals("TEXT")) {
                    String stringValue =
                        dimObject.getDimensionMembers().get(columnName);

                    // encryption
                    byte[] iv = new byte[16];
                    byte[] cryptString =
                        cryptEngine.encryptString(stringValue, iv);

                    //MyLogger.logMessage("encrypted string value:" + cryptString);


                    String encryptedString = "";
                    try {
                        encryptedString = new String(cryptString, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        MyLogger.logMessage(e.getMessage());
                    }
                    cryptDimObject.addDimensionMember(cryptColumnName,
                                                      encryptedString);
                }

                if (dataType.equals("INTEGER")) {
                    int integerValue =
                        CastObjectTo.getInteger(dimObject.getDimensionMembers().get(columnName));

                    byte[] iv = new byte[16];
                    byte[] cryptString =
                        cryptEngine.encryptString(integerValue + "", iv);

                    //MyLogger.logMessage("encrypted integer value:" + encryptedInt);

                    String encryptedString = "";
                    try {
                        encryptedString = new String(cryptString, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        MyLogger.logMessage(e.getMessage());
                    }
                    cryptDimObject.addDimensionMember(cryptColumnName,
                                                      encryptedString);
                }


            }

        }
        return cryptDimObject;
    }


    public void getLocalDimensionData() {
    }

    /**
     * This method builds the DimensionTree and calls another method to do this recursively
     * @return DimensionTree
     */

    public DimensionTree<String> getDimensionTree() throws SQLException,
                                                           SecureDWException {
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
    public List<DimensionNode<String>> getDimensionNodes(String name) throws SQLException,
                                                                             SecureDWException {
        List<DimensionNode<String>> children =
            new LinkedList<DimensionNode<String>>();
        List<String> dimensionNames =
            new LinkedList<String>(dataDictionary.getDimensionList(name));
        //gets the List of Dimensions which are children of the dimension with the name stored in name

        for (String s : dimensionNames) {
            DimensionNode<String> n = new DimensionNode<String>();
            n.setName(s);
            n.setAttributes(dataDictionary.getDimensionAttributes(s));

            n.setChildren(getDimensionNodes(s)); //this is the recursive call to fill the children with children who have children
            children.add(n);
        }

        return children;
    }

    public void updateLocalDimensions() {
        // get tablenames of all local dimension tables
        // get latest version of all local dimension tables
        // get talbename of all remote dimension tables
        // get latest version of all remote dimension tables
        // compare version
        // if local verison < remote version
        // loop for every dimension
        // - fetch newer entries from remote dimension table
        // - encrypt result
        // - update local dimension table
        // - while update, generate BIX
        // - store BIX

    }
}
