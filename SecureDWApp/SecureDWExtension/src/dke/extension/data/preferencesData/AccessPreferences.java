package dke.extension.data.preferencesData;

import java.io.File;
import java.io.FileNotFoundException;

import javax.crypto.SecretKey;

import oracle.ide.Ide;

public class AccessPreferences {
    private static String sep = File.separator;
    
    public AccessPreferences() {
        super();
    }
    
    public static SecretKey getKey() throws FileNotFoundException {
        return KeyWrapper.getInstance().getKey();
    }
    
    public static String getLocalDBURL() {
        return "jdbc:sqlite:" + getExtensionDir() + sep + getSecureDWFileDir() + sep + "securedw.db";
    }
    
    public static String getLocalDBUser() {
        return "";
        }
    
  public static String getLocalDBPassword() {
      return "";
      }
  
  public static String getKeyFileName() {
      return "SecureDWKey.key";
  }
  
  public static String getExtensionDir() {
    return Ide.getProductHomeDirectory() + sep + "extensions";
  }
  
  public static String getSecureDWFileDir() {
      return "dke.extension.file";
  }
  
  public static String getEncryptionMode() {
      return "AES";
  }
  
  public static String getEncoding() {
      return "ascii";
  }
}
