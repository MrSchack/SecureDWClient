/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Make a file writable by checking it out of rcs. Used by other components of
    JDeveloper, eg code editor 

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */


package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import java.util.logging.Logger;

import oracle.jdeveloper.vcs.generic.CheckOutProvider;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.spi.VCSDirectoryInvokable;
import oracle.jdeveloper.vcs.spi.VCSDirectoryInvokableState;
import oracle.jdeveloper.vcs.spi.VCSProgress;


public class RCSCheckOutProvider extends CheckOutProvider {
    private static final Logger sLogger =
        RCSProfileListener.getQualifiedLogger(RCSFileProtocolHelper.class);

    public RCSCheckOutProvider() {
        super();
    }

    /**
     * Called when a file is to be made writable for example when a user starts
     * typeing in the code editor. Ie it has to checked out of
     * the version control system (RCS)
     * @param url - urls of the file to check out
     * @return true on success
     * @throws Exception
     */
    public boolean checkOut(URL[] url,
                            final VCSProgress progress) throws Exception {
        VCSProfile profile =
            VCSProfiles.getInstance().getProfile(RCSProfileListener.RCS_PROFILE_ID);

        VCSDirectoryInvokableState state = new VCSDirectoryInvokableState(url);

        VCSDirectoryInvokable invokable =
            new VCSDirectoryInvokable(state, 10) {
            protected final boolean doInvocation(URL parent,
                                                 URL[] invokeUrls) throws Exception {
                if (RCSOperationUtil.doCheckOut(invokeUrls)) {
                    progress.incrementProgress(invokeUrls.length);
                    return true;
                }

                return false;
            }
        };

        try {
            return invokable.runInvokable();
        } catch (Exception e) {
            sLogger.warning(e.getMessage());
            return false;
        } finally {
            profile.getStatusCache().clear(state.getProcessedURLs().toArray(new URL[0]));
        }
    }
}
