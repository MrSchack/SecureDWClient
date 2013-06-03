package dke.extension.gui.dockable;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.Menubar;

public class MainDockableAddin implements Addin {
    private MainDockableFactory dockableFactory;

    public void initialize() {
        dockableFactory = new MainDockableFactory();
        installViewMenu();
    }

    private void installViewMenu() {
        Ide.getMenubar().add(Ide.getMenubar().createMenuItem(IdeAction.find(ViewDockableCommand.actionId())),
                             Menubar.getJMenu(IdeMainWindow.MENU_VIEW));
    }

    public MainDockableFactory getFactory() {
        return dockableFactory;
    }
}
