/* $Header: MyDockableFactory.java 20-aug-2007.14:48:56 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS. Some code cleanup.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.dockablewindow;

import oracle.ide.IdeConstants;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.DockableFactory;
import oracle.ide.docking.Dockable;
import oracle.ide.docking.DockingParam;
import oracle.ide.layout.ViewId;

public final class MyDockableFactory implements DockableFactory {
    public static final String FAMILY = "MyDockable";

    private MyDockable myDockable;

    public MyDockableFactory() {
        final DockStation dockStation = DockStation.getDockStation();
        dockStation.registerDockableFactory(MyDockableFactory.FAMILY, this);
    }

    /* This method will only be called the first time this factory is encountered in a layout.
   */

    public void install() {
        final DockStation dockStation = DockStation.getDockStation();
        DockingParam dp = new DockingParam();
        dp.setPosition(IdeConstants.SOUTH);
        dockStation.dock(getMyDockable(), dp);
    }

    /* A factory can be responsible for multiple dockables.  For example, there is only one debugger factory to control
     * the debugger windows.
     * The view ID will be MyDockableFactory.FAMILY + "." + MyDockable.VIEW_ID
     */
    public Dockable getDockable(ViewId viewId) {
        if (MyDockable.VIEW_ID.equals(viewId)) return getMyDockable();
        return null;
    }

    public MyDockable getMyDockable() {
        if (myDockable == null) myDockable = new MyDockable();
    
        return myDockable;
    }
}
