/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Enable a user to select and compare two files     

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */


package oracle.jdeveloper.extsamples.vcrcs.op;

import oracle.ide.Ide;

import oracle.jdeveloper.vcs.versionhistory.VersionHistoryCommand;

public class CompareOther extends VersionHistoryCommand {
    
    public static final String COMMAND_ID = "oracle.jdeveloper.rcs.compare-other";

    public CompareOther() {
        super( Ide.findCmdID( COMMAND_ID ));
    }
}
