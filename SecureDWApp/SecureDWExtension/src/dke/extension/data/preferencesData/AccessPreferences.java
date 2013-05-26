package dke.extension.data.preferencesData;

import java.io.FileNotFoundException;

import javax.crypto.SecretKey;

public class AccessPreferences {
    
    public AccessPreferences() {
        super();
    }
    
    public static SecretKey getKey() throws FileNotFoundException {
        return KeyWrapper.getInstance().getKey();
    }
}
