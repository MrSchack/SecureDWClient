package dke.extension.logging;

import oracle.ide.log.LogManager;

import oracle.javatools.util.ClosureException;
import oracle.javatools.util.SwingClosure;


public class MyLogger {
    public MyLogger() {
        super();
    }

    /**
     * Log a message safely on the AWT event thread. We must dispach calls that
     * affect UI on the right thread.
     *
     * @param message
     */
    public static void logMessage(final String message) {
        final String msg = "SecureDW> " + message + "\n";
        try {
            new SwingClosure() {
                protected void runImpl() {
                    LogManager.getLogManager().showLog();
                    LogManager.getLogManager().getMsgPage().log(msg);
                }
            }.run();
        } catch (ClosureException ce) {
            ce.printStackTrace();
        }
    }
}
