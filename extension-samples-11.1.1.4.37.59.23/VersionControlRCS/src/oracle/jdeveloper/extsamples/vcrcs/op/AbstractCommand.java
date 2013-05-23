/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Base class for command operation classes

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */


package oracle.jdeveloper.extsamples.vcrcs.op;

import java.awt.Component;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import oracle.ide.net.URLFileSystem;

import oracle.javatools.dialogs.progress.DeterminateProgressMonitor;

import oracle.jdeveloper.extsamples.vcrcs.RCSShellRunner;
import oracle.jdeveloper.vcs.generic.ActionCommand;
import oracle.jdeveloper.vcs.generic.CommandState;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.spi.VCSDirectoryInvokable;

public class AbstractCommand extends ActionCommand {
    public AbstractCommand(String string, int i, String string1) {
        super(string, i, string1);
    }

    public AbstractCommand(String string, int i) {
        super(string, i);
    }

    public AbstractCommand(String string) {
        super(string);
    }

    public AbstractCommand(int i, int i1, String string) {
        super(i, i1, string);
    }

    public AbstractCommand(int i, int i1) {
        super(i, i1);
    }

    public AbstractCommand(int i) {
        super(i);
    }

    protected boolean invokeCommandImpl(VCSProfile profile,
                                        CommandState commandState,
                                        Component parentUi,
                                        Map options) throws Exception {
        VCSDirectoryInvokable invokable =
            createDirectoryInvokable(profile, commandState, options);
        invokable.setProgressMonitor(createProgressMonitor(parentUi));
        return invokable.runInvokable();
    }

    protected boolean invokeCommandSilentlyImpl(VCSProfile profile,
                                                CommandState commandState,
                                                Map options) throws Exception {
        VCSDirectoryInvokable invokable =
            createDirectoryInvokable(profile, commandState, options);
        return invokable.runInvokable();
    }

    protected VCSDirectoryInvokable createDirectoryInvokable(VCSProfile profile,
                                                             CommandState commandState,
                                                             Map options) {
        return null;
    }

    protected DeterminateProgressMonitor createProgressMonitor(Component parent) {
        return null;
    }

    /**
     * Check out the url
     * @param runner - RCS Shell Runner to use
     * @param url - file url to check out
     * @param lock - should the file be locked when it is checked out
     * @return true: successfully checked in; false: failed to check in
     * @throws Exception
     */
    protected boolean checkOut(RCSShellRunner runner, URL url,
                               boolean lock) throws Exception {
        Collection<String> cmds = new ArrayList<String>();
        cmds.add("co");
        if (lock) {
            cmds.add("-l");
        }
        cmds.add(URLFileSystem.getPlatformPathName(url));

        runner.setCmdList(cmds);
        runner.execAndWait();

        return true;
    }

    /**
     * Check in the url
     * @param runner - RCS Shell Runner to use
     * @param url - file url to check in
     * @param desc - file description ( -t )
     * @return true: successfully checked out; false: failed to check out
     * @throws Exception
     */
    protected boolean checkIn(RCSShellRunner runner, URL url,
                              String comment) throws Exception {

        // create the "RCS" directory if it does not exist
        Collection<String> cmds = new ArrayList<String>();
        cmds.add("ci");
        if (comment != null && !comment.isEmpty()) {
            cmds.add("\"-m" + comment + "\"");
        }
        else {
            // require a comment
            cmds.add("\"-m JDeveloper \"");
        }
        cmds.add(URLFileSystem.getPlatformPathName(url));

        runner.setCmdList(cmds);

        runner.execAndWait();

        return true;
    }

    /**
     * Check in the url
     * @param runner - RCS Shell Runner to use
     * @param url - file url to check in
     * @param desc - file description ( -t )
     * @return true: successfully checked out; false: failed to check out
     * @throws Exception
     */
    protected boolean checkIn(RCSShellRunner runner, String desc,
                              URL url) throws Exception {

        // create the "RCS" directory if it does not exist
        Collection<String> cmds = new ArrayList<String>();
        cmds.add("ci");
        cmds.add("\\-t-" + desc + "\\");
        cmds.add(URLFileSystem.getPlatformPathName(url));

        runner.setCmdList(cmds);

        runner.execAndWait();

        return true;
    }
}
