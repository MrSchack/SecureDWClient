package dke.extension.logic.preferences;


import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.data.preferencesData.ExtensionPreferencesData;

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

    public ConnectionData getRemoteConnectionData() {
        Preferences preferences = Preferences.getPreferences();
        return ConnectionData.getInstance(preferences);
    }

    public void storeRemoteConnectionData(String host, String port, String sid,
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
        storeRemoteConnectionData(null, null, null, null, null);
    }

    public void setInitStatus(boolean initDone) {
        Preferences preferences = Preferences.getPreferences();
        ExtensionPreferencesData data = ExtensionPreferencesData.getInstance(preferences);
        data.setInitStatus(initDone);
    }

    public boolean firstInitDone() {
        Preferences preferences = Preferences.getPreferences();
        return ExtensionPreferencesData.getInstance(preferences).getInitStatus();
    }
}
