package dke.extension.data.dbConnection;

import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.data.preferencesData.LocalConnectionData;

import dke.extension.exception.SecureDWException;
import dke.extension.logging.MyLogger;
import dke.extension.logic.dimensionManagement.DimensionObject;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;

import java.io.File;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.Iterator;
import java.util.List;

public class DBManagerImpl implements DBManager {
    private File localDB;
    private ManagePreferences prefManager;

    public DBManagerImpl() {
        super();
        localDB =
                new File(ExtensionPreferencesData.getExtensionDir() + File.separator +
                         ExtensionPreferencesData.getSecureDWFileDir() +
                         File.separator + LocalConnectionData.DBNAME);

        prefManager = new ManagePreferencesImpl();
    }

    public List<DimensionObject> fetchNewDimensionMembers(String tablename,
                                                          int localVersion) throws Exception {
        ConnectionData data = prefManager.getRemoteConnectionData();
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        Connection con =
            connectionManager.remoteConnect(data.getHost(), data.getPort(),
                                            data.getSid(), data.getUser(),
                                            data.getPassword());

      return null;
    }

    public int getLatestVersion(String tablename, String columnName, boolean local) {
                                          String columnName) {
        return 0;
    }

    public boolean localDBExists() {
        return localDB.exists();
    }

    public void deleteLocalDB() {
        if (this.localDBExists())
            localDB.delete();
    }

    public int getLatestEntryVersion(String tablename) {
        return 0;
    }


    public void insertDimensionMembers(DimensionObject dimObject) throws SQLException,
                                                                         Exception {

        MyLogger.logMessage("inserting new dimension members on DW");

        ConnectionData data = prefManager.getRemoteConnectionData();
        ConnectionManager connectionManager = ConnectionManager.getInstance();


        // TODO
        // connection not established, immediatly closed

        Connection con =
            connectionManager.remoteConnect(data.getHost(), data.getPort(),
                                            data.getSid(), data.getUser(),
                                            data.getPassword());


        String tablename = dimObject.getDimensionName();
        String query = ("INSERT INTO " + tablename + "VALUES(?,?,?,?,?,?)");

        MyLogger.logMessage("for testing: " + query);


        PreparedStatement stmt = con.prepareStatement(query);

        for (int i = 0; i <= dimObject.getDimensionMembers().size(); i++) {
            String key =
                (String)dimObject.getDimensionMembers().keySet().toArray()[i];
            String value = dimObject.getDimensionMembers().get(key);

            stmt.setString(i, value);
        }

        MyLogger.logMessage(stmt.toString());

        /*
        ResultSet rs = stmt.executeQuery(query);
        {
            while (rs.next()) {
                cryptTableName = (String)rs.getObject(1);
            }

            return cryptTableName;
        }
    }*/
    }

}
