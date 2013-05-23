/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Filter to determine if a file is under RCS version control 

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import oracle.ide.net.URLFactory;
import oracle.ide.net.URLFileSystem;
import oracle.ide.net.URLFilter;

import oracle.jdevimpl.vcs.generic.util.AbstractIdentifiable;

public class RCSURLFilter extends AbstractIdentifiable implements URLFilter{
    public RCSURLFilter() {
        super();
    }

    public boolean accept(URL url) {
        URL RCSUrl;
        
        if ( URLFileSystem.isDirectoryPath( url ) ) {
            RCSUrl = URLFactory.newDirURL( url, "RCS" );
        } else {
            RCSUrl = URLFactory.newDirURL( URLFileSystem.getParent( url ), "RCS" );
        }
            
        boolean ans = URLFileSystem.exists( RCSUrl );
        
        return ans;
    }
}
