package dke.extension.logic.preferences;

import dke.extension.data.preferencesData.ConnectionData;

public interface ManagePreferences {

    /**
     * Stored submitted key file to the extension directory
     */
    public void storeKey();

    /**
     * Stores new database connection data to preferences api.
     * @param host the database host name
     * @param port the database host port
     * @param sid the database service id
     * @param user the database user
     * @param pwd the database password
     */
    public void storeRemoteConnectionData(String host, String port, String sid,
                                    String user, String pwd);

    /**
     * Retrieves currently stored remote database connection data.
     * @return the connection data
     */
    public ConnectionData getRemoteConnectionData();
    
    /**
     * Clears currently stored preferences.
     */
    public void clearPreferences();
    
     /**
      * Store init status of SecureDW extension for checking if
      * local database has to be initialized or not.
      * @param initDone
      */
    public void setInitStatus(boolean initDone);
     
    /**
     * Retrieve the init status of SecureDW extension in order to check
     * if local database has to be intialized for the first time.
     * @return true, is initialization of local database has to be done, otherwise false
     */
    public boolean firstInitDone();
}
