/* $Header: UserInformationAddin.java 16-aug-2007.22:25:45 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor code cleanup.
    bduff       02/27/07 - Big rewrite for 11.1.1 ESDK
 */
package oracle.ide.extsamples.configpanel;

import oracle.javatools.data.StructureChangeEvent;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.config.Preferences;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuConstants;
import oracle.ide.controller.MenuManager;

import oracle.ide.log.LogManager;
import oracle.ide.log.LogPage;

import oracle.javatools.data.StructureChangeListener;

/**
 * This addin installs a menu item and demonstrates how to listen for
 * changes to a preferences object.
 */
final class UserInformationAddin implements Addin {
    // This action id (esdk.configpanel.writeToLog) matches the one defined in
    // extension.xml.
    static final int CMD_ID = Ide.findCmdID("esdk.configpanel.writeToLog");

    public void initialize() {
        installMenuItem();
        listenForPreferenceChanges();
    }

    private void installMenuItem() {
        IdeAction action = IdeAction.find(CMD_ID);
        Ide.getMenubar().add(Ide.getMenubar().createMenuItem(action),
                             MenuManager.getJMenu(IdeMainWindow.MENU_TOOLS),
                             MenuConstants.SECTION_TOOLS_ADDINS);
    }

    private void listenForPreferenceChanges() {
        // This is how to listen for changes to a preference model.
        UserInformation info =
            UserInformation.getInstance(Preferences.getPreferences());
        info.addStructureChangeListener(new StructureChangeListener() {
                    public void structureValuesChanged(StructureChangeEvent e) {
                        log("UserInformation preferences changed");
                    }
                });
    }

    static void log(String message) {
        LogPage msgPage = LogManager.getLogManager().getMsgPage();
        msgPage.log("(ESDK Sample - ConfigPanel) ");
        msgPage.log(message + "\n");
    }

}
