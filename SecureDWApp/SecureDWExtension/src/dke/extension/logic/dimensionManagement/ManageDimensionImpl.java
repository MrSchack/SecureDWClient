package dke.extension.logic.dimensionManagement;

import dke.extension.data.dbConnection.DBManager;
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Map;

import org.bouncycastle.crypto.CryptoException;

public class ManageDimensionImpl implements ManageDimension {
    private DataDictionary dataDictionary;
    private CryptEngine cryptEngine;

    public ManageDimensionImpl() {
        super();
        dataDictionary = new DataDictionary();
    }

    public void updateLocalDimension(DimensionObject obj) throws SQLException,
                                                                 SecureDWException {
        if (obj.isEncrypted())
            throw new SecureDWException("Error: Dimension object to insert locally is encrypted!");

        DBManager dbManager = new DBManagerImpl();

        dbManager.insertDimensionMember(obj);
        MyLogger.logMessage("Dimension members inserted locally into " +
                            obj.getDimensionName() + ".");
        //TODO: generate BIX and store it locally
    }

    public void insertNewDimensionMember(DimensionObject dimObject) throws CryptoException,
                                                                           NoSuchAlgorithmException,
                                                                           InvalidKeySpecException,
                                                                           SQLException,
                                                                           SecureDWException {

        DimensionObject cryptDimObject =
            this.generateEncryptedDimensionObject(dimObject);

        if (cryptDimObject.getDimensionMembers() != null) {
            DBManager dbManager = new DBManagerImpl();

            try {
                dbManager.insertDimensionMember(cryptDimObject);
                MyLogger.logMessage("Dimension members inserted on remote database into " +
                                    dimObject.getDimensionName() + ".");
            } catch (SQLException e) {
                MyLogger.logMessage(e.getMessage());
            } catch (Exception e) {
                MyLogger.logMessage(e.getMessage());
            }

            this.updateLocalDimension(dimObject);
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
                                                                                              InvalidKeySpecException,
                                                                                              SQLException,
                                                                                              SecureDWException {
        if (dimObject.isEncrypted())
            throw new CryptoException("Error: Dimension object can not get encrypted because it is already encrypted!");

        DimensionObject cryptDimObject = new DimensionObject(true);
        cryptEngine = new AESCryptEngineImpl();

        String dataType = "";
        String cryptColumnName = "";

        String cryptTableName =
            dataDictionary.getEncryptedTablename(dimObject.getDimensionName());
        cryptDimObject.setDimensionName(cryptTableName);

        for (String columnName : dimObject.getDimensionMembers().keySet()) {

            dataType =
                    dataDictionary.getDataType(dimObject.getDimensionName(), columnName);
            cryptColumnName =
                    dataDictionary.getEncryptedColumnName(dimObject.getDimensionName(),
                                                          columnName);
            // encrypt all data except from column VERS
            if (!columnName.equals(DataDictionary.VERSIONCOLUMNNAME)) {
                // casting objects to specific datatypes & encrypting
                if (dataType != null) {
                    String value =
                        dimObject.getDimensionMembers().get(columnName);

                    // encryption
                    byte[] iv = AESCryptEngineImpl.DEFAULT_IV;
                    byte[] cryptString = cryptEngine.encryptString(value, iv);

                    String encryptedString = "";
                    try {
                        encryptedString = new String(cryptString, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        MyLogger.logMessage(e.getMessage());
                    }
                    cryptDimObject.addDimensionMember(cryptColumnName,
                                                      encryptedString);
                }

            } else { // col = VERS
                cryptDimObject.addDimensionMember(cryptColumnName,
                                                  dimObject.getDimensionMembers().get(columnName));
            }
        } // end for loop
        return cryptDimObject;
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

    public void updateLocalDimensions() throws SQLException,
                                               SecureDWException {
        DataDictionary dataDictionary = new DataDictionary();
        DBManager dbManager = new DBManagerImpl();
        Map<String, String> dimTables;
        CryptEngine cryptEngine = new AESCryptEngineImpl();

        //if local verison < remote version
        //loop for every dimension
        // - fetch newer entries from remote dimension table
        // - encrypt result
        //TODO: - update local dimension table
        //TODO: - while update, generate BIX
        //TODO: - store BIX

        dimTables = dataDictionary.getAllDimensionTableNames();

        String curTableCrypt;
        int localVersion;
        int remoteVersion;
        String cryptedColName;
        List<DimensionObject> dimObjectList;
        DimensionObject curPlainObj;

        // update local db - dimension table by table
        for (String curTablePlain : dimTables.keySet()) {
            // compare versions of current local and remote dim tables

            // local version
            localVersion =
                    dbManager.getLatestVersion(curTablePlain, DataDictionary.VERSIONCOLUMNNAME,
                                               true);
            // remote version
            cryptedColName =
                    dataDictionary.getEncryptedColumnName(curTablePlain,
                                                          DataDictionary.VERSIONCOLUMNNAME);
            curTableCrypt =
                    dataDictionary.getEncryptedTablename(curTablePlain);
            remoteVersion =
                    dbManager.getLatestVersion(curTableCrypt, cryptedColName,
                                               false);

            if (remoteVersion > localVersion) { /*

                for (int i = localVersion; i <= remoteVersion; i++) {
                    // fetch dimension members from server
                    dimObjectList = dbManager.fetchDimensionMembers(curTableCrypt, i);

                    // insert dimension member into local db
                    if (dimObjectList != null && !dimObjectList.isEmpty()) {
                        for (DimensionObject obj : dimObjectList) {
                          // method for decrypting a dimensionObject
                          curPlainObj = decryptDimensionObject(obj);
                          this.updateLocalDimension(obj);
                        }
                    }
                }*/
            }
        }
    }

    private DimensionObject decryptDimensionObject(DimensionObject encObj) throws SecureDWException {
        DimensionObject plainObj;
        if (encObj.isEncrypted()) {
            plainObj = new DimensionObject(false);
            // encrypt every item and get corresponding plain column name
        } else
            throw new SecureDWException("Error while update: Oject cant be encrypted!");
        return plainObj;
    }

    public List<String> getDimensionAttributes(String dimensionName) throws SQLException,
                                                                            SecureDWException {
        return dataDictionary.getDimensionAttributes(dimensionName);
    }

    public Map<String, String> getDimensionList() throws SQLException {
        return dataDictionary.getAllDimensionTableNames();
    }
}
