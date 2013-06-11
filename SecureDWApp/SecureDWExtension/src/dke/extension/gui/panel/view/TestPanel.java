package dke.extension.gui.panel.view;

import dke.extension.data.dbConnection.ConnectionManager;
import dke.extension.data.dimension.DimensionTree;
import dke.extension.data.initialize.DBInitializer;

import dke.extension.data.preferencesData.KeyPreferencesData;
import dke.extension.logging.MyLogger;
import dke.extension.logic.Controller;
import dke.extension.logic.ControllerImpl;
import dke.extension.logic.crypto.AESCryptEngineImpl;
import dke.extension.logic.crypto.CryptEngine;

import dke.extension.logic.dimensionManagement.ManageDimension;

import dke.extension.logic.dimensionManagement.ManageDimensionImpl;

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

import javax.swing.tree.TreeNode;

import oracle.ide.Ide;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.FieldLayoutBuilder;

import org.bouncycastle.crypto.CryptoException;

public class TestPanel extends TransparentPanel  {
    private final JButton test = new JButton();
    private final JTree tree = fillJTree();
    private Controller controller;
    
    TestPanel() {
      this.controller = new ControllerImpl();
      
      initComponents();
      layoutComponents();
  }

  /**
   * Defines the basic layout for the components.
   */
  private void layoutComponents() {
      FieldLayoutBuilder b = new FieldLayoutBuilder(this);
      b.add(b.field().component(test).withText("test"));
      b.add(b.field().component(tree));
  }

    private void initComponents() {
        test.addActionListener(new TestListener());
    }

    private static JTree fillJTree() {
        ManageDimension m = new ManageDimensionImpl();
        DimensionTree<String> d = m.getDimensionTree();
        TreeNode root = new TreeNode();
        d.getRoot()
        JTree t = new JTree(root)
        
    }

    private class TestListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        testLocalConnection();
        MyLogger.logMessage("------------------------");
        //DBInitializer.initDataDictionary();
        //testEncryption();
      }
  }
  
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
