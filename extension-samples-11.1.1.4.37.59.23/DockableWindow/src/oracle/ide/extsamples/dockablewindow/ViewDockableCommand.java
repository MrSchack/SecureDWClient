/* $Header: ViewDockableCommand.java 21-nov-2007.19:06:57 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       11/21/07 - Bug 6624651 - fix compilation warning.
    bduff       08/20/07 - Initial revision
 */

package oracle.ide.extsamples.dockablewindow;

import oracle.ide.AddinManager;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.Dockable;
import oracle.ide.extension.RegisteredByExtension;

/**
 * Command handler for esdksample.exampleDockable.
 */
@RegisteredByExtension("oracle.ide.extsamples.dockablewindow")
public final class ViewDockableCommand extends Command {
    public ViewDockableCommand() {
        super(actionId());
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("esdksample.exampleDockable");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.exampleDockable not found.");
        return cmdId;
    }

    public int doit() {
        final MyDockableAddin addin = AddinManager.getAddinManager().getAddin(
          MyDockableAddin.class );

        final MyDockableFactory myDockableFactory = addin.getFactory();
        final Dockable dockable = myDockableFactory.getMyDockable();
        final DockStation dockStation = DockStation.getDockStation();
        dockStation.setDockableVisible(dockable, true);
        return OK;
    }

}
