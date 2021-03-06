/* $Header: Harness.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import oracle.ide.IdeConstants;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.Dockable;
import oracle.ide.docking.DockableFactory;
import oracle.ide.docking.DockingParam;
import oracle.ide.layout.ViewId;

public class OpenNodesDockableFactory implements DockableFactory {
    private final OpenNodesDockable dockable = new OpenNodesDockable();
    
    static final String ID = "openNodesSample";
    public OpenNodesDockableFactory() {
        DockStation.getDockStation().registerDockableFactory( ID, this );
    }


    public Dockable getDockable(ViewId viewId) {
        return dockable;
    }

    public void install() {
        DockingParam p = new DockingParam();
        p.setPosition( IdeConstants.EAST );
        DockStation.getDockStation().dock( dockable, p );
    }
}
