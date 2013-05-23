/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Class that runs the rcs commands (ci,co etc), handles the input and output
    from the command.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import oracle.ide.log.LogManager;
import oracle.ide.log.LogPrintWriter;
import oracle.ide.runner.RunProcessListener;

import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.migrate.VCSShellRunner;
import oracle.jdeveloper.vcs.spi.VCSException;
import oracle.jdeveloper.vcs.spi.VCSPropertyKey;
import oracle.jdeveloper.vcs.spi.VCSPropertyMap;


/**
 * RCS outputs on to the error stream not output stream.
 */
public class RCSShellRunner extends VCSShellRunner {

    private LogPrintWriter _logWriter;
    private boolean _write = true;    

    private final RunProcessListener _timeoutListener =
        new RunProcessListener() {

        /**
         * Called when a RCS command has finished. 
         */        
        public final void processFinished(int exitCode) {
        
            if (!writeMsgToLogConsole() )
                return;

            getPrintWriter().println("Command finished");
        }
    };

    public RCSShellRunner() {
        super();

        // set the default as no timeout
        setTimeout(-1);

        getSimpleProcess().setAddToTerminateMenu(false);
        getSimpleProcess().setShowStartStatus(false);

        // get the time out from the user preferences
        Integer value = getPreferenceTimeOut();

        if (value != null)
          setTimeout( (int)( value.intValue() * Math.pow( 10, 3 ) ) );

        // add the time out listener so that we get receive notification of when
        // a command finishes
        getSimpleProcess().setRunProcessListener( _timeoutListener );

    }

    public int execAndWait() throws Exception {
        preExecute();

        int stat = super.execAndWait();

        if ( isTimedOut() ) {
            long timeout = getTimeout();
            timeout = timeout / (long)Math.pow( 10, 3 );
            
            // the command timeout.
            // throw a VCSException so that it is displayed to the user
            throw new VCSException( 
                            "Timeout", 
                            "Command timed out after " + Long.toString(timeout ) + " seconds"
                            );
        }
        
        return stat;
    }

    public void exec() throws Exception {
        preExecute();

        super.exec();
    }

    public void setWriteLog( boolean write ) {
        _write = write;
    }
    
    private Boolean writeMsgToLogConsole() {
        
        if ( !_write )
            return false;
        
        VCSProfile profile =
            VCSProfiles.getInstance().getProfile(RCSProfileListener.RCS_PROFILE_ID);
        VCSPropertyMap map =
            profile.getData(RCSEnvironmentTraversable.DATA_KEY);

        if (map == null)
            return null;

        return (Boolean)map.getMap().get(VCSPropertyKey.KEY_AUTO_LOG_MESSAGES);
    }

    private void preExecute() {
        Boolean write = writeMsgToLogConsole();

        getSimpleProcess().setUseLogPage(write.booleanValue());
        if (write.booleanValue()) {
            getSimpleProcess().setLogPage(LogManager.getLogManager().getMsgPage());
        }
        getSimpleProcess().setAddToTerminateMenu(write.booleanValue());
    }

    private Integer getPreferenceTimeOut() {
        VCSProfile profile =
            VCSProfiles.getInstance().getProfile(RCSProfileListener.RCS_PROFILE_ID);
        VCSPropertyMap map =
            profile.getData(RCSEnvironmentTraversable.DATA_KEY);

        if (map == null)
            return null;

        return (Integer)map.getMap().get(VCSPropertyKey.KEY_OPERATION_TIMEOUT);
    }

    private LogPrintWriter getPrintWriter() {
        if (_logWriter == null) {
            if (LogManager.getLogManager() != null) {
                // maybe null if running unit tests
                _logWriter =
                        new LogPrintWriter(LogManager.getLogManager().getMsgPage());
            }
        }

        return _logWriter;
    }

}
