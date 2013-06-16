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

    public List<DimensionObject> fetchDimensionMembers(String tablename,
                                                          int version) throws Exception {
        ConnectionData data = prefManager.getRemoteConnectionData();
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        Connection con =
            connectionManager.remoteConnect(data.getHost(), data.getPort(),
                                            data.getSid(), data.getUser(),
                                            data.getPassword());

      return null;
    }

    public int getLatestVersion(String tablename, String columnName, boolean local) throws
                                                                    SQLException, SecureDWException {
        Connection con;
        int max = 0;
        String colAlias = "MAXVERS";
        
        if (local) {
            con = ConnectionManager.getInstance().localConnect();
        } else {
            ConnectionData data = prefManager.getRemoteConnectionData();
            if (!data.isAvailable())
                throw new SecureDWException("Error: No connection data available!");
            
            con = ConnectionManager.getInstance().remoteConnect(data.getHost(),
                                                                data.getPort(),
                                                                data.getSid(),
                                                                data.getUser(),
                                                                data.getPassword());
        }
        Statement stmt = con.createStatement();
        MyLogger.logMessage(query);

        // special treatment for sqlite
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
        
        return max;
    }

    public boolean localDBExists() {
        return localDB.exists();
    }

    public void deleteLocalDB() {
        if (this.localDBExists())
            localDB.delete();
    }

    public void insertDimensionMembers(DimensionObject dimObject) throws SQLException,
                                                                         SecureDWException {

        MyLogger.logMessage("inserting new dimension members on DW");

        ConnectionData data = prefManager.getRemoteConnectionData();
        if (!data.isAvailable())
            throw new SecureDWException("Error: No connection data available!");
        
        ConnectionManager connectionManager = ConnectionManager.getInstance();


        // TODO
        // connection not established, immediatly closed

        Connection con =
            connectionManager.remoteConnect(data.getHost(), data.getPort(),
                                            data.getSid(), data.getUser(),
                                            data.getPassword());


        String tablename = dimObject.getDimensionName();
        String query = ("INSERT INTO " + tablename + " VALUES(?,?,?,?,?,?)");

        MyLogger.logMessage("for testing: " + query);


        PreparedStatement stmt = con.prepareStatement(query);

        for (int i = 0; i <= dimObject.getDimensionMembers().size(); i++) {
            String key =
                (String)dimObject.getDimensionMembers().keySet().toArray()[i];
            String value = dimObject.getDimensionMembers().get(key);

            stmt.setString(i+1, value); // statements counter starts with 1
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
