package dke.extension.data.preferencesData;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class KeyWrapper {
    private static KeyWrapper keyWrapper = null;
    private String keyFileName = "SecureDWKey.key";
    private String keyEncoding = "ascii";
    private String encryptionMode = "AES";
    private SecretKey myKey;
    
    private KeyWrapper() throws FileNotFoundException {
        super();
        this.init();
    }

    protected void init() throws FileNotFoundException {
      StringBuilder keyString = new StringBuilder();
      //String NL = System.getProperty("line.separator");
      Scanner scanner = new Scanner(new FileInputStream(keyFileName), keyEncoding);
      try {
        while (scanner.hasNextLine()){
          keyString.append(scanner.nextLine());
        }
        byte[] keyBytes = Base64.decode(keyString.toString());
        this.myKey = new SecretKeySpec(keyBytes,0,keyBytes.length, encryptionMode);
      }
      finally{
        scanner.close();
      }
    }
    
    protected SecretKey getKey() {
        return this.myKey;
    }

    protected static KeyWrapper getInstance() throws FileNotFoundException {
        if (keyWrapper == null)
            keyWrapper = new KeyWrapper();
        return keyWrapper;
    }
}
