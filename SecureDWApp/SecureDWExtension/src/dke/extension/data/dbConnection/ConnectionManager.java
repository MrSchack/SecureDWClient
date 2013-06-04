package dke.extension.data.dbConnection;

import dke.extension.data.preferencesData.AccessPreferences;
import dke.extension.logging.MyLogger;

import java.io.File;

import java.lang.reflect.InvocationTargetException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    /**
     * Indicator for a local connection.
     */
    public static final boolean LOCAL = true;
    /**
     * Indicator for a remote connection.
     */
    public static final boolean REMOTE = false;
       
    private static final ConnectionManager conMgr = new ConnectionManager();
    private static Connection local;
    private static Connection remote;

    static {
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) { 
            MyLogger.logMessage(e.getMessage());
        } 
    }

    private ConnectionManager() {
      super();   
    }
    
    public static ConnectionManager getInstance() {
        return conMgr;
   }
    
    /**
     * Returns either the local or remote connection.
     * 
     * @param mode
     * @return
     */
    public Connection getLocalConnection() throws SQLException {
        if (local == null)
              this.createLocalConnection();
        return local;
    }

    private void createLocalConnection() {
        try { 
            if (local != null) 
                return; 
            MyLogger.logMessage("Creating Connection to Database..."); 
            local = DriverManager.getConnection(
                      AccessPreferences.getLocalDBURL(),
                      AccessPreferences.getLocalDBUser(),
                      AccessPreferences.getLocalDBPassword()); 
            if (!local.isClosed()) 
                MyLogger.logMessage("...Connection established"); 
        } catch (SQLException e) { 
            MyLogger.logMessage(e.getMessage());
        } 

        Runtime.getRuntime().addShutdownHook(new Thread() { 
            public void run() { 
                try { 
                    if (!local.isClosed() && local != null) { 
                        local.close(); 
                        if (local.isClosed()) 
                            MyLogger.logMessage("Connection to Database closed"); 
                    } 
                } catch (SQLException e) { 
                  MyLogger.logMessage(e.getMessage());
                } 
            } 
        });

    }

    private void createRemoteConnection() {
        
    }
}
