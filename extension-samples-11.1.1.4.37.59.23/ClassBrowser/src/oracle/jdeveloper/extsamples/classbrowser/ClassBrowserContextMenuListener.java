/* $Header: ClassBrowserContextMenuListener.java 21-nov-2007.19:08:43 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       11/21/07 - Bug 6624651 - fix compilation warning.
    bduff       08/16/07 - Initial revision
 */
package oracle.jdeveloper.extsamples.classbrowser;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

public class ClassBrowserContextMenuListener implements ContextMenuListener {

    public void menuWillShow(ContextMenu contextMenu) {
        contextMenu.add(contextMenu.createMenuItem(IdeAction.find(ClassBrowserCommand.actionId())));
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}
