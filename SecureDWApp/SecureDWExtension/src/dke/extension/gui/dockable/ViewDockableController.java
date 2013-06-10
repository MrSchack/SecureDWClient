package dke.extension.gui.dockable;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;

/**
 * Controller for action dke.extension.MainDockable
 */
@RegisteredByExtension("dke.extension")
public class ViewDockableController implements Controller {
    
    public boolean handleEvent(IdeAction ideAction, Context context) {
        return false;
    }

    public boolean update(IdeAction ideAction, Context context) {
        return true;
    }
}
