package dke.extension.data.dbConnection;

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
     * Returns a list of new dimension members.
     * 
     * @param tablename
     * @param localVersion
     * @return
     * @throws SQLException
     */
    public List<String> fetchDimensionMembers(String tablename,
                                                          int version) throws SQLException;
  
    /**
     * Returns the version value of the latest entry.
     * 
     * @param tablename dimension table name
     * @param columnName column name of the colum where version is stored 
     * @param local if ture, version of local table is queried, otherwise version of remote table
     * @return highest version number of all entries in given table
     */
    public int getLatestVersion(String tablename, String columnName, boolean local);
}
