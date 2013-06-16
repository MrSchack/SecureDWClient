package dke.extension.mvc;

import java.util.EventObject;

import javax.swing.tree.TreeModel;

import oracle.javatools.ui.TransparentPanel;

public class SecureDWEvent extends EventObject {
    
    private final TreeModel treeModel;
    private boolean validConnctionData = false;
    
    public SecureDWEvent(Object object) {
        this(object, null);
    }
    
    public SecureDWEvent(Object object, TreeModel treeModel) {
        super(object);
        this.treeModel = treeModel;
    }
    
    public SecureDWEvent(Object object, boolean validConnectionData) {
        this(object, null);
        this.validConnctionData = validConnectionData;
    }
    
    public TreeModel getTreeModel() {
        return this.treeModel;
    }

    public boolean isConnectionDataValid() {
        return this.validConnctionData;
    }

}
