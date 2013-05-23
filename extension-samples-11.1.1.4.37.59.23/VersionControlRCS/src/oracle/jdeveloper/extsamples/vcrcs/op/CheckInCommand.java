/* $Header: jdev/src/esdk-samples/sample/VersionControlRCS/src/oracle/jdeveloper/extsamples/vcrcs/op/CheckInCommand.java /main/4 2009/01/22 10:04:03 dedwards Exp $ */

/* Copyright (c) 2007, 2009, Oracle and/or its affiliates.
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    dedwards    01/20/09 - Compare previous
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.vcrcs.op;


import java.awt.Component;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import oracle.ide.model.Locatable;

import oracle.javatools.dialogs.progress.DeterminateProgressMonitor;

import oracle.jdeveloper.extsamples.vcrcs.RCSRevisionCache;
import oracle.jdeveloper.extsamples.vcrcs.RCSShellRunner;
import oracle.jdeveloper.vcs.generic.CommandState;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.spi.VCSCommentsCustomizer;
import oracle.jdeveloper.vcs.spi.VCSDirectoryInvokable;
import oracle.jdeveloper.vcs.spi.VCSOptionsCustomizer;


/**
 * Implementation of a ActionCommand.  We extend ActionCommand so that
 * we behave like the other VCS systems and get this behavior for free.
 */
public class CheckInCommand extends AbstractCommand {

    private static final String OPERATION_ID = "oracle.jdeveloper.rcs.checkin";

    public CheckInCommand() {
        super(OPERATION_ID);
    }


    protected DeterminateProgressMonitor createProgressMonitor(Component parent) {
        return new DeterminateProgressMonitor(parent, "Check In",
                                              "Check in files to RCS", "", 0,
                                              -1);
    }

    protected VCSOptionsCustomizer createOptionsCustomizer() {
        VCSCommentsCustomizer customizer = new VCSCommentsCustomizer();
        customizer.setShowReuseCommentsOption(false);

        // force the defaults to be set
        customizer.setOptions(new HashMap<Object,Object>());

        return customizer;
    }

    /*
     * Perform the action.
     */

    protected VCSDirectoryInvokable createDirectoryInvokable(final VCSProfile profile,
                                                             final CommandState commandState,
                                                             final Map options) {
        final String comments =
            (String)options.get(VCSCommentsCustomizer.KEY_SETTING_COMMENTS);

        return new VCSDirectoryInvokable(commandState.getInvokableState(), 10,
                                         true) {
            protected boolean doInvocation(URL parent,
                                           URL[] children) throws Exception {

                RCSShellRunner runner = new RCSShellRunner();

                for (int i = 0; i < children.length; i++) {
                    final URL url = children[i];

                    if ( checkIn(runner, url, comments ) )
                        checkOut(runner, url, false);
                }

                return true;
            }
        };
    }
    
    /**
     * Called after the command has been run
     * @param profile
     * @param commandState
     */
    protected void postInvoke( VCSProfile profile, CommandState commandState) throws Exception { 
        super.postInvoke( profile, commandState);
        Locatable []locs = commandState.getLocatables();
        
        for ( Locatable loc: locs) {
            // clear the revision cache
            RCSRevisionCache.getInstance().clear( loc.getURL() );
        }
    }

}
