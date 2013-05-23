/* $Header: UserNameMacro.java 16-aug-2007.22:02:47 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/07/07 - Initial Revision
 */
package oracle.ide.extsamples.externaltoolmacros;

import oracle.ide.Context;
import oracle.ide.externaltools.macro.MacroExpander;

/**
 * An example macro for external tools that just expands to the current
 * user's user name.
 */
final class UserNameMacro extends MacroExpander {
    public String getMacro() {
        return "sample.user.name";
    }

    public String expand(Context context) {
        return System.getProperty("user.name");
    }

    public String getShortLabel() {
        return "User name (ESDK Sample)";
    }

    public String getLongLabel() {
        return "The current user's username (provided by the ExternalToolsMacro sample)";
    }

}
