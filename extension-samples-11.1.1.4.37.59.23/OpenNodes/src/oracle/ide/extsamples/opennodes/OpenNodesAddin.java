/* $Header: Harness.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.Menubar;
import oracle.ide.docking.DockableFactory;
import oracle.ide.extension.RegisteredByExtension;

/**
 * TODO Provide javadoc comment for OpenNodesAddin.
 */
@RegisteredByExtension("oracle.ide.extsamples.opennodes")
final class OpenNodesAddin implements Addin {
    private OpenNodesDockableFactory factory;
    public void initialize() {
        factory = new OpenNodesDockableFactory();
        installViewMenu();
        
    }
    
    private void installViewMenu() {
        Ide.getMenubar().add( 
            Ide.getMenubar().createMenuItem( IdeAction.find( ViewCommand.actionId() )),
            Menubar.getJMenu( IdeMainWindow.MENU_VIEW )
        );
    }

    DockableFactory getFactory() {
        return factory;
    }

}
