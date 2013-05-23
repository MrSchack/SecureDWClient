/* $Header: ClassGenerator.java 16-aug-2007.21:03:49 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat with JCS. Code cleanup.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classgenerator;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuManager;
import oracle.ide.controller.Menubar;


/**
 * Thanks to Jose Eneas da Silva Maria for updating this sample for 10.1.3.
 */
public final class ClassGenerator implements Addin {
    public void initialize() {
        Menubar menus = Ide.getMenubar();
        // Add a menu item to the main View menu.
        menus.add(menus.createMenuItem(IdeAction.find(ClassGeneratorCommand.actionId())),
            MenuManager.getJMenu(IdeMainWindow.MENU_VIEW));
    }

}
