package dke.extension.logic;

import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;

public class ControllerImpl implements Controller {
    private ManagePreferences prefs;    
    
    public ControllerImpl() {
        super();
        prefs = new ManagePreferencesImpl();
    }

    public void processQuery() {
      checkInitState();
    }

    public void insertFacts() {
      checkInitState();
    }

    public void insertDimensionMember() {
      checkInitState();
    }

    private void checkInitState() {
        if (!prefs.firstInitDone()) {
            //TODO: init local database
            //TODO: update dimension tables
            //TODO: generate BIX
        }
    }
}
