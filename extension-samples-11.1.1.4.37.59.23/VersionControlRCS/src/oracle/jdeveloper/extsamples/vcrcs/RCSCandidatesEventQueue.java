/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Candidates event queue for the pending chanages window

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */

package oracle.jdeveloper.extsamples.vcrcs;

import java.util.Collection;

import oracle.jdeveloper.vcs.changelist.ChangeList;
import oracle.jdeveloper.vcs.changelist.ChangeListEventQueue;
import oracle.jdeveloper.vcs.spi.VCSHashURL;

public class RCSCandidatesEventQueue extends ChangeListEventQueue {
    public RCSCandidatesEventQueue(ChangeList changeList, int i) {
        super(changeList, i);
        
        // Exclude directories from the pending changes window - RCS does 
        // not version directories
        changeList.setExcludeDirectories(true);

    }

    public RCSCandidatesEventQueue(ChangeList changeList) {
        super(changeList);
        changeList.setExcludeDirectories(true);

    }
    
    protected void filterByStatus(Collection<VCSHashURL> urls)
      throws Exception
    {
        super.filterByStatus( urls );
        
        // Override if other filtering is required 
    }
}
