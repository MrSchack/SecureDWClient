package dke.extension.mvc;

import java.util.EventObject;

import javax.swing.tree.TreeModel;

import oracle.javatools.ui.TransparentPanel;

public class SecureDWEvent extends EventObject {
    
    private final TreeModel treeModel;
    
    public SecureDWEvent(Object object, TreeModel treeModel) {
        super(object);
        this.treeModel = treeModel;
    }
    
    public TreeModel getTreeModel() {
        return this.treeModel;
    }
}
