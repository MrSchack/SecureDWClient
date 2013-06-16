package dke.extension.data.dimension;

import dke.extension.data.dbConnection.ConnectionManager;

import dke.extension.data.dbConnection.DBManager;
import dke.extension.data.dbConnection.DBManagerImpl;
import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.exception.SecureDWException;

import dke.extension.logging.MyLogger;

import dke.extension.logic.crypto.CastObjectTo;
import dke.extension.logic.dimensionManagement.DimensionObject;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataDictionary {
    public static final String VERSIONCOLUMNNAME = "VERS";
    
    public DataDictionary() {
        super();
    }

    /**
     * Returns a map of all dimension table names. Keys are the table names of local tables
     * and values are encrypted table names of tables on the remote database.
     * @return
     * @throws SQLException
     */
    public Map<String, String> getAllDimensionTableNames() throws SQLException {
        Connection con;
        Map<String, String> tableNames = new HashMap<String, String>();

        con = ConnectionManager.getInstance().localConnect();
        Statement stmt = con.createStatement();
        String query = "SELECT TABLENAME_PLAIN, TABLENAME_CRYPT FROM DICTIONARYTABLE WHERE ISDIMENSION = 1";
        
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            tableNames.put((String)rs.getObject(1), (String)rs.getObject(2));
        }
            
        return tableNames;
    }

    public Map<String, String> getLocalDimensionTables() throws SQLException,
                                                                SecureDWException {
        Connection con;
        Map<String, String> map = null;

        String tablename = "DICTIONARYTABLE";


        if (!isTableAvailable(tablename)) {
            SecureDWException ex =
                new SecureDWException("Table " + tablename + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        con = ConnectionManager.getInstance().localConnect();
        Statement stmt = con.createStatement();
        String query =
            "Select TABLENAME_PLAIN from " + tablename + " where ISDIMENSION = 1 ";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            map.put("tablename_plain", rs.getString("TABLENAME_PLAIN"));
        }

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
     * Gets the datatype of the column field for the given table and columnname
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

        tablename = tablename.toUpperCase();
        columnname = columnname.toUpperCase();


        if (!isTableAvailable(name)) {
            SecureDWException ex =
                new SecureDWException("Table " + name + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        con = ConnectionManager.getInstance().localConnect();

        Statement stmt = con.createStatement();
        String query =
            "Select DATATYPE From " + name + " Where " + "TABLENAME = '" +
            tablename + "' AND " + " COLUMNNAME_PLAIN = '" + columnname + "';";

        // for testing purposes
        //MyLogger.logMessage(query);


        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            datatype = (String)rs.getObject(1);
        }

        return datatype;

    }

    /**
     * Gets the encrypted column name for the given one
     * @param tablename
     * @param columnname
     * @return COLUMNNAME_CRYPT for given table and columnname
     * @throws SQLException
     * @throws SecureDWException
     */
    public String getEncryptedColumnName(String tablename,
                                         String columnname) throws SQLException,
                                                                   SecureDWException {
        Connection con;

        String name = "DICTIONARYCOLUMN";
        String cryptColumnName = "";

        tablename = tablename.toUpperCase();
        columnname = columnname.toUpperCase();


        if (!isTableAvailable(name)) {
            SecureDWException ex =
                new SecureDWException("Table " + name + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        con = ConnectionManager.getInstance().localConnect();

        Statement stmt = con.createStatement();
        String query =
            "Select COLUMNNAME_CRYPT From " + name + " Where " + "TABLENAME = '" +
            tablename + "' AND " + " COLUMNNAME_PLAIN = '" + columnname + "';";


        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            cryptColumnName = (String)rs.getObject(1);
        }

        return cryptColumnName;
    }

    /**
     * Gets the encrypted table name for the given one
     * @param tablename
     * @return encrypted tablename for given one
     * @throws SQLException
     * @throws SecureDWException
     */
    public String getEncryptedTablename(String tablename) throws SQLException,
                                                                 SecureDWException {
        Connection con;

        String name = "DICTIONARYTABLE";
        String cryptTableName = "";

        tablename = tablename.toUpperCase();

        if (!isTableAvailable(name)) {
            SecureDWException ex =
                new SecureDWException("Table " + name + " is not available in local DB!");
            ex.setForceInit(true);
            throw ex;
        }

        con = ConnectionManager.getInstance().localConnect();

        Statement stmt = con.createStatement();
        String query =
            "Select TABLENAME_CRYPT From " + name + " Where " + "TABLENAME_PLAIN = '" +
            tablename + "';";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            cryptTableName = (String)rs.getObject(1);
        }

        return cryptTableName;
    }

}
