package dke.extension.data.dbConnection;

import dke.extension.exception.SecureDWException;

import java.sql.SQLException;
import dke.extension.logic.dimensionManagement.DimensionObject;

import java.sql.SQLException;

import java.util.List;

public interface DBManager {

    /**
     * Checks if local DB file exists.
     */
    public boolean localDBExists();

    /**
     * Deletes local DB file.
     */
    public void deleteLocalDB();

    /**
     * Returns a list of new dimension members with the given version.
     *
     * @param tablename
     * @param version
     * @return
     * @throws SQLException
     */
    public List<DimensionObject> fetchDimensionMembers(String tablename,
                                                          int version) throws Exception;

    /**
     * Returns the version value of the latest entry.
     *
     * @param tablename dimension table name
     * @param columnName column name of the colum where version is stored 
     * @param local if ture, version of local table is queried, otherwise version of remote table
     * @return highest version number of all entries in given table
     */
    public int getLatestVersion(String tablename, String columnName, boolean local)
                                                                throws SQLException, SecureDWException;

    /**
     * Adds a new DimensionObject to the remote database
     * @param dimObject
     * @throws SQLException
     * @throws SecureDWException
     */
    public void insertDimensionMemberRemote(DimensionObject dimObject) throws SQLException, SecureDWException;
    
    /**
     * Adds a new DimensionObject to the local database
     * @param dimObject
     * @throws SQLException
     * @throws SecureDWException
     */
    public void insertDimensionMemberLocal(DimensionObject dimObject) throws SQLException,
                                                                       SecureDWException;
}
