package dke.extension.logic;

import dke.extension.data.dbConnection.DBManager;
import dke.extension.data.dbConnection.DBManagerImpl;
import dke.extension.logic.preferences.DBInitializer;
import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.logging.MyLogger;
import dke.extension.logic.dimensionManagement.DimensionObject;
import dke.extension.logic.dimensionManagement.ManageDimension;
import dke.extension.logic.dimensionManagement.ManageDimensionImpl;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;

import dke.extension.mvc.SecureDWModel;

import java.io.IOException;

import java.sql.SQLException;

import oracle.ide.config.Preferences;

public class ControllerImpl implements Controller {
    private ManagePreferences prefs;
    private ManageDimension dimensionManager;
    private static Controller controller;
    private SecureDWModel model;

    static {
        controller = new ControllerImpl();
    }

    private ControllerImpl() {
        super();
        prefs = new ManagePreferencesImpl();
        dimensionManager = new ManageDimensionImpl();
    }

    public static Controller getInstance(SecureDWModel model) {
        controller.setModel(model);
        return controller;
    }


    public void processQuery() {
    }

    public void insertFacts() {
    }

    public void insertDimensionMember(DimensionObject dimObject) {
        dimensionManager.insertNewDimensionMember(dimObject);

    }

    public void initialize() throws SQLException, IOException {
        this.initialize(false);
    }

    public void initialize(boolean force) throws SQLException, IOException {
        DBManager db = new DBManagerImpl();

        Preferences preferences = Preferences.getPreferences();
        ExtensionPreferencesData extensionPrefs =
            ExtensionPreferencesData.getInstance(preferences);

        // initialize local db
        if (!db.localDBExists() || !extensionPrefs.lastInitSucessfull() ||
            force) {
            extensionPrefs.setInitSuccessfull(false);

            if (db.localDBExists())
                db.deleteLocalDB();

            DBInitializer.initDataDictionary();
            extensionPrefs.setInitSuccessfull(true);
        }
        this.model.updateTree();

        // update local dimension tables and BIX
        dimensionManager.updateLocalDimensions();
    }

    public SecureDWModel getModel() {
        return model;
    }

    public void setModel(SecureDWModel model) {
        this.model = model;
    }
}
