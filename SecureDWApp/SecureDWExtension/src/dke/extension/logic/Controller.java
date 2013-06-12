package dke.extension.logic;

import java.io.IOException;

import java.sql.SQLException;

public interface Controller {

    public void processQuery();

    public void insertFacts();

    public void insertDimensionMember();
    
    /**
     * Checks, if first initialization after installation was already done. If not,
     * starts initializing by
     *  - set up local db
     *  - check if connection data exists
     *  if yes 
     *    - update dimension tables
     *    - generate BIX
     *  else
     *    - throw Exception, that no connection data is available
     *  @throws Exception that no connection data is available
     */
    public void checkInitState() throws SQLException, IOException;
    
    /**
     * Updates local database by first comparing remote version of dimension tables
     * and local version. If remote version is newer, all new tuples are retrieved from the server.
     * During the insert process of new dimension members into the local database, BIX values are
     * calculated and stored.
     */
    public void updateLocalDB();
}
