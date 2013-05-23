/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Compare a previous a file against its previous rcs revision.    

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */


package oracle.jdeveloper.extsamples.vcrcs.op;

import java.io.ByteArrayInputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;

import oracle.ide.model.Locatable;

import oracle.ide.net.URLFileSystem;

import oracle.ide.runner.RunProcess;

import oracle.javatools.compare.CompareContributor;

import oracle.jdeveloper.compare.BinaryCompareContributor;
import oracle.jdeveloper.compare.InputStreamTextContributor;
import oracle.jdeveloper.compare.PatchCompareDescriptor;
import oracle.jdeveloper.extsamples.vcrcs.RCSProfileListener;
import oracle.jdeveloper.extsamples.vcrcs.RCSRevisionCache;
import oracle.jdeveloper.extsamples.vcrcs.RCSShellRunner;
import oracle.jdeveloper.history.HistoryEntrySelector;
import oracle.jdeveloper.vcs.generic.VCSProfile;

import oracle.jdeveloper.vcs.migrate.VCSStreamMonitor;
import oracle.jdeveloper.vcs.spi.VCSStatus;

import oracle.jdeveloper.vcs.spi.VCSStatusFilter;

import oracle.jdeveloper.vcs.util.VCSFileSystemUtils;

import oracle.jdevimpl.vcs.generic.util.VCSCompareCommand;

public class ComparePrevious extends VCSCompareCommand {
    public static final String COMMAND_ID =
        "oracle.jdeveloper.rcs.compare-previous";

    public ComparePrevious() {
        super(COMMAND_ID);
    }

    /**
     * Get the previous revision number. Obtain the previous revision number by
     * running the rlog command and use the current revision number.
     *
     * @param vcsProfile
     * @param locatable
     * @return
     * @throws Exception
     */
    protected Object getContextRevision(VCSProfile vcsProfile,
                                        Locatable locatable) throws Exception {
        URL url = locatable.getURL();

        // get the version status - if checked out and modified then should
        // compare with the head revision. Here we just examine that it is checked
        // out.
        VCSStatus status = vcsProfile.getStatusCache().get(url);
        VCSStatusFilter filter =
            vcsProfile.getStatusFilter(RCSProfileListener.RCS_CHECKOUT_FILTER);

        if (filter.accept(status)) {
            return RCSRevisionCache.getInstance().getRevision(url);
        }

        return getPreviousRevision(RCSRevisionCache.getInstance().getRevision(url),
                                   url);
    }

    /**
     * Get the "content" of the revision as a compare contributor.
     *
     * @param vCSProfile
     * @param locatable
     * @param object
     * @return
     * @throws Exception
     */
    protected CompareContributor getHistoricalContributor(VCSProfile vCSProfile,
                                                          Locatable locatable,
                                                          Object object) throws Exception {
        URL url = locatable.getURL();
        String revision = (String)object;

        if (VCSFileSystemUtils.isContentTypeBinary(url)) {
            // binary file - require binary contributor
            return new BinaryCompareContributor(new ByteArrayInputStream(RCSRevisionCache.getInstance().getVersionContent(url,
                                                                                                                          revision)),
                                                URLFileSystem.getFileName(url),
                                                revision, revision);
        }

        InputStreamTextContributor compareContributor =
            new InputStreamTextContributor(new ByteArrayInputStream(RCSRevisionCache.getInstance().getVersionContent(url,
                                                                                                                     revision)),
                                           revision, revision,
                                           URLFileSystem.getSuffix(url));

        compareContributor.setPatchDescriptor(new PatchCompareDescriptor(URLFileSystem.getFileName(url)));

        compareContributor.getPatchDescriptor().setPatchParentURL(URLFileSystem.getParent(url));
        compareContributor.getPatchDescriptor().setRevision(revision);

        return compareContributor;
    }

    protected HistoryEntrySelector getHistoryEntrySelector(VCSProfile vCSProfile,
                                                           Locatable locatable,
                                                           Object revision) throws Exception {
        return new HistoryEntrySelector( (String)revision );
    }

    private Object getPreviousRevision(String rev, URL url) throws Exception {
        RCSShellRunner runner = new RCSShellRunner();
        Collection<String> cmds = new ArrayList<String>();
        PreviousStreamMonitor monitor = new PreviousStreamMonitor();
        monitor.setRevision(rev);

        cmds.add("rlog");
        cmds.add("-b");
        cmds.add(URLFileSystem.getPlatformPathName(url));

        runner.setCmdList(cmds);
        runner.setWriteLog(false);
        runner.addErrorMonitor(monitor);
        runner.addOutputMonitor(monitor);

        runner.execAndWait();

        return monitor.getPreviousRevision();
    }

    private class PreviousStreamMonitor extends VCSStreamMonitor {
        private String _revision;
        private String _previous;
        private boolean _revisionFound = false;

        private PreviousStreamMonitor() {
        }

        protected void streamLine(String line, RunProcess process) {
            if (line.startsWith("revision")) {
                String[] prts = line.split(" ");
                if (prts[1].equals(_revision)) {
                    _revisionFound = true;
                } else if (_revisionFound && _previous == null) {
                    _previous = prts[1].trim();
                }
            }
        }

        private void setRevision(String rev) {
            _revision = rev;
        }

        private Object getPreviousRevision() {
            return _previous;
        }
    }
}
