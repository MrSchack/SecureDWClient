package dke.extension.gui.dockable;

import dke.extension.logging.MyLogger;

import oracle.ide.AddinManager;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.Dockable;
import oracle.ide.extension.RegisteredByExtension;

/**
 * Command handler for dke.extension.MainDockable
 */
@RegisteredByExtension("dke.extension")

public class ViewDockableCommand extends Command {
    public ViewDockableCommand() {
        super(actionId());
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("dke.extension.MainDockable");
        if (cmdId == null)
            throw new IllegalStateException("Action dke.extension.MainDockable not found.");
        return cmdId;
    }

    public int doit() throws Exception {
        final MainDockableAddin addin =
            AddinManager.getAddinManager().getAddin(MainDockableAddin.class);

        final MainDockableFactory myDockableFactory = addin.getFactory();
        final Dockable dockable = myDockableFactory.getMainDockable();
        final DockStation dockStation = DockStation.getDockStation();
        dockStation.setDockableVisible(dockable, true);
        return OK;
    }
}
