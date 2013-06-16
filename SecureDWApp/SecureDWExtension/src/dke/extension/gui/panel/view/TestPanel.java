
package dke.extension.gui.panel.view;

import dke.extension.data.dbConnection.ConnectionManager;

import dke.extension.data.preferencesData.KeyPreferencesData;
import dke.extension.gui.panel.SecureDWPanel;
import dke.extension.logging.MyLogger;
import dke.extension.logic.crypto.AESCryptEngineImpl;
import dke.extension.logic.crypto.CryptEngine;

import dke.extension.mvc.SecureDWEvent;
import dke.extension.mvc.SecureDWListener;
import dke.extension.mvc.SecureDWModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;


import javax.swing.JTree;

import javax.swing.tree.TreeModel;

import oracle.ide.Ide;

import oracle.javatools.ui.layout.FieldLayoutBuilder;

import org.bouncycastle.crypto.CryptoException;

public class TestPanel extends SecureDWPanel  {
    private final JButton test = new JButton();
    private JTree tree; 
    private FieldLayoutBuilder fb;
    
    public TestPanel(SecureDWModel model) {
      super(model);
      layoutComponents();
      
      model.addListener(new SecureDWListener() {
          public void focusGained(SecureDWEvent e) {
              initTree(e.getTreeModel());
          }

          public void connectionDataChanged(SecureDWEvent e) {
          }

          public void initComplete(SecureDWEvent e) {
          }
      });
    }

    /**
     * Defines the basic layout for the components.
     */
  private void layoutComponents() {
      fb = new FieldLayoutBuilder(this);
      //fb.add(fb.field().component(test).withText("test"));
  }

     private void initTree(TreeModel treeModel) {
         if (tree != null) // clear layout
          this.remove(tree);
         
         if (treeModel != null) {
            tree = new JTree(treeModel);
            for (int i = 0; i < tree.getRowCount(); i++) {
              tree.expandRow(i);
            }
            fb.add(fb.field().component(tree));
         }
    }
     
    private class TestListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        //testLocalConnection();
        //testEncryption();
      }
    }
  
  /* - TEST FUNCTIONS ----------------------------- */
  private void testLocalConnection() {
      ConnectionManager conMgr = ConnectionManager.getInstance();
      
      Connection con;
      try {
          con = conMgr.localConnect();
          MyLogger.logMessage("End connection created!");
      } catch (SQLException e) {
        MyLogger.logMessage(e.getMessage());
      }
  }
  
  
  private void testEncryption() {
    MyLogger.logMessage(Ide.getProductHomeDirectory());
    CryptEngine crypt = new AESCryptEngineImpl();
    
     try {
          if(KeyPreferencesData.getKey() != null)
            MyLogger.logMessage("Key successfully created!");
          else
            MyLogger.logMessage("Error while creating key!");
         
        String plainText = "My Text";
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
