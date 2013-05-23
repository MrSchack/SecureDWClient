/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    File handler to manage file renames, moves and make file writeable. 

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import java.util.logging.Logger;

import oracle.ide.net.ProtocolConstants;
import oracle.ide.net.URLFileSystem;

import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.spi.VCSExtension;
import oracle.jdeveloper.vcs.spi.VCSStatus;
import oracle.jdeveloper.vcs.spi.VCSURLFileSystemHelper;

import oracle.jdevimpl.vcs.generic.GenericClient;

/**
 * File Helper used by components like refactoring, to rename and move files
 */
public class RCSFileProtocolHelper extends VCSURLFileSystemHelper {

    private static final Logger sLogger =
        RCSProfileListener.getQualifiedLogger(RCSFileProtocolHelper.class);

    public RCSFileProtocolHelper() {

        super(URLFileSystem.findHelper(ProtocolConstants.FILE_PROTOCOL),
              (GenericClient)VCSProfiles.getInstance().getProfile(RCSProfileListener.RCS_PROFILE_ID));

    }

    public RCSFileProtocolHelper(oracle.ide.net.URLFileSystemHelper urlFileSystemHelper,
                                 VCSExtension vcsExtension) {
        super(urlFileSystemHelper, vcsExtension);
    }

    protected boolean setReadWrite(URL url) {
        // Grab hold of the rcs profile instance.
        VCSProfile profile =
            VCSProfiles.getInstance().getProfile(RCSProfileListener.RCS_PROFILE_ID);

        try {
            // Are we being asked to operate on a versioned object?
            VCSStatus status = profile.getStatusCache().get(url);
            if (!status.isVersioned()) {
                // Not a versioned object, so delegate to the file system impl.
                return super.setReadWrite(url);
            }

            // make the rcs controlled file writeable by checking it out.
            return RCSOperationUtil.doCheckOut( new URL[] {url} );
        } catch (Exception e) {
            sLogger.severe("unable to change read/write status for " +
                           URLFileSystem.getPlatformPathName(url) + ": " +
                           e.getMessage());
            return false;
        }        
    }
}
