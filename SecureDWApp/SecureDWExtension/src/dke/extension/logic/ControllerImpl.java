package dke.extension.logic;

import dke.extension.data.initialize.DBInitializer;
import dke.extension.logging.MyLogger;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;

import java.io.IOException;

import java.sql.SQLException;

public class ControllerImpl implements Controller {
    private ManagePreferences prefs;    
    
    public ControllerImpl() {
        super();
        prefs = new ManagePreferencesImpl();
    }

    public void processQuery() {
    }

    public void insertFacts() {
    }

    public void insertDimensionMember() {
    }

    public void checkInitState() throws SQLException, IOException {
        if (!prefs.firstInitDone()) {
            MyLogger.logMessage("start init in controller");
            DBInitializer.initDataDictionary();
            this.updateLocalDB();
            //prefs.setInitStatus(true);
        }
    }
    
    public void updateLocalDB() {
      //TODO: update dimension tables
      //TODO: generate BIX
    }
}
