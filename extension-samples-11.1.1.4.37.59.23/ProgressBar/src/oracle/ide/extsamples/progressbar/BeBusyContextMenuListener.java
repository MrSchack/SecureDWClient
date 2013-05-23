package oracle.ide.extsamples.progressbar;

import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

public class BeBusyContextMenuListener implements ContextMenuListener {
    public BeBusyContextMenuListener() {
    }

    public void menuWillShow(ContextMenu contextMenu) {
        contextMenu.add( contextMenu.createMenuItem( 
            IdeAction.find( BeBusyCommand.actionId() )                     
        ));
    }

    public void menuWillHide(ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}
