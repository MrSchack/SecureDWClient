package dke.extension.data.preferencesData;

import java.io.File;

import oracle.ide.Ide;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;
import oracle.javatools.data.PropertyStorage;


public class ExtensionPreferencesData extends HashStructureAdapter {
    private static final String DATA_KEY =
        "dke.extension.data.preferencesData.ExtensionPreferencesData";
    private static final String INIT_DONE_KEY = "initDone";
    private static String sep = File.separator;
    
    public ExtensionPreferencesData(HashStructure hash) {
        super(hash);
    }
  
    public static String getExtensionDir() {
      return Ide.getProductHomeDirectory() + "extensions" + sep + "dke.securedw";
    }
  
    public static String getSecureDWFileDir() {
      return "files";
    }
    
    public static String getInitSQLScriptName() {
        return "data_dictionary.sql";
    }
    
    public static ExtensionPreferencesData getInstance(PropertyStorage prefs) {
        return new ExtensionPreferencesData(findOrCreate(prefs, DATA_KEY));
    }
    
    /**
     * Sets the init status of SecureDW extension
     * @param initDone
     */
    public void setInitStatus(boolean initDone) {
        getHashStructure().putBoolean(INIT_DONE_KEY, initDone);
    }
    
    /**
     * Returns the init status of SecureDW extension
     * @return
     */
    public boolean getInitStatus() {
        return getHashStructure().getBoolean(INIT_DONE_KEY);
    }
}
