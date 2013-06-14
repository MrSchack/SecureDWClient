package dke.extension.logic.dimensionManagement;

import dke.extension.data.dbConnection.DBManager;
import dke.extension.data.dbConnection.DBManagerImpl;
import dke.extension.data.dimension.DataDictionary;
import dke.extension.data.dimension.DimensionNode;
import dke.extension.data.dimension.DimensionTree;

import dke.extension.exception.SecureDWException;

import dke.extension.logging.MyLogger;

import dke.extension.logic.crypto.AESCryptEngineImpl;
import dke.extension.logic.crypto.CryptEngine;

import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ManageDimensionImpl implements ManageDimension {
    private DataDictionary dataDictionary;
    
    public ManageDimensionImpl() {
        super();
        dataDictionary = new DataDictionary();
    }

    public void updateLocalDimension(DimensionObject obj) {
        //TODO: update local DB
        //TODO: generate BIX and store it locally
    }
    
    public void insertNewDimensionMember() {
        //TODO: update server dimension table
        this.updateLocalDimension();
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

    public void updateLocalDimensions() {
        DataDictionary  dataDictionary = new DataDictionary();
        DBManager dbManager = new DBManagerImpl();
        Map<String, String> dimTables;
        CryptEngine cryptEngine = new AESCryptEngineImpl();
        
        //get tablenames of all local dimension tables
        //get latest version of all local dimension tables
        //get talbename of all remote dimension tables
        //get latest version of all remote dimension tables
        //compare version
        //if local verison < remote version
        //loop for every dimension
        // - fetch newer entries from remote dimension table
        // - encrypt result
        //TODO: - update local dimension table
        //TODO: - while update, generate BIX
        //TODO: - store BIX

        try {
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
                localVersion = dbManager.getLatestVersion(curTablePlain, DataDictionary.VERSIONCOLUMNNAME, true);
                // remote version
                cryptedColName = dataDictionary.getEncryptedColumnName(curTablePlain);
                curTableCrypt = dataDictionary.getEncryptedTableName(curTablePlain);
                remoteVersion = dbManager.getLatestVersion(curTableCrypt, cryptedColName, false);
                
                
                if ((remoteVersion - localVersion) > 0) {

                    for (int i = localVersion; remoteVersion; i++) {
                        // fetch dimension members from server
                        dimObjectList = dbManager.fetchDimensionMembers(curTableCrypt, i);
                        
                        // insert dimension member into local db
                        if (dimObjectList != null && !dimObjectList.isEmpty()) {
                            for (DimensionObject obj : dimObjectList) {
                              // method for encrypting a dimensionObject
                              curPlainObj = encryptDimensionObject(obj);
                              this.updateLocalDimension(obj);
                            }
                        }
                    }
                }
            }
            
        } catch (SQLException e) {
            MyLogger.logMessage(e.getMessage());
        } catch (SecureDWException e) {
            MyLogger.logMessage(e.getMessage());
        }
    }

    private int getDimDifference(DBManager dbManager,
                                  String tablename) throws SQLException {
        int localVersion = dbManager.getLatestVersion(tablename, DataDictionary.VERSIONCOLUMNNAME, true);
        
        // get remote version
        String colName = dataDictionary.getEncryptedColumnName(tablename);
        int remoteVersion = dbManager.getLatestVersion(tablename, colName, false);
        
        return (remoteVersion - localVersion);
    }
    
    private DimensionObject encryptDimensionObject(DimensionObject encObj) throws SecureDWException {
        DimensionObject plainObj;
        if (encObj.isEncrypted)  {
            plainObj = new DimensionObject(false);
            // encrypt every item and get corresponding plain column name
        } else
            throw new SecureDWException("Error while update: Remote object cant be encrypted!");
        return plainObj;
    }
}
