/* $Header: MyDockableAddin.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Added copyright banner, renamed class, reformat.
 */
package oracle.ide.extsamples.dockablewindow;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.Menubar;

final class MyDockableAddin implements Addin {
    private MyDockableFactory dockableFactory;

    public void initialize() {
        dockableFactory = new MyDockableFactory();
        installViewMenu();
    }
    
    private void installViewMenu() {
        Ide.getMenubar().add( 
            Ide.getMenubar().createMenuItem( IdeAction.find( ViewDockableCommand.actionId() )),
            Menubar.getJMenu( IdeMainWindow.MENU_VIEW )
        );
    }

    MyDockableFactory getFactory() {
        return dockableFactory;
    }
}
