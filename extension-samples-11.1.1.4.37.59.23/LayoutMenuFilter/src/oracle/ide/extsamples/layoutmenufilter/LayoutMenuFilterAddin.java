/* $Header: LayoutMenuFilterAddin.java 20-aug-2007.15:05:01 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS. Minor code improvement.
    bduff       04/04/07 - Bug 5957716 - fix compilation warnings
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.layoutmenufilter;

import java.awt.Component;

import java.net.URL;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import oracle.ide.Addin;
import oracle.ide.ExtensionRegistry;
import oracle.ide.Ide;
import oracle.ide.IdeEvent;
import oracle.ide.IdeListener;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuFilter;
import oracle.ide.controller.MenuManager;
import oracle.ide.layout.Layout;
import oracle.ide.layout.Layouts;
import oracle.ide.net.URLFactory;


class LayoutMenuFilterAddin implements Addin {
    private Layout _layout;

    public Layout getLayout() {
        return _layout;
    }

    public void initialize() {
        // Attach an ide listener and do work after the main window has been
        // displayed and all layouts are loaded...
        Ide.addIdeListener(new IdeListener() {
            public void addinsLoaded(IdeEvent ideEvent) {
            }

            public void mainWindowOpened(IdeEvent ideEvent) {
                installLayout();
            }

            public void mainWindowClosing(IdeEvent ideEvent) {
            }
        });

        installLayout();
    }

    private void installLayout() {
        // Create our global custom layout
        final ExtensionRegistry er = ExtensionRegistry.getExtensionRegistry();


        final URL mySystemDir =
            er.getSystemDirectory("oracle.ide.extsamples.layoutmenufilter");
        final URL layoutFileUrl =
            URLFactory.newURL(mySystemDir, "ESDKCustom" + Layouts.LAYOUT_EXT);

        _layout = Layouts.getLayouts().newDesignLayout(layoutFileUrl);

        final MenuFilter filter = new MenuFilter() {
                public boolean accept(Component component) {
                    // Accept the file menu and our custom action only.
                    final JMenu fileMenu =
                        MenuManager.getJMenu(IdeMainWindow.MENU_FILE);
                    return (component == fileMenu ||
                            "ESDK.ToggleMinimalMode".equals(getMenuItemActionId(component)));
                }
            };

        _layout.setMenuFilter( filter );
    }
    
    private String getMenuItemActionId( Component c ) {
        if ( c instanceof JMenuItem ) {
            JMenuItem menuItem = (JMenuItem) c;
            if ( menuItem.getAction() instanceof IdeAction ) {
                IdeAction ideAction = (IdeAction) menuItem.getAction();
                return Ide.findCmdName( ideAction.getCommandId() );
            }
        }
        return "";
    }
            
}
