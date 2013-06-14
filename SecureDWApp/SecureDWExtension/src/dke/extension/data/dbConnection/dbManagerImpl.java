package dke.extension.data.dbConnection;

import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.data.preferencesData.LocalConnectionData;

import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;

import java.io.File;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.List;

public class DBManagerImpl implements DBManager {
    private File localDB;
    private ManagePreferences prefManager;
    
    public DBManagerImpl() {
        super();
        localDB = new File(
          ExtensionPreferencesData.getExtensionDir() + File.separator +
          ExtensionPreferencesData.getSecureDWFileDir() + File.separator + 
          LocalConnectionData.DBNAME);
        
        prefManager = new ManagePreferencesImpl();
    }
    
    public List<String> fetchDimensionMembers(String tablename,
                                                          int version) throws SQLException {
      ConnectionData data = prefManager.getRemoteConnectionData();
      ConnectionManager connectionManager = ConnectionManager.getInstance();
      
      Connection con = connectionManager.remoteConnect(data.getHost(),
                                                       data.getPort(),
                                                       data.getSid(),
                                                       data.getUser(),
                                                       data.getPassword());
        
      return null;
    }
    
    public int getLatestVersion(String tablename, String columnName, boolean local) {
        return 0;
    }
    
    public boolean localDBExists() {
      return localDB.exists();
    }

    public void deleteLocalDB() {
        if (this.localDBExists())
            localDB.delete();
    }
}
