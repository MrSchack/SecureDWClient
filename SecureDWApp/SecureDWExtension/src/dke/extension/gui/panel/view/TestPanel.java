package dke.extension.gui.panel.view;

import dke.extension.data.dbConnection.ConnectionManager;
import dke.extension.data.initialize.DBInitializer;
import dke.extension.data.preferencesData.AccessPreferences;

import dke.extension.logging.MyLogger;
import dke.extension.logic.crypto.AESCryptEngineImpl;
import dke.extension.logic.crypto.CryptEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;


import oracle.ide.Ide;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.FieldLayoutBuilder;

import org.bouncycastle.crypto.CryptoException;

public class TestPanel extends TransparentPanel  {
    private final JButton test = new JButton();
    
    TestPanel() {
      initComponents();
      layoutComponents();
  }

  /**
   * Defines the basic layout for the components.
   */
  private void layoutComponents() {
      FieldLayoutBuilder b = new FieldLayoutBuilder(this);
      b.add(b.field().component(test).withText("test"));
  }

    private void initComponents() {
        test.addActionListener(new TestListener());
    }
            
  private class TestListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        testLocalConnection();
        MyLogger.logMessage("------------------------");
        DBInitializer.initDataDictionary();
        //testEncryption();
      }
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
