package dke.extension.action;

import dke.extension.data.dbConnection.ConnectionManager;
import dke.extension.data.initialize.DBInitializer;
import dke.extension.data.preferencesData.AccessPreferences;

import dke.extension.logging.MyLogger;

import dke.extension.logic.crypto.AESCryptEngineImpl;
import dke.extension.logic.crypto.CryptEngine;

import java.io.FileNotFoundException;

import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.Map;

import javax.swing.JOptionPane;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;

import org.bouncycastle.crypto.CryptoException;

@RegisteredByExtension("dke.extension")
public final class KeyWrapController implements Controller{
    public KeyWrapController() {
        super();
    }

    public boolean handleEvent(IdeAction ideAction, Context context) {
      if (context == null)
          return false;

      testLocalConnection();
      MyLogger.logMessage("------------------------");
      DBInitializer.initDataDictionary();
      //testEncryption();
        return false;
    }

    public boolean update(IdeAction ideAction, Context context) {
      ideAction.setEnabled(true);
      return true;
    }

    private void testLocalConnection() {
        MyLogger.logMessage("Start creating a connection");
        ConnectionManager conMgr = ConnectionManager.getInstance();
        
        Connection con;
        try {
            con = conMgr.getConnection(ConnectionManager.LOCAL);
            MyLogger.logMessage("End connection created!");
        } catch (SQLException e) {
          MyLogger.logMessage(e.getMessage());
        }
    }
    
    
    private void testEncryption() {
      MyLogger.logMessage(Ide.getProductHomeDirectory());
      AccessPreferences pref = new AccessPreferences();
      CryptEngine crypt = new AESCryptEngineImpl();
      
       try {
            if(pref.getKey() != null)
              MyLogger.logMessage("Key successfully created!");
            else
              MyLogger.logMessage("Error while creating key!");
           
          String plainText = "My Plaintext";
          byte[] iv = new byte[16];
           MyLogger.logMessage(plainText);
          
          byte[] encryptedString = crypt.encryptString(plainText, iv);
          MyLogger.logMessage(new String(encryptedString, "UTF-8"));
           
           String decryptedString = crypt.decryptString(encryptedString, iv);
          MyLogger.logMessage(decryptedString);
           
        } catch (FileNotFoundException e) {
            MyLogger.logMessage(e.getMessage());
          } catch (CryptoException e) {
           MyLogger.logMessage(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
          MyLogger.logMessage(e.getMessage());
        } catch (InvalidKeySpecException e) {
          MyLogger.logMessage(e.getMessage());
        } catch (UnsupportedEncodingException e) {
          MyLogger.logMessage(e.getMessage()); 
        }
    }
}
