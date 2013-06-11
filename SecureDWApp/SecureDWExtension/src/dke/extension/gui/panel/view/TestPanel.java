package dke.extension.gui.panel.view;

import dke.extension.data.dbConnection.ConnectionManager;
import dke.extension.data.dimension.DimensionNode;
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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import oracle.ide.Ide;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.FieldLayoutBuilder;

import org.bouncycastle.crypto.CryptoException;

public class TestPanel extends TransparentPanel  {
    private final JButton test = new JButton();
    private JTree tree; 
    private Controller controller;
    private FieldLayoutBuilder fb;
    
    TestPanel() {
      this.controller = new ControllerImpl();
      
      initComponents();
      layoutComponents();
  }

  /**
   * Defines the basic layout for the components.
   */
  private void layoutComponents() {
      fb = new FieldLayoutBuilder(this);
      fb.add(fb.field().component(test).withText("test"));
      
  }

    private void initComponents() {
        test.addActionListener(new TestListener());
    }

    
    /**
     * Creates a TreeModel and fills it recursively with the DimensionTree. 
     * To start the recursion, the root of the DimensionTree has to have children.
     * @return TreeModel
     */
    private static TreeModel fillJTree() {
      ManageDimension m = new ManageDimensionImpl();
      DimensionTree<String> d = m.getDimensionTree();
      DefaultMutableTreeNode root = new DefaultMutableTreeNode(d.getRoot().getName() + " " + d.getRoot().getAttributes());
      
      if(d.getRoot().getChildren()!=null && !d.getRoot().getChildren().isEmpty()){
          buildJTree(root, d.getRoot()); // calls buildJTree for recursive filling
      }
      
      return new DefaultTreeModel(root);
        
    }

     /**
     * This is the recursive build of a JTree. A DimensionNode is the source of data to fill in the tree.
     * If a child of a DimensionNode has children, this method calls itself with the child as input parameter
     * @param n is the DefaultMutableTreeNode to fill with data and children
     * @param d is the DimensionNode where we get the data from
     */
     private static void buildJTree (DefaultMutableTreeNode n,DimensionNode<String> d){
        for(DimensionNode<String> dChild: d.getChildren()){
          DefaultMutableTreeNode nChild = new DefaultMutableTreeNode(dChild.getName() + " " + dChild.getAttributes());
          n.add(nChild);
            if(dChild.getChildren()!=null && !dChild.getChildren().isEmpty()){
              buildJTree(nChild, dChild);
            }
        }
      
    }
    private class TestListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        //testLocalConnection();
       // MyLogger.logMessage("------------------------");
        //DBInitializer.initDataDictionary();
        //testEncryption();
        tree = new JTree(fillJTree());
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        fb.add(fb.field().component(tree));
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
