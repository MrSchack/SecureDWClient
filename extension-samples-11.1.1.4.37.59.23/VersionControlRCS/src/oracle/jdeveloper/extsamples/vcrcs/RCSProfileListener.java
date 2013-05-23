/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Main class that recieves notifcation when the extension is initialized /
    activated /deactivated  

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.util.logging.Logger;

import oracle.jdeveloper.vcs.generic.AbstractProfileListener;
import oracle.jdeveloper.vcs.generic.VCSProfile;

public class RCSProfileListener extends AbstractProfileListener {
    public static final String RCS_PROFILE_ID =
         "oracle.jdeveloper.extsamples.vcsrcs";
    public static final String GENERAL_ENVIRONMENT_SETTINGS_KEY = VCSProfile.DATA_KEY_COMMON_SETTINGS;
    public static final String VTOOLS_SETTINGS_KEY = "VTOOLS_SETTINGS";
    public static final String PENDING_CHANGES_DIALOG_KEY =
      "pendingChangesCheckinDialog";

    public static final String RCS_CHECKOUT_FILTER = "filters.status.checkout";
    
    public RCSProfileListener() {
        super();
    }

    public static Logger getQualifiedLogger(Class c) {
        return Logger.getLogger(RCS_PROFILE_ID + "." + c.getName());
    }

    public static String getQualifiedKey(String key) {
        return RCS_PROFILE_ID + "." + key;
    }

    public void profileInitialized(VCSProfile profile) {
        // NO-OP
    }

    public boolean canActivate(VCSProfile profile) {
        return true;
    }

    public void profileActivated(VCSProfile profile) {
        // NO-OP
    }

    public void profileDestroyed(VCSProfile profile) {
        // NO-OP
    }

}
