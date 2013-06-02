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
     * Create a new connection to the database depending on the concrete implementation.
     */
    public void init(boolean mode) {
        if (mode == LOCAL)
            createLocalConnection();
        else
            createRemoteConnection();
    }
    
    /**
     * Returns either the local or remote connection.
     * 
     * @param mode
     * @return
     */
    public Connection getConnection(boolean mode) throws SQLException {
        Connection con = null;
        if (mode == LOCAL) {
              if (local == null)
                  this.init(mode);
              con = local;
        } else {
            if (remote == null) {
                this.init(mode);
            }
            con = remote;
        }
        
        if (con == null)
            throw new SQLException("Connection is null");
        
        return con;
    }

    private void createLocalConnection() {
      /*String jdbcURLString = AccessPreferences.getLocalDBURL();
      final Properties p = new Properties();
      p.setProperty("user", AccessPreferences.getLocalDBUser());
      p.setProperty("password", AccessPreferences.getLocalDBPassword());
       
      JDBCDriverManager.initialize(new File(
            ".." + File.separator + "lib" + File.separator + "h2-1.3.172.jar").toURI().toURL());
      JDBCDriverManager.registerDriver(new File(
            "/opt/oracle/product/10gR2/jdbc/lib/ojdbc14.jar").toURI().toURL(),
            "oracle.jdbc.driver.OracleDriver");
       
      final Connection conn = JDBCDriverManager.getConnection(jdbcURLString, p);*/
    /*    
      File lib = new File(".." + File.separator + "lib" + File.separator + "h2-1.3.172.jar");
      if (lib.exists()) {
          MyLogger.logMessage("lib exists");
      URLClassLoader child;
        try {
            // darf nur 1x aufgerufen werden!! --> Todo
            URL[] urls = new URL[1];
            urls[0] = lib.toURI().toURL();
            child = new URLClassLoader(urls, this.getClass().getClassLoader());
          MyLogger.logMessage("try Class.forName");
          Class.forName ("org.h2.Driver", true, child);
          //Method method = classToLoad.getDeclaredMethod ("myMethod");
          //Object instance = classToLoad.newInstance ();
          //Object result = method.invoke (instance);
            
          try {
            // insert connection request statement here
            MyLogger.logMessage("Try to get create a local DB connection");
            local = DriverManager.getConnection(
                AccessPreferences.getLocalDBURL(), AccessPreferences.getLocalDBUser(),
                AccessPreferences.getLocalDBPassword());
            MyLogger.logMessage("Local database connection successfully created");
          } catch (SQLException e) {
            MyLogger.logMessage(e.getMessage());
          }
        } catch (MalformedURLException e) {
          MyLogger.logMessage(e.getMessage());
        } catch (ClassNotFoundException e) {
              MyLogger.logMessage(e.getMessage());
            }

        } else
          MyLogger.logMessage("lib nicht gefunden");
        try {
            // insert driver load statement here
            MyLogger.logMessage("Try to load H2 driver for local DB");
            //Class.forName("org.h2.Driver");
            java.lang.Class.forName("org.h2.Driver");
          } catch (ClassNotFoundException ex) {
              MyLogger.logMessage(ex.getMessage());
          }
      try {
        // insert connection request statement here
        MyLogger.logMessage("Try to get create a local DB connection");
        local = DriverManager.getConnection(
            AccessPreferences.getLocalDBURL(), AccessPreferences.getLocalDBUser(),
            AccessPreferences.getLocalDBPassword());
        MyLogger.logMessage("Local database connection successfully created");
      } catch (SQLException e) {
        MyLogger.logMessage(e.getMessage());
      }*/
      
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
