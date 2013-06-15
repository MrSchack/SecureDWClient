package dke.extension.data.dbConnection;


import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.data.preferencesData.LocalConnectionData;
import dke.extension.logging.MyLogger;

import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLClassLoader;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

import oracle.jdbc.OracleDriver;


public class ConnectionManager {
    private static final ConnectionManager conMgr = new ConnectionManager();
    private static Connection local;
    private static Connection remote;
    private static Class driverClass;

    // load sqlite driver
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
    public Connection localConnect() throws SQLException {
        if (local == null) {

            //MyLogger.logMessage("Creating Connection to local Database...");
            local =
DriverManager.getConnection(LocalConnectionData.PATH_TO_DB, LocalConnectionData.USER,
                            LocalConnectionData.PWD);

            /*if (!local.isClosed())
                MyLogger.logMessage("...Connection established"); */

            Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            if (!local.isClosed() && local != null) {
                                local.close();
                                if (local.isClosed())
                                    MyLogger.logMessage("Connection to local Database closed");
                            }
                        } catch (SQLException e) {
                            MyLogger.logMessage(e.getMessage());
                        }
                    }
                });
        }

        return local;
    }

    /**
     * Establishes a new jdbc connection. If there is still an open connection, all active transactions will be rolled back and the  connection will be closed.
     * @param host the host name
     * @param port the host port
     * @param sid the database's services name
     * @param name the user name
     * @param pwd the user's password
     * @return the new connection
     * @throws Exception thrown if an error occurs during connection establishing
     */
    public Connection remoteConnect(String host, String port, String sid,
                                    String name, String pwd) throws Exception {
        if (remote != null) {
            remote.rollback();
            remote.close();
        }
        remote = null;
        Properties props = new Properties();
        props.put("user", name);
        props.put("password", pwd);
        String connectionString =
            "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        DriverManager.registerDriver(new OracleDriver());
        remote = DriverManager.getConnection(connectionString, props);
        return remote;
    }

    public void disconnectRemote() throws Exception {
        if (remote != null) {
            remote.close();
        }
    }
}
