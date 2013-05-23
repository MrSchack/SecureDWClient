/* $Header: CreatePropertiesFileWorker.java 16-aug-2007.21:58:31 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor cleanup.
    bduff       04/09/07 - Initial revision
 */

package oracle.ide.extsamples.createdialog;

import java.io.IOException;

import java.net.URL;

import oracle.ide.model.NodeFactory;
import oracle.ide.model.TextNode;
import oracle.ide.util.SwingWorker;

import oracle.javatools.buffer.TextBuffer;
import oracle.javatools.dialogs.progress.IndeterminateProgressMonitor;

/**
 * Background worker task that creates a new properties file.
 */
// @Immutable
final class CreatePropertiesFileWorker extends SwingWorker {
    private final Runnable uiTasksWhenDone;
    private final URL fileURL;
    private final IndeterminateProgressMonitor monitor;

    /**
     * Creates a new worker that creates a properties file.
     *
     * @param monitor a progress monitor to notify about the progress of the
     *    operation.
     * @param fileURL the URL of the properties file to create.
     * @param uiTasksWhenDone a runnable. The run() method of the runnable
     *    will be invoked on the event dispatch thread after the operation
     *    completes. You should perform any final user interface tasks in this
     *    runnable.
     * @throws NullPointerException if any parameter is null.
     */
    CreatePropertiesFileWorker(IndeterminateProgressMonitor monitor,
                               URL fileURL, Runnable uiTasksWhenDone) {
        if (monitor == null || uiTasksWhenDone == null || fileURL == null)
            throw new NullPointerException();

        this.monitor = monitor;
        this.uiTasksWhenDone = uiTasksWhenDone;
        this.fileURL = fileURL;
    }

    /**
     * Performs work (on a background thread).
     */
    public Object construct() {
        monitor.start();
        try {
            final TextNode node = createNode();
            writeDefaultText(node);
            saveToDisk(node);

            return node;
        } finally {
            monitor.finish();
        }
    }

    private TextNode createNode() {
        return NodeFactory.findOrCreateOrFail(TextNode.class, fileURL);
    }

    private void saveToDisk(final TextNode node) {
        try {
            // Saving the node makes it show up in the navigator.
            node.save();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void writeDefaultText(final TextNode node) {
        TextBuffer tb = node.acquireTextBuffer();
        try {
            tb.writeLock();
            tb.append("# Generated by ESDK Sample CreateDialog\n".toCharArray());
        } finally {
            tb.writeUnlock();
        }
    }

    @Override
    public void finished() {
        uiTasksWhenDone.run();
    }
}
