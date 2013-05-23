/* $Header: WidgetController.java 16-aug-2007.22:10:36 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.samples.customhook;

import javax.ide.extension.ExtensionRegistry;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.log.LogManager;

public class WidgetController implements Controller {
    public boolean handleEvent(IdeAction ideAction, Context context) {
        WidgetsHook hook =
            (WidgetsHook)ExtensionRegistry.getExtensionRegistry().getHook(WidgetsHook.NAME);
        for (Widget w : hook.getWidgets().getWidgets()) {
            LogManager.getLogManager().getMsgPage().log(w.toString() + "\n");
        }
        return true;
    }

    public boolean update(IdeAction ideAction, Context context) {
        return true;
    }
}
