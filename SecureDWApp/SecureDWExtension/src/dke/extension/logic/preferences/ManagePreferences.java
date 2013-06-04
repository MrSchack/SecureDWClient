package dke.extension.logic.preferences;

import dke.extension.data.preferencesData.ConnectionData;

public interface ManagePreferences {

    public void storeKey();

    public void storeConnectionData(String host, String port, String sid,
                                    String user, String pwd);

    public ConnectionData getConnectionData();


}
