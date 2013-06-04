package dke.extension.data.preferencesData;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;
import oracle.javatools.data.PropertyStorage;

/**
 * Data access class for storing, retrieving and transporting database connection data. Data is persisted using the IDE's preference API.
 */
public class ConnectionData extends HashStructureAdapter {

    private static final String DATA_KEY =
        "dke.extension.data.preferencesData.ConnectionData";
    private static final String HOST_KEY = "host";
    private static final String PORT_KEY = "port";
    private static final String SID_KEY = "sid";
    private static final String USER_KEY = "user";
    private static final String PWD_KEY = "pwd";

    public ConnectionData(HashStructure hash) {
        super(hash);
    }

    public static ConnectionData getInstance(PropertyStorage prefs) {
        return new ConnectionData(findOrCreate(prefs, DATA_KEY));
    }

    public String getHost() {
        return getHashStructure().getString(HOST_KEY);
    }

    public void setHost(String host) {
        getHashStructure().putString(HOST_KEY, host);
    }

    public String getPort() {
        return getHashStructure().getString(PORT_KEY);
    }

    public void setPort(String port) {
        getHashStructure().putString(PORT_KEY, port);
    }

    public String getSid() {
        return getHashStructure().getString(SID_KEY);
    }

    public void setSid(String sid) {
        getHashStructure().putString(SID_KEY, sid);
    }

    public String getUser() {
        return getHashStructure().getString(USER_KEY);
    }

    public void setUser(String user) {
        getHashStructure().putString(USER_KEY, user);
    }

    public String getPassword() {
        return getHashStructure().getString(PWD_KEY);
    }

    public void setPassword(String pwd) {
        getHashStructure().putString(PWD_KEY, pwd);
    }
}
