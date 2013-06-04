package dke.extension.data.preferencesData;

import java.io.File;

/**
 * Data access class for storing, retrieving and transporting database connection data. Data is persisted using the IDE's preference API.
 */
public class LocalConnectionData {

    public static final String PATH_TO_DB = 
        "jdbc:sqlite:" + ExtensionPreferencesData.getExtensionDir() + File.separator +
        ExtensionPreferencesData.getSecureDWFileDir() + File.separator + "securedw.db";
    public static final String USER = "secureDW";
    public static final String PWD = "secureDWPW";
}
