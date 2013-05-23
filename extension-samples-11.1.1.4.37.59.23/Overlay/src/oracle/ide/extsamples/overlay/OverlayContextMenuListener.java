/* $Header: OverlayContextMenuListener.java 16-aug-2007.22:16:06 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.overlay;

import javax.swing.JMenuItem;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;
import static oracle.ide.extsamples.overlay.OverlayController.MENU_GROUP;
import static oracle.ide.extsamples.overlay.OverlayController.REMOVE_OVERLAYS;
import static oracle.ide.extsamples.overlay.OverlayController.SHOW_COMBO_OVERLAY;
import static oracle.ide.extsamples.overlay.OverlayController.SHOW_ICON_OVERLAY;
import static oracle.ide.extsamples.overlay.OverlayController.SHOW_LABEL_OVERLAY;
import static oracle.ide.extsamples.overlay.OverlayController.SHOW_TOOLTIP_OVERLAY;


public final class OverlayContextMenuListener implements ContextMenuListener {
    private JMenuItem createMenuItem(ContextMenu menu, int cmdId) {
        return menu.createMenuItem(IdeAction.find(cmdId));
    }

    public void menuWillShow(ContextMenu menu) {
        menu.add(createMenuItem(menu, SHOW_ICON_OVERLAY), MENU_GROUP);
        menu.add(createMenuItem(menu, SHOW_LABEL_OVERLAY), MENU_GROUP);
        menu.add(createMenuItem(menu, SHOW_TOOLTIP_OVERLAY), MENU_GROUP);
        menu.add(createMenuItem(menu, SHOW_COMBO_OVERLAY), MENU_GROUP);
        menu.add(createMenuItem(menu, REMOVE_OVERLAYS), MENU_GROUP);

    }

    public void menuWillHide(ContextMenu menu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}
