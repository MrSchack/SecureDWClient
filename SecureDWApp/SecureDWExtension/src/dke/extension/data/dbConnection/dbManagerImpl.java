package dke.extension.data.dbConnection;

import dke.extension.data.dimension.DataDictionary;
import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.data.preferencesData.LocalConnectionData;

import dke.extension.exception.SecureDWException;
import dke.extension.logging.MyLogger;
import dke.extension.logic.crypto.CastObjectTo;
import dke.extension.logic.dimensionManagement.DimensionObject;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;

import java.io.File;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

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

    public List<DimensionObject> fetchDimensionMembers(String tablename,
                                                       int version) throws Exception {
        ConnectionData data = prefManager.getRemoteConnectionData();
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        Connection con =
            connectionManager.remoteConnect(data.getHost(), data.getPort(),
                                            data.getSid(), data.getUser(),
                                            data.getPassword());

        connectionManager.disconnectRemote();
        return null;

    }

    public int getLatestVersion(String tablename, String columnName,
                                boolean local) throws SQLException,
                                                      SecureDWException {
        Connection con;
        int max = 0;
        String colAlias = "MAXVERS";

        if (local) {
            con = ConnectionManager.getInstance().localConnect();
        } else {
            ConnectionData data = prefManager.getRemoteConnectionData();
            if (!data.isAvailable())
                throw new SecureDWException("Error: No connection data available!");

            con =
 ConnectionManager.getInstance().remoteConnect(data.getHost(), data.getPort(),
                                               data.getSid(), data.getUser(),
                                               data.getPassword());
        }
        Statement stmt = con.createStatement();
        String query =
            "SELECT MAX(" + columnName + ") AS " + colAlias + " FROM " +
            tablename;

        // special for sqlite - add ;
        if (con.getMetaData().getDriverName().equals("SQLiteJDBC"))
            query = query + ";";

        ResultSet rs = stmt.executeQuery(query);
        int col;
        try {
            while (rs.next()) {
                col = rs.findColumn(colAlias);
                max = rs.getInt(col);
            }
        } catch (SQLException e) {
            // result set is empty
            max = 0;
        }

        // close connection
        if (!local)
            ConnectionManager.getInstance().disconnectRemote();

        return max;
    }

    public boolean localDBExists() {
        return localDB.exists();
    }

    public void deleteLocalDB() {
        if (this.localDBExists())
            localDB.delete();
    }

    public void insertDimensionMember(DimensionObject dimObject) throws SQLException,
                                                                        SecureDWException {

        Connection con;
        DataDictionary dataDictionary = new DataDictionary();

        if (dimObject.isEncrypted()) {
            ConnectionData data = prefManager.getRemoteConnectionData();
            if (!data.isAvailable())
                throw new SecureDWException("Error: No connection data available!");

            ConnectionManager conManager = ConnectionManager.getInstance();

            con =
 conManager.remoteConnect(data.getHost(), data.getPort(), data.getSid(),
                          data.getUser(), data.getPassword());
        } else {
            con = ConnectionManager.getInstance().localConnect();
        }


        String tablename = dimObject.getDimensionName().toUpperCase();
        String query = ("INSERT INTO " + tablename + " VALUES(");
        for (int i = 0; i < dimObject.getDimensionMembers().size(); i++) {
            query += "?";
            if (i < dimObject.getDimensionMembers().size() - 1) {
                query += ",";
            }
        }
        query += ")";
        PreparedStatement stmt = con.prepareStatement(query);

        for (int i = 0; i < dimObject.getDimensionMembers().size(); i++) {
            String key =
                (String)dimObject.getDimensionMembers().keySet().toArray()[i];
            String value = dimObject.getDimensionMembers().get(key);

            if (dimObject.isEncrypted()) {
                stmt.setString(i + 1, value);
            } else {
                if (dataDictionary.getDataType(tablename,
                                               key).equals("INTEGER")) {
                    int intValue =
                        CastObjectTo.getInteger(dimObject.getDimensionMembers().get(key));
                    stmt.setInt(i + 1, intValue);
                } else {
                    String stringValue =
                        dimObject.getDimensionMembers().get(key);
                    stmt.setString(i + 1, stringValue);
                }
            }
        }

        stmt.executeUpdate();
        if (dimObject.isEncrypted()) {
            ConnectionManager.getInstance().disconnectRemote();
        }

    }

}
