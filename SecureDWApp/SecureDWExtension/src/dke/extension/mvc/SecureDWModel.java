package dke.extension.mvc;

import dke.extension.data.dimension.DimensionNode;
import dke.extension.data.dimension.DimensionTree;
import dke.extension.exception.SecureDWException;
import dke.extension.logging.MyLogger;
import dke.extension.logic.ControllerImpl;
import dke.extension.logic.dimensionManagement.ManageDimension;
import dke.extension.logic.dimensionManagement.ManageDimensionImpl;

import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class SecureDWModel {
    private List<SecureDWListener> listeners;
    private TreeModel treeModel;
    private static SecureDWModel model;

    static {
        model = new SecureDWModel();
    }

    private SecureDWModel() {
        super();
    }

    public static SecureDWModel getInstance() {
        return model;
    }
    
    public void connectionValid() {
      if (listeners != null) {
          SecureDWEvent evt = new SecureDWEvent(this, null);
          for (SecureDWListener l : listeners) {
              l.connectionDataValid(evt);
          }
      }
    }

    public void updateTree() {
        try {
            treeModel = fillJTree();
        } catch (SecureDWException e) {
            MyLogger.logMessage("Error: " + e.getMessage());
            MyLogger.logMessage("Local DB has to be initialized!");

            if (e.forceInit()) {
                // if local DB is corrupt, force initialization of local DB
                try {
                    ControllerImpl.getInstance(this).initialize(true);
                } catch (IOException e1) {
                    MyLogger.logMessage(e1.getMessage());
                } catch (SQLException e2) {
                    MyLogger.logMessage(e2.getMessage());
                } catch (SecureDWException f) {
                    MyLogger.logMessage(f.getMessage());
                }
            }
        } catch (SQLException e) {
            MyLogger.logMessage(e.getMessage());
        }

        fireTreeChanged(treeModel);
    }

    public void addListener(SecureDWListener l) {
        if (listeners == null)
            listeners = new ArrayList<SecureDWListener>();

        listeners.add(l);
    }

    /**
     * Creates a TreeModel and fills it recursively with the DimensionTree.
     * To start the recursion, the root of the DimensionTree has to have children.
     * @return TreeModel
     */
    private static TreeModel fillJTree() throws SQLException,
                                                SecureDWException {
        ManageDimension m = new ManageDimensionImpl();
        DimensionTree<String> d;
        DefaultMutableTreeNode root = null;

        d = m.getDimensionTree();

        root = new DefaultMutableTreeNode(d.getRoot().getName() + " " + d.getRoot().getAttributes());

        if (d.getRoot().getChildren() != null &&
            !d.getRoot().getChildren().isEmpty()) {
            buildJTree(root,
                       d.getRoot()); // calls buildJTree for recursive filling
        }

        return new DefaultTreeModel(root);
    }

    /**
     * This is the recursive build of a JTree. A DimensionNode is the source of data to fill in the tree.
     * If a child of a DimensionNode has children, this method calls itself with the child as input parameter
     * @param n is the DefaultMutableTreeNode to fill with data and children
     * @param d is the DimensionNode where we get the data from
     */
    private static void buildJTree(DefaultMutableTreeNode n,
                                   DimensionNode<String> d) {
        for (DimensionNode<String> dChild : d.getChildren()) {
            DefaultMutableTreeNode nChild =
                new DefaultMutableTreeNode(dChild.getName() + " " +
                                           dChild.getAttributes());
            n.add(nChild);
            if (dChild.getChildren() != null &&
                !dChild.getChildren().isEmpty()) {
                buildJTree(nChild, dChild);
            }
        }

    }

    private void fireTreeChanged(TreeModel treeModel) {
        if (listeners != null) {
            SecureDWEvent evt = new SecureDWEvent(this, treeModel);
            for (SecureDWListener l : listeners) {
                l.focusGained(evt);
            }
        }
    }
}
