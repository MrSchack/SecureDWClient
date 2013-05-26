package dke.extension.data.preferencesData;

import javax.crypto.SecretKey;

public class AccessPreferences {
    private static SecretKey myKey = null;
    
    public AccessPreferences() {
        super();
    }
    
    public static SecretKey getKey() {
        return myKey;
    }
}
