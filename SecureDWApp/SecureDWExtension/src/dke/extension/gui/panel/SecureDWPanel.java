package dke.extension.gui.panel;

import dke.extension.mvc.SecureDWModel;

import oracle.javatools.ui.TransparentPanel;

public class SecureDWPanel extends TransparentPanel {
    private SecureDWModel model;
    
    public SecureDWPanel(SecureDWModel model) {
        super();
        this.model = model;
    }


    public SecureDWModel getModel() {
        return model;
    }
}
