/* $Header: jdev/src/esdk-samples/sample/VersionControlRCS/src/oracle/jdeveloper/extsamples/vcrcs/op/CheckOutCommand.java /main/4 2009/01/22 10:04:04 dedwards Exp $ */

/* Copyright (c) 2007, 2009, Oracle and/or its affiliates.
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.vcrcs.op;


import java.awt.Component;

import java.net.URL;

import java.util.Map;

import oracle.javatools.dialogs.progress.DeterminateProgressMonitor;

import oracle.jdeveloper.extsamples.vcrcs.RCSShellRunner;
import oracle.jdeveloper.vcs.generic.CommandState;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.spi.VCSDirectoryInvokable;


/**
 * Implementation of a ActionCommand.  We extend ActionCommand so that
 * we behave like the other VCS systems and get this behavior for free.
 */
public class CheckOutCommand extends AbstractCommand {

    private static final String OPERATION_ID =
        "oracle.jdeveloper.rcs.checkout";

    public CheckOutCommand() {
        super(OPERATION_ID);
    }


    protected DeterminateProgressMonitor createProgressMonitor(Component parent) {
        return new DeterminateProgressMonitor(parent, "Check out",
                                              "Check out files from RCS ", "",
                                              0, -1);
    }

    /*
   * Perform the action in batches
   */

    protected VCSDirectoryInvokable createDirectoryInvokable(final VCSProfile profile,
                                                             final CommandState commandState,
                                                             final Map options) {
        return new VCSDirectoryInvokable(commandState.getInvokableState(), 10,
                                         true) {
            protected boolean doInvocation(URL parent,
                                           URL[] children) throws Exception {
                
                RCSShellRunner runner = new RCSShellRunner();

                for (int i = 0; i < children.length; i++) {
                    checkOut(runner, children[i], true);
                }

                return true;
            }
        };
    }
}
