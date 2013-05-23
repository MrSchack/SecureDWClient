/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Make the file writeable by checking it out of rcs. Used by other 
    components of JDeveloper eg refactoring.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */


package oracle.jdeveloper.extsamples.vcrcs.op;

import java.net.URL;

import oracle.ide.controller.Command;

import oracle.jdeveloper.vcs.spi.VCSCheckOutNodeCmd;


public class RCSCheckoutNode extends VCSCheckOutNodeCmd {
    public RCSCheckoutNode() {
        super(Command.NORMAL, "Check out from RCS");
    }

    protected boolean canCheckOutUI(URL url) {
        // Override this if you want to have a specific message for when 
        // automatic check out has been turned off by the user. 
        return super.canCheckOutUI( url );
    }

}
