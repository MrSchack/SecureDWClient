package dke.extension.logic.validation;

import java.sql.Connection;

import java.sql.DriverManager;

import java.util.Properties;

import oracle.jdbc.OracleDriver;

/**
 * Connection provider for this example. Holds the currently configured jdbc connection in a singleton bean in order to allow only one connection per
 */
public class testConnection {
    private static testConnection instance;
    private static Connection connection;

    /*
    public static testConnection getInstance() {
        if (instance == null) {
            instance = new testConnection();
        }
        return instance;
    }
    */

    public testConnection(String host, int port, String sid, String name,
                          String pwd) {


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
    public Connection connect(String host, String port, String sid,
                              String name, String pwd) throws Exception {
        if (connection != null) {
            connection.rollback();
            connection.close();
        }
        connection = null;
        Properties props = new Properties();
        props.put("user", name);
        props.put("password", pwd);
        String connectionString =
            "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        DriverManager.registerDriver(new OracleDriver());
        connection = DriverManager.getConnection(connectionString, props);
        return connection;
    }

    /**
     *Returns the current connection
     * @return the current connection or <code>null</code> if the connection has not been established.
     */
    public Connection getConnection() {
        return connection;
    }
}
