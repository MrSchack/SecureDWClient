/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Entry for a history revision.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs.history;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Date;

import oracle.jdeveloper.extsamples.vcrcs.RCSRevisionCache;
import oracle.jdeveloper.history.RevisionIdentifier;
import oracle.jdeveloper.vcs.generic.VCSHistoryEntry;
import oracle.jdeveloper.vcs.generic.VCSProfile;


public class RCSHistoryEntry extends VCSHistoryEntry {
    private static String HISTORY_PROPERTY_AUTHOR = "oracle.jdeveloper.extsamples.vcrcs.history.AUTHOR";
    
    public RCSHistoryEntry(URL url, VCSProfile vcsProfile) {
        super(url, vcsProfile);
    }

    protected InputStream getContent(URL url,
                                     RevisionIdentifier revisionIdentifier) throws Exception {        
        
        return new ByteArrayInputStream( 
            RCSRevisionCache.getInstance().getVersionContent( 
                url, 
                revisionIdentifier.getLabel()
            )
        );
    }

    /**
     * Set the revision number 
     * @param revision
     */
    void setRevision( String revision ) {
        setValue( VCSProfile.HISTORY_PROPERTY_REVISION, new RevisionIdentifier( null, revision ));
    }

    /**
     * Set the date that the revision was created
     * @param date
     */
    void setDate(Date date) {
        setValue( VCSProfile.HISTORY_PROPERTY_DATE, date );
    }

    /**
     * The author who created the revision
     * @param author
     */
    void setAuthor(String author) {
        setValue( HISTORY_PROPERTY_AUTHOR, author );
    }
    
    /**
     * Comment that was associated to the revision
     * @param comment
     */
    void setComment(String comment) {
        setValue( VCSProfile.HISTORY_PROPERTY_DESCRIPTION, comment );        
    }

}
