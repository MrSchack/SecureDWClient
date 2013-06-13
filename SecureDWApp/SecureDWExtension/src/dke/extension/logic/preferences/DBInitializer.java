package dke.extension.logic.preferences;


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
    
    public static void initDataDictionary() throws SQLException, IOException{
        Connection con;
        MyLogger.logMessage("Initialize local data dicitonary ...");
        
        try {
            con = ConnectionManager.getInstance().localConnect();
            ScriptRunner runner = new ScriptRunner(con, false, true);
            runner.runScript(new BufferedReader(new FileReader(sqlFileName)));
            MyLogger.logMessage("... initializing local data dictionary done.");
        } catch (SQLException e) {
            MyLogger.logMessage("Error: " + e.getMessage());
        } catch (IOException e) {
            MyLogger.logMessage("Error: " + e.getMessage());
        }
    }
    
}
