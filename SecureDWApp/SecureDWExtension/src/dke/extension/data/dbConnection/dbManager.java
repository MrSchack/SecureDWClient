package dke.extension.data.dbConnection;

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
     */
    public List<DimensionObject> fetchNewDimensionMembers(String tablename,
                                                          int localVersion);
  
    /**
     * Returns the most version value of the latest entry.
     * 
     * @param tablename Table name of local dimension table
     * @return
     */
    public int getLatestEntryVersion(String tablename);
}
