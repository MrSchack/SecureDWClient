package dke.extension.data.initialize;


import dke.extension.data.dbConnection.ConnectionManager;
import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.data.preferencesData.LocalConnectionData;
import dke.extension.logging.MyLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;


public class DBInitializer {
    private static String sqlFileName =
        ExtensionPreferencesData.getExtensionDir() + File.separator + 
        ExtensionPreferencesData.getSecureDWFileDir() + File.separator + 
        ExtensionPreferencesData.getInitSQLScriptName();
    
    public DBInitializer() {
        super();
    }
    
    public static void initDataDictionary() throws SQLException, IOException{
        Connection con;
        MyLogger.logMessage("Start init local data dicitonary ...");

        File db = new File(
              ExtensionPreferencesData.getExtensionDir() + File.separator +
              ExtensionPreferencesData.getSecureDWFileDir() + File.separator + 
              LocalConnectionData.DBNAME);
        if (db.exists()) {
            throw new IOException("Local database already exists.\n  -> Delete local database (LocalConnectionData.PATH_TO_DB) and restart SQL Developer in order to continue!");
        }
        
        try {
            con = ConnectionManager.getInstance().localConnect();
            ScriptRunner runner = new ScriptRunner(con, false, true);
            runner.runScript(new BufferedReader(new FileReader(sqlFileName)));
            MyLogger.logMessage("...  finished init data dictionary!");
        } catch (SQLException e) {
            MyLogger.logMessage("Error: " + e.getMessage());
        } catch (IOException e) {
            MyLogger.logMessage("Error: " + e.getMessage());
        }
    }
    
}
