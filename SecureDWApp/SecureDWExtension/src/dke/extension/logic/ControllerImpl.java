package dke.extension.logic;

import dke.extension.data.dbConnection.DBManager;
import dke.extension.data.dbConnection.DBManagerImpl;
import dke.extension.logic.preferences.DBInitializer;
import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.exception.SecureDWException;
import dke.extension.logging.MyLogger;
import dke.extension.logic.dimensionManagement.DimensionObject;
import dke.extension.logic.dimensionManagement.ManageDimension;
import dke.extension.logic.dimensionManagement.ManageDimensionImpl;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;

import dke.extension.mvc.SecureDWEvent;
import dke.extension.mvc.SecureDWListener;
import dke.extension.mvc.SecureDWModel;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.SQLException;

import oracle.ide.config.Preferences;

import org.bouncycastle.crypto.CryptoException;

public class ControllerImpl implements Controller {
    private ManagePreferences prefs;
    private ManageDimension dimensionManager;
    private static Controller controller;
    private SecureDWModel model;

    private ControllerImpl(SecureDWModel model) {
        super();
        this.model = model;
        this.model.addListener(new SecureDWListener() {

                public void focusGained(SecureDWEvent e) {
                }

                public void connectionDataValid(SecureDWEvent e) {
                    try {
                        initialize();
                    } catch (SQLException f) {
                        MyLogger.logMessage(f.getMessage());
                    } catch (IOException f) {
                        MyLogger.logMessage(f.getMessage());
                    } catch (SecureDWException f) {
                        MyLogger.logMessage(f.getMessage());
                    }
                }
            });
        
        prefs = new ManagePreferencesImpl();
        dimensionManager = new ManageDimensionImpl();
    }

    public static Controller getInstance(SecureDWModel model) {
        if (controller == null)
            controller = new ControllerImpl(model);
        return controller;
    }


    public void processQuery() {
    }

    public void insertFacts() {
    }

    public void insertDimensionMember(DimensionObject dimObject) throws CryptoException,
                                                                        NoSuchAlgorithmException,
                                                                        InvalidKeySpecException {

        dimensionManager.insertNewDimensionMember(dimObject);

    }

    public void initialize() throws SQLException, IOException, SecureDWException {
        this.initialize(false);
    }

    public void initialize(boolean force) throws SQLException, IOException,
                                                 SecureDWException {
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
