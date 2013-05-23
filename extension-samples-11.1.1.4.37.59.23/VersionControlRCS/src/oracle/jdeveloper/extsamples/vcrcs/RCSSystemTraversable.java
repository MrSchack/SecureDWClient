/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.awt.Component;
import java.awt.GridBagLayout;

import java.util.Collections;
import java.util.Map;

import javax.swing.JPanel;

import oracle.jdeveloper.vcs.spi.VCSPropertyTraversable;


/**
 * This is a dummy traversable class so that the two children traversables can 
 * be held
 */

public class RCSSystemTraversable extends VCSPropertyTraversable {
    private UI _ui = null;

    public RCSSystemTraversable() {
        super();
    }

    protected Component getPropertyPage() {
        if (_ui == null) {
            _ui = new UI();
        }

        return _ui;
    }

    protected String getDataKey() {
        return null;
    }

    protected void validateProperties() {
    }

    protected Map<String,?> getProperties() {
        return Collections.emptyMap();
    }

    protected void setProperties(Map properties) {
    }

    private final class UI extends JPanel {
        private UI() {
            super(new GridBagLayout());
        }

    }


}
