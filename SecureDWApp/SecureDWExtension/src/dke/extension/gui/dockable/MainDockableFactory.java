package dke.extension.gui.dockable;

import oracle.ide.IdeConstants;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.Dockable;
import oracle.ide.docking.DockableFactory;
import oracle.ide.docking.DockingParam;
import oracle.ide.layout.ViewId;

public class MainDockableFactory implements DockableFactory {
    public static final String FAMILY = "MainDockable";

    private MainDockable mainDockable;

    public MainDockableFactory() {
        final DockStation dockStation = DockStation.getDockStation();
        dockStation.registerDockableFactory(MainDockableFactory.FAMILY, this);
    }

    /**
     * This method will only be called the first time this factory is encountered in a layout.
     */
    public void install() {
        final DockStation dockStation = DockStation.getDockStation();
        DockingParam dp = new DockingParam();
        dp.setPosition(IdeConstants.SOUTH);
        dockStation.dock(getMainDockable(), dp);
    }

    /**A factory can be responsible for multiple dockables.  For example, there is only one debugger factory to control
     * the debugger windows.
     * The view ID will be MainDockableFactory.FAMILY + "." + MainDockable.VIEW_ID
     * @param viewId
     * @return a new Dockable matching the view ID
     */
    public Dockable getDockable(ViewId viewId) {
        if (MainDockable.VIEW_ID.equals(viewId))
            return getMainDockable();
        return null;
    }

    public MainDockable getMainDockable() {
        if (mainDockable == null)
            mainDockable = new MainDockable();

        return mainDockable;
    }
}
