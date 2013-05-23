/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Version status resolver for RCS, given a url (file) determine if it us under
    RCS if it is obtain the RCS version status (checked in, out).

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import oracle.ide.net.URLFactory;
import oracle.ide.net.URLFileSystem;

import oracle.jdeveloper.vcs.generic.StatusResolver;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.spi.VCSStatus;


public class RCSStatusResolver implements StatusResolver
{
    public static final String STATUS_ID_NOT_UNDER_RCS = "NOT_UNDER_RCS";
    public static final String STATUS_ID_CHECKOUT = "CHECKOUT_RCS";
    public static final String STATUS_ID_CHECKIN = "CHECKIN_RCS";
    
    public RCSStatusResolver()
    {
        super();
    }
    
    /**
     * Populate a versioning status for each of the passed urls. If a URL is not
     * within the RCS directory tree then the versioning status is unrecognised.
     * @param vcsProfile - versioning profile
     * @param urls - list of urls
     * @param vcsStatus - list (returned) set of versioned statuses
     */
    public void populateStatuses(VCSProfile vcsProfile, URL[] urls,
                               VCSStatus[] vcsStatus)
    {
        // Reference the unrecognized status.
        VCSStatus unrecognized = vcsProfile.getStatusInstance(
          VCSProfile.STATUS_ID_UNRECOGNIZED);

        // Here you can either create your own status implementation 
        for ( int u = 0; u < urls.length; u++ ) {
            if (URLFileSystem.isDirectory(urls[u]) || !isFileInRCSTree( urls[u] )) {
                // Since RCS doesn't deal with directories, ignore them.
                vcsStatus[u] = unrecognized;
            } else if (!isFileInRCS( urls[u])) {
                // If the file is not in RCS, we guess it's unversioned.
                vcsStatus[u] = vcsProfile.getStatusInstance( RCSStatusResolver.STATUS_ID_NOT_UNDER_RCS ) ;
            } else if (!URLFileSystem.canWrite(urls[u]) || !URLFileSystem.exists(urls[u])) {
                // If the file is read-only or it doesn't exist then it must be
                // checked in currently.
                vcsStatus[u] = vcsProfile.getStatusInstance( RCSStatusResolver.STATUS_ID_CHECKIN );
            } else {
                // If all else fails (meaning it's in RCS, it's there and it's read-write
                // then it's checked-out
                vcsStatus[u] = vcsProfile.getStatusInstance( RCSStatusResolver.STATUS_ID_CHECKOUT );
            }
        }
    }

    private boolean isFileInRCSTree(URL url) {
        URL parent = URLFileSystem.getParent( url );
        if ( parent == null )
            return false;
        
        URL rcs = URLFactory.newDirURL( parent, "RCS" );
        if ( URLFileSystem.exists( rcs ) )
            return true;
        
        return isFileInRCSTree( parent );
    }

    private boolean isFileInRCS(URL url) {
        String candidateFile = URLFileSystem.getFileName( url ) + ",v";
        URL parent = URLFileSystem.getParent( url );
        URL candidateURL = URLFactory.newURL( parent, candidateFile );
        if ( URLFileSystem.exists( candidateURL ))
            return true;
        URL rcs = URLFactory.newDirURL( parent, "RCS" );
        candidateURL = URLFactory.newURL( rcs, candidateFile );
        
        return URLFileSystem.exists( candidateURL );
    }
}
