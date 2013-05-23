/* $Header: ViewCommand.java 21-nov-2007.19:08:11 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       11/21/07 - Bug 6624651 - fix compilation warning.
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import oracle.ide.AddinManager;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.Dockable;
import oracle.ide.docking.DockableFactory;
import oracle.ide.extension.RegisteredByExtension;

/**
 * Command handler for esdksample.openNodeTracker.
 */
@RegisteredByExtension("oracle.ide.extsamples.opennodes")
public final class ViewCommand extends Command {
    public ViewCommand() {
        super(actionId());
    }

    public int doit() {
        OpenNodesAddin addin = AddinManager.getAddinManager().getAddin( OpenNodesAddin.class );
        
        DockableFactory factory = addin.getFactory();
        Dockable dockable = factory.getDockable( OpenNodesDockable.VIEW_ID );
        DockStation.getDockStation().setDockableVisible( dockable, true );
        
        return OK;
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("esdksample.openNodeTracker");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.openNodeTracker not found.");
        return cmdId;
    }
}
