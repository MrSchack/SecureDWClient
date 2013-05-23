/* $Header: GoogleThisContextMenuListener.java 27-feb-2007.22:37:33 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Initial revision
 */
package oracle.ide.extsamples.codeinteraction;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

final class GoogleThisContextMenuListener implements ContextMenuListener {
    public void menuWillShow(ContextMenu contextMenu) {
        contextMenu.add( contextMenu.createMenuItem( 
            IdeAction.find( GoogleThisCommand.actionId() )
        ));
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}
