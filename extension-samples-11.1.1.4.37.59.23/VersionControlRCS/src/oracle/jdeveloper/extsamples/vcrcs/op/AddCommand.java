/* $Header: jdev/src/esdk-samples/sample/VersionControlRCS/src/oracle/jdeveloper/extsamples/vcrcs/op/AddCommand.java /main/4 2009/01/22 10:04:02 dedwards Exp $ */

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

import oracle.ide.net.URLFactory;
import oracle.ide.net.URLFileSystem;

import oracle.javatools.dialogs.progress.DeterminateProgressMonitor;

import oracle.jdeveloper.extsamples.vcrcs.RCSShellRunner;
import oracle.jdeveloper.vcs.generic.CommandState;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.spi.VCSDirectoryInvokable;


/**
 * Implementation of a ActionCommand.  We extend ActionCommand so that
 * we behave like the other VCS systems and get this behavior for free.
 */
public class AddCommand extends AbstractCommand {

    private static final String OPERATION_ID = "oracle.jdeveloper.rcs.add";

    public AddCommand() {
        super(OPERATION_ID);
    }

    protected DeterminateProgressMonitor createProgressMonitor(Component parent) {
        return new DeterminateProgressMonitor(parent, "Add",
                                              "Add files to RCS ", "", 0, -1);
    }

    /*
     * Perform the rcs add action.
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
                    final URL url = children[i];

                    createRCSSubDirectory(url);

                    String desc = URLFileSystem.getFileName(url) + " added by JDeveloper";

                    if (checkIn(runner, desc, url)) {
                        checkOut(runner, url, true);
                    }
                }
                return true;
            }

        };
    }

    /**
     * Create an RCS subdirectory if it does not exist at the same
     * directory level as the file represented by <code>url</code>
     *
     * @param url The file location
     * @return true if the RCS directory exists.
     */
    private void createRCSSubDirectory(URL url) {
        URL parent = URLFileSystem.getParent(url);
        URL rcs = URLFactory.newDirURL(parent, "RCS");

        if (!URLFileSystem.exists(rcs)) {
            URLFileSystem.mkdir(rcs);
        }
    }


}
