package dke.extension.logic.preferences;


import dke.extension.data.preferencesData.ConnectionData;

import oracle.ide.config.Preferences;

/**
 * Javadoc @see {@link dke.extension.logic.preferences.ManagePreferences}
 */
public class ManagePreferencesImpl implements ManagePreferences {
    public ManagePreferencesImpl() {
        super();
    }

    public void storeKey() {
    }

    public ConnectionData getConnectionData() {
        Preferences preferences = Preferences.getPreferences();
        return ConnectionData.getInstance(preferences);
    }

    public void storeConnectionData(String host, String port, String sid,
                                    String user, String pwd) {
        Preferences preferences = Preferences.getPreferences();
        ConnectionData data = ConnectionData.getInstance(preferences);
        data.setHost(host);
        data.setPort(port);
        data.setSid(sid);
        data.setUser(user);
        data.setPassword(pwd);
    }

    public void clearPreferences() {
        storeConnectionData(null, null, null, null, null);
    }
}
