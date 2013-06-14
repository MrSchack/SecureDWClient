package dke.extension.data.dimension;

import dke.extension.data.dbConnection.ConnectionManager;

import dke.extension.data.dbConnection.DBManager;
import dke.extension.data.dbConnection.DBManagerImpl;
import dke.extension.exception.SecureDWException;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataDictionary {
    public DataDictionary() {
        super();
    }

    public Map<String, String> getAllDimensionTables() {
        Connection con;
        Map<String, String> map = null;

        return map;
    }

    /**
     * Returns a List with successors of a Dimension
     * @param prev is the "parent" of the list
     * @return a List of Strings which presents the successors
     */
    public List<String> getDimensionList(String prev) throws SQLException,
                                                             SecureDWException {
        Connection con;

        String tablename = "DIMENSIONSCHEMA";

        if (!isTableAvailable(tablename)) {
            SecureDWException ex =
                new SecureDWException("Table " + tablename + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        List<String> dimensionList = new LinkedList<String>();

        con = ConnectionManager.getInstance().localConnect();
        Statement stmt = con.createStatement();
        String query =
            "Select DISTINCT DIMNAME From " + tablename + " Where PREVDIM = '" +
            prev + "';";
        // You can find all the relations between Dimensions if you look in the table DIMENSIONSCHEMA
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String dimName = (String)rs.getObject(1);
            dimensionList.add(dimName);
        }

        return dimensionList;
    }

    /**
     * This method returns a List of Attributes of a Dimension.
     * @param name is the name of the Dimension you want to know the attributes of
     * @return a List of Strings which represents the attributes
     */
    public List<String> getDimensionAttributes(String name) throws SQLException,
                                                                   SecureDWException {
        Connection con;

        String tablename = "DIMENSIONATTRIBUTE";

        if (!isTableAvailable(tablename)) {
            SecureDWException ex =
                new SecureDWException("Table " + tablename + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        List<String> dimensionAttributeList = new LinkedList<String>();

        con = ConnectionManager.getInstance().localConnect();
        Statement stmt = con.createStatement();
        String query =
            "Select DISTINCT DIMATTRIBUTE From " + tablename + " Where DIMNAME = '" +
            name + "';";
        // You can find all the relations between Dimensions if you look in the table DIMENSIONSCHEMA

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String dimAttribute = (String)rs.getObject(1);
            dimensionAttributeList.add(dimAttribute);
        }

        return dimensionAttributeList;
    }

    /**
     * Returns you the name of the FactTable
     * @return the name of the FactTable as a String
     */
    public String getFactTableName() throws SQLException, SecureDWException {
        Connection con;

        String name = "";
        String tablename = "DIMENSIONSCHEMA";

        if (!isTableAvailable(tablename)) {
            SecureDWException ex =
                new SecureDWException("Table " + tablename + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        con = ConnectionManager.getInstance().localConnect();

        Statement stmt = con.createStatement();
        String query =
            "Select DIMNAME From " + tablename + " Where PREVDIM is NULL;";
        //The Facttable is the root and has no previous dimensions -> PREVDIM is null
        // You can find all the relations between Dimensions if you look in the table DIMENSIONSCHEMA

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            name = (String)rs.getObject(1);
        }

        return name;
    }

    private boolean isTableAvailable(String tablename) throws SQLException,
                                                              SecureDWException {
        DBManager db = new DBManagerImpl();

        if (!db.localDBExists()) {
            SecureDWException ex =
                new SecureDWException("Local DB does not exist.");
            ex.setForceInit(true);
            throw ex;
        }

        Connection con;

        con = ConnectionManager.getInstance().localConnect();
        Statement stmt = con.createStatement();
        String query =
            "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
            tablename + "';";

        ResultSet rs = stmt.executeQuery(query);

        if (rs.next())
            return true;
        else
            return false;
    }


    /**
     * @param tablename
     * @param columnname
     * @return datatype to use for insert of given column
     * @throws SQLException
     * @throws SecureDWException
     */
    public String getDataType(String tablename,
                              String columnname) throws SQLException,
                                                        SecureDWException {
        Connection con;

        String name = "DICTIONARYCOLUMN";
        String datatype = "";

        if (!isTableAvailable(name)) {
            SecureDWException ex =
                new SecureDWException("Table " + name + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        con = ConnectionManager.getInstance().localConnect();

        Statement stmt = con.createStatement();
        String query =
            "Select DATATYPE From " + name + " Where" + "tablename = " +
            tablename + " AND" + "columnname = " + columnname + ";";

        //The Facttable is the root and has no previous dimensions -> PREVDIM is null
        // You can find all the relations between Dimensions if you look in the table DIMENSIONSCHEMA

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            datatype = (String)rs.getObject(1);
        }

        return datatype;

    }

}
