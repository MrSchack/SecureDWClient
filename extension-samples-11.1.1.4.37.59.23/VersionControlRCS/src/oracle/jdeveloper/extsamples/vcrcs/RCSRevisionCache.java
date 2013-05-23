/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    A cache holding the rcs revision numbers for the files that appear within 
    the application navigator.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import oracle.ide.net.URLFileSystem;
import oracle.ide.runner.RunProcess;

import oracle.jdeveloper.vcs.migrate.VCSStreamMonitor;


public class RCSRevisionCache {
    private static final Logger sLogger =
        RCSProfileListener.getQualifiedLogger(RCSRevisionCache.class);

    private static String HEAD = "head:";
    
    private static RCSRevisionCache _instance = new RCSRevisionCache();

    // use a string not a url for performance reasons
    private Map<String, String> _cache = new HashMap<String, String>();
    
    private RCSRevisionCache() {
        
    }
    
    public static RCSRevisionCache getInstance() {
        return _instance;
    }
    
    public String getRevision( URL url ) {
        if ( _cache.containsKey( url.getPath() ) )
            return _cache.get( url.getPath() );
        
        // get the revision - assume that we have the head revision
        String revision = getRevisionForURL( url );
        
        _cache.put( url.getPath(), revision );
            
        return revision;    
    }

    public void clear( URL url ) {
        _cache.remove( url.getPath() );
    }

    public byte[] getVersionContent( URL url, String revision ) throws Exception {
        RCSShellRunner runner = new RCSShellRunner();
        Collection<String> cmds = new ArrayList<String>();
        cmds.add( "co" );
        cmds.add( "-p" + revision );
        cmds.add( URLFileSystem.getPlatformPathName( url ) );
        
        runner.setCmdList( cmds );
        runner.setWriteLog( false );
        
        runner.execAndWait();
    
        return runner.getOutputBytes();
    }
    
    /**
     * Get the head revision number fo the url;
     * @param url
     * @return revision
     */
    private String getRevisionForURL(URL url) {
        
        final String revision = null;
        HistoryStreamMonitor monitor = new HistoryStreamMonitor();
        
        RCSShellRunner runner = new RCSShellRunner();
        Collection<String> cmds = new ArrayList<String>();
        cmds.add( "rlog" );
        cmds.add( "-h" );
        cmds.add( URLFileSystem.getPlatformPathName( url ) );
        
        runner.addOutputMonitor( monitor );
        runner.addErrorMonitor( monitor );
        runner.setCmdList( cmds );
        runner.setWriteLog( false );
        
        try {
            runner.execAndWait();
        } catch (Exception e) {
            sLogger.warning( "Failed to get head revision " + e.getMessage() );
            
        }
        return monitor.getRevision();
    }

    private class HistoryStreamMonitor extends VCSStreamMonitor {
        private String _revision;
        
        protected void streamLine(String line, RunProcess process) {
            if (line.startsWith(HEAD)) {
                _revision = line.substring(HEAD.length()).trim();
            }
        }
        
        String getRevision() {
            return _revision;
        }
        
    }
}
