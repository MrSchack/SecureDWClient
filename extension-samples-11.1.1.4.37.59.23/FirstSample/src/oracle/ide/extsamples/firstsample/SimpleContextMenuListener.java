/* $Header: SimpleContextMenuListener.java 16-aug-2007.22:06:19 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.firstsample;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

/**
 * ContextMenuListeners add items to context menus.
 */
public final class SimpleContextMenuListener implements ContextMenuListener {
    public void menuWillShow(ContextMenu contextMenu) {
        // First, retrieve our action using the ID we specified in the extension
        // manifest.
        IdeAction action = IdeAction.find(SimpleController.SAMPLE_CMD_ID);

        // Then add it to the context menu.
        contextMenu.add(contextMenu.createMenuItem(action));
    }

    public void menuWillHide(ContextMenu contextMenu) {
        // Most context menu listeners will do nothing in this method. In
        // particular, you should *not* remove menu items in this method.
    }

    public boolean handleDefaultAction(Context context) {
        // You can implement this method if you want to handle the default
        // action (usually double click) for some context.
        return false;
    }
}
