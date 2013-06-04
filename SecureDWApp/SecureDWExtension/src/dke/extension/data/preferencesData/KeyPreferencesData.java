package dke.extension.data.preferencesData;

import java.io.File;
import java.io.FileNotFoundException;

import javax.crypto.SecretKey;

import oracle.ide.Ide;

public class KeyPreferencesData {
    private static String sep = File.separator;
    
    public KeyPreferencesData() {
        super();
    }
    
    public static SecretKey getKey() throws FileNotFoundException {
        return KeyWrapper.getInstance().getKey();
    }
  
  public static String getEncryptionMode() {
      return "AES";
  }
  
  public static String getEncoding() {
      return "ascii";
  }
  
  public static String getKeyFileName() {
    return "SecureDWKey.key";
  }
}
