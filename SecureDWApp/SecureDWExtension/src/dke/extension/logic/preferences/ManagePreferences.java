package dke.extension.logic.preferences;

import dke.extension.data.preferencesData.ConnectionData;

public interface ManagePreferences {

    public void storeKey();

    /**
     * Stores new database connection data to preferences api.
     * @param host the database host name
     * @param port the database host port
     * @param sid the database service id
     * @param user the database user
     * @param pwd the database password
     */
    public void storeConnectionData(String host, String port, String sid,
                                    String user, String pwd);

    /**
     * Retrieves currently stored database connection data.
     * @return the connection data
     */
    public ConnectionData getConnectionData();

    /**
     * Clears currently stored preferences.
     */
    public void clearPreferences();
}
