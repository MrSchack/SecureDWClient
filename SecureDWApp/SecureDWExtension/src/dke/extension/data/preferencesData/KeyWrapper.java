package dke.extension.data.preferencesData;

import dke.extension.logging.MyLogger;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.util.Scanner;

import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;

import oracle.ide.Ide;

import org.bouncycastle.util.encoders.Base64;

public class KeyWrapper {
    private static KeyWrapper keyWrapper = null;
    private String sep = File.separator;
    private String keyFileName =  "SecureDWKey.key";
    private String extensionPath = Ide.getProductHomeDirectory() + sep + "extensions";
    private String keyFilePath = extensionPath + sep + "dke.extension.file";
    private String keyEncoding = "ascii";
    private String encryptionMode = "AES";
    private SecretKey myKey;
    
    private KeyWrapper() throws FileNotFoundException {
        super();
        this.copyKeyFile();
        this.init();
    }

    protected void init() throws FileNotFoundException {
      MyLogger.logMessage("Start init");
      StringBuilder keyString = new StringBuilder();
      //String NL = System.getProperty("line.separator");
      Scanner scanner = new Scanner(new FileInputStream(keyFilePath + sep + keyFileName), keyEncoding);
      try {
        while (scanner.hasNextLine()){
          keyString.append(scanner.nextLine());
        }
        MyLogger.logMessage("Key: " + keyString.toString());
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

    private void copyKeyFile() {
      try{
          // create extension dke.extension.file directory
          File extDir = new File(extensionPath);
          if (!extDir.exists()) {
              extDir.mkdir();
              MyLogger.logMessage("Dir created: " + extDir.getAbsolutePath());
          } else
                MyLogger.logMessage("dir exists: " + extDir.getAbsolutePath());
          
          
          File keyDir = new File(keyFilePath);
          if (!keyDir.exists()) {
              keyDir.mkdir();
              MyLogger.logMessage("Dir created: " + keyDir.getAbsolutePath());
          } else
                MyLogger.logMessage("dir exists: " + keyDir.getAbsolutePath());
          
          // part for testing purpose starts
          // copy file
          File f1 = new File(
              "classes" + sep + "dke" + sep + "extension" + sep + "data" + sep +
              "preferencesData" + sep + keyFileName);
          File f2 = new File(keyFilePath + sep + keyFileName);
          
          if (f1.exists())
               MyLogger.logMessage("keyfile in jar exists: " + f1.getAbsolutePath());
          if (f2.exists())
               MyLogger.logMessage("keyfile is already available: " + f2.getAbsolutePath());
          else {
                MyLogger.logMessage("keyfile is beeing copied to: " + f2.getAbsolutePath());
          
                InputStream in = new FileInputStream(f1);

                //For Append the file.
                //OutputStream out = new FileOutputStream(f2,true);

                //For Overwrite the file.
                OutputStream out = new FileOutputStream(f2);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0){
                  out.write(buf, 0, len);
                }
                in.close();
                out.close();
                MyLogger.logMessage("KeyFile copied");
              }
              }
              catch(FileNotFoundException ex){
                MyLogger.logMessage("FileNotFoundEx: " + ex.getMessage() + " in the specified directory.");
              }
              catch(IOException e){
                MyLogger.logMessage("IOEx: " + e.getMessage());      
              }
        
    }
}
