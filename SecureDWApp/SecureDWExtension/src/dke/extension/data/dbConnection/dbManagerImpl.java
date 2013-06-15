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

    public void fetchNewDimensionMembers(String tablename,
                                         int localVersion) throws Exception {
        ConnectionData data = prefManager.getRemoteConnectionData();
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        Connection con =
            connectionManager.remoteConnect(data.getHost(), data.getPort(),
                                            data.getSid(), data.getUser(),
                                            data.getPassword());

    }

    public int getCurrentDimensionVersion(Connection con, String tablename,
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


    /**
     * @param dimObject
     * @throws SQLException
     * @throws Exception
     */
    public void insertDimensionMembers(DimensionObject dimObject) throws SQLException,
                                                                         Exception {

        MyLogger.logMessage("inserting new dimension members on DW");

        ConnectionData data = prefManager.getRemoteConnectionData();

        // TODO
        // connection not established, immediatly closed


        ConnectionManager conManager = ConnectionManager.getInstance();

        String conData =
            data.getHost() + "" + data.getPassword() + "" + data.getPort() +
            "" + data.getSid() + "" + data.getUser();
        MyLogger.logMessage(conData);


        Connection con =
            conManager.remoteConnect(data.getHost(), data.getPort(),
                                     data.getSid(), data.getUser(),
                                     data.getPassword());


        String tablename = dimObject.getDimensionName();
        String query = ("INSERT INTO " + tablename + " VALUES(");
        MyLogger.logMessage(query.toString());
        for (int i = 0; i < dimObject.getDimensionMembers().size(); i++) {
            query += "?";
            if (i < dimObject.getDimensionMembers().size() - 1) {
                query += ",";
            }
        }
        query += ")";

        PreparedStatement stmt = con.prepareStatement(query);

        for (int i = 0; i <= dimObject.getDimensionMembers().size(); i++) {
            String key =
                (String)dimObject.getDimensionMembers().keySet().toArray()[i];
            String value = dimObject.getDimensionMembers().get(key);

            stmt.setString(i + 1, value);
        }

        MyLogger.logMessage(stmt.toString());

        stmt.executeUpdate();
    }

}
