/* $Header: ClassGeneratorController.java 16-aug-2007.20:55:15 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Code cleanup. Reformat to JLS.
    bduff       03/28/07 - Fix bug 5957716 - deprecation warning.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classgenerator;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.model.Element;
import oracle.ide.model.Project;


/**
 * Controller for the class generator sample.
 */
final class ClassGeneratorController implements Controller {

    /**
     * Determines whether the action is enabled.
     * @param context
     * @return
     */
    static boolean isActionEnabled(Context context) {
        // Only enable the action when the element in context is a project.
        Element element = (context != null ? context.getElement() : null);
        return ((element != null) && (element instanceof Project));
    }

    public boolean handleEvent(IdeAction action, Context context) {
        return false;
    }


    /*
    * This method updates the enabled status of the specified action within the
    * specified context.
    *
    * An IdeAction is used when adding a menu and/or toobar button.
    * An action object is associated with a specific command.
    */

    public boolean update(IdeAction ideAction, Context context) {
        ideAction.setEnabled(isActionEnabled(context));
        return true;
    }
}
