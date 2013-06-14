package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DataDictionary;
import dke.extension.data.dimension.DimensionNode;
import dke.extension.data.dimension.DimensionTree;

import dke.extension.exception.SecureDWException;

import dke.extension.logging.MyLogger;

import dke.extension.logic.crypto.AESCryptEngineImpl;
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


        cryptEngine = new AESCryptEngineImpl();
        DimensionObject encryptDimObject = new DimensionObject(true);
        try {
            String cryptTableName =
                dataDictionary.getEncryptedTablename(dimObject.getDimensionName());
            encryptDimObject.setDimensionName(cryptTableName);

        } catch (SQLException e) {
            MyLogger.logMessage(e.toString());
        } catch (SecureDWException e) {
            MyLogger.logMessage(e.toString());
        }


        for (String columnName : dimObject.getDimensionMembers().keySet()) {
            //MyLogger.logMessage(columnName);

            try {
                dataType =
                        dataDictionary.getDataType(dimObject.getDimensionName(),
                                                   columnName);
                cryptColumnName =
                        dataDictionary.getEncryptedColumnName(dimObject.getDimensionName(),
                                                              columnName);

                MyLogger.logMessage("crypt columnname:" + cryptColumnName);


            } catch (SQLException e) {
                MyLogger.logMessage(e.toString());
            } catch (SecureDWException e) {
                MyLogger.logMessage(e.toString());
            }

            // casting objects to specific datatypes & enrypting
            if (dataType != null) {
                if (dataType.equals("TEXT")) {
                    stringValue =
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
                    encryptDimObject.addDimensionMember(cryptColumnName,
                                                        encryptedString);
                }

                if (dataType.equals("INTEGER")) {
                    integerValue =
                            Integer.parseInt(dimObject.getDimensionMembers().get(columnName));


                    byte[] iv = new byte[16];
                    byte[] value;

                    ByteBuffer b = ByteBuffer.allocate(4);
                    value = b.putInt(integerValue).array();

                    byte[] encryptedInt = cryptEngine.encrypt(value, iv);

                    //MyLogger.logMessage("encrypted integer value:" + encryptedInt);

                    String encryptedString = "";
                    try {
                        encryptedString = new String(encryptedInt, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        MyLogger.logMessage(e.getMessage());
                    }
                    encryptDimObject.addDimensionMember(cryptColumnName,
                                                        encryptedString);
                }
            }


            saveDimensionMembers(encryptDimObject);


        }
    }

    private void saveDimensionMembers(DimensionObject dimObject) {
        /*
         * call DB methods for storing
         * locally and
         * remote
         */

        for (String value : dimObject.getDimensionMembers().values()) {
            MyLogger.logMessage(value);
        }

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
