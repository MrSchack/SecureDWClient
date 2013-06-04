package dke.extension.data.initialize;

import dke.extension.data.dbConnection.ConnectionManager;

import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.logging.MyLogger;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBInitializer {
    private static String sqlFileName =
        ExtensionPreferencesData.getExtensionDir() + File.separator + ExtensionPreferencesData.getSecureDWFileDir() +
        File.separator + "data_dictionary.sql";
    
    public DBInitializer() {
        super();
    }
    
    public static void initDataDictionary() {
        Connection con;
        try {
            MyLogger.logMessage("start init data dicitonary ...");
            con = ConnectionManager.getInstance().localConnect();
            ScriptRunner runner = new ScriptRunner(con, false, true);
            runner.runScript(new BufferedReader(new FileReader(sqlFileName)));
            MyLogger.logMessage("finished init data dictionary");
        } catch (SQLException e) {
            MyLogger.logMessage("Error: " + e.getMessage());
        } catch (IOException e) {
            MyLogger.logMessage("Error: " + e.getMessage());
        }
    }
    
}
