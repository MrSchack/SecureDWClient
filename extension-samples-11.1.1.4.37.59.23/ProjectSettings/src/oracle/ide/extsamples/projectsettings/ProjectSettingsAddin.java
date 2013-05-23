/* $Header: ProjectSettingsAddin.java 16-aug-2007.22:18:54 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       03/28/07 - Remove programmatic registration of project
                           properties panel and action (moved to extension.xml)
    bduff       02/27/07 - Added copyright banner
 */

package oracle.ide.extsamples.projectsettings;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuManager;

final class ProjectSettingsAddin implements Addin {
    public static final int CMDID_DUMP_MYJPR_PROPS =
        Ide.findOrCreateCmdID("Samples.CMDID_DUMP_MYJPR_PROPS");

    private final JMenuItem createDumpInfoMenuItem() {
        IdeAction action = IdeAction.find(CMDID_DUMP_MYJPR_PROPS);
        return Ide.getMenubar().createMenuItem(action);
    }

    public void initialize() {
        JMenu toolsMenu = MenuManager.getJMenu(IdeMainWindow.MENU_TOOLS);
        toolsMenu.add(createDumpInfoMenuItem());
    }
}


