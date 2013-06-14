package dke.extension.logic.dimensionManagement;

import dke.extension.data.dimension.DataDictionary;
import dke.extension.data.dimension.DimensionNode;
import dke.extension.data.dimension.DimensionTree;

import dke.extension.exception.SecureDWException;

import dke.extension.logging.MyLogger;

import dke.extension.logic.crypto.AESCryptEngineImpl;
import dke.extension.logic.crypto.CryptEngine;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

    public void insertNewDimensionMember(DimensionObject dimObject) {
        //TODO: update server dimension table
        this.updateLocalDimension();

        MyLogger.logMessage("Inserting new dimension members...");

        String dataType = "";
        String stringValue = "";
        int integerValue = 0;

        cryptEngine = new AESCryptEngineImpl();


        for (String columnName : dimObject.getDimensionMembers().keySet()) {
            //MyLogger.logMessage(columnName);

            try {
                dataType =
                        dataDictionary.getDataType(dimObject.getDimensionName(),
                                                   columnName);

                MyLogger.logMessage(dataType);


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
                    MyLogger.logMessage("cast string value: " + stringValue);

                    //cryptEngine.encryptString(stringValue, iv);
                }
                if (dataType.equals("INTEGER")) {
                    // TODO
                    String value =
                        dimObject.getDimensionMembers().get(columnName).toString();
                    MyLogger.logMessage("value: " + value);
                    MyLogger.logMessage("testing if changes");
                    long temp = Long.parseLong(value.trim());
                    MyLogger.logMessage("cast int value: " + temp);

                }
            }


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
