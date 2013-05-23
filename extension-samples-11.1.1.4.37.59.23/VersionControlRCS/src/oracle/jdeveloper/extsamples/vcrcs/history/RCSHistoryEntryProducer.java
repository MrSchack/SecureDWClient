/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    RCS History entry producer, integrates RCS to the version history viewer.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */

package oracle.jdeveloper.extsamples.vcrcs.history;

import java.net.URL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.ide.net.URLFileSystem;
import oracle.ide.runner.RunProcess;

import oracle.javatools.history.HistoryEntry;

import oracle.jdeveloper.extsamples.vcrcs.RCSProfileListener;
import oracle.jdeveloper.extsamples.vcrcs.RCSShellRunner;
import oracle.jdeveloper.vcs.generic.HistoryEntryProducer;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.migrate.VCSStreamMonitor;


public class RCSHistoryEntryProducer implements HistoryEntryProducer{
    private static final Logger sLogger =
        RCSProfileListener.getQualifiedLogger(RCSHistoryEntryProducer.class);
    private static String DATE = "date:";
    private static String AUTHOR = "author:";
    private static String LOCKED = "locked";
    
    private SimpleDateFormat _dateFormat;
    
    public RCSHistoryEntryProducer() {
        super();
    }

    public HistoryEntry[] produceEntries(URL url) {
        
        RCSShellRunner runner = new RCSShellRunner();
        RCSHistoryStreamMonitor monitor = new RCSHistoryStreamMonitor( url );
        
        Collection<String> cmds = new ArrayList<String>();
        
        cmds.add( "rlog" );
        cmds.add( URLFileSystem.getPlatformPathName( url ) );
    
        runner.setCmdList( cmds );
        runner.setWriteLog( false );
        runner.addOutputMonitor( monitor );
        runner.addErrorMonitor( monitor );
        
        try {
            runner.execAndWait();
        } catch (Exception e) {
            sLogger.warning( "Failed to get history on " + URLFileSystem.getPlatformPathName( url ) );
        }
        
        return monitor.getHistoryEntries();
    }

    private class RCSHistoryStreamMonitor extends VCSStreamMonitor{
        private Stack<RCSHistoryEntry> entries = new Stack<RCSHistoryEntry>();
        private URL _url;
        private VCSProfile profile;
        
        RCSHistoryStreamMonitor( URL url ) {
            _url = url;    
            profile = VCSProfiles.getInstance().getProfile( RCSProfileListener.RCS_PROFILE_ID );        
        }
        
        protected void streamLine( String line, RunProcess run) {
            
            if ( line.startsWith( "======" ) )
                return;
            
            // Process the output from rlog 
            // Each output line from the command "rlog" is processed here
            if ( line.startsWith( "----" ) ) {
                RCSHistoryEntry entry = new RCSHistoryEntry( _url, profile );
                entries.push( entry );
            }
            else if ( entries.size() > 0 ) {
                RCSHistoryEntry entry = entries.peek();
                
                if ( line.startsWith( "revision" ) ) {
                    String []parts = line.split( " " );
                    if ( parts.length == 2 || parts.length == 4 ) {
                        entry.setRevision( getRevision( parts[1] ) );
                    }
                }
                
                String [] details = line.split( ";" );
                if ( details.length >= 3 ){
                    // Found date: auther: state:
                    Date date = getDate( details[0].trim() );
                    String author = getAuthor( details[1].trim() );
                    
                    entry.setDate( date );
                    entry.setAuthor( author );
                }
                else {
                    // comments 
                    entry.setComment( line );
                }
            }
        }
        private HistoryEntry[] getHistoryEntries() {
            return entries.toArray( new HistoryEntry[0] );
        }

        private Date getDate( String str ) {
            if ( str.startsWith( DATE) ) {
                String date = str.substring( DATE.length() ).trim();
                try {
                    return getDateFormater().parse( date );
                } catch (ParseException e) {
                    sLogger.log( Level.WARNING, "Failed to parse " + date );
                }
            }
            return null;            
        }

        private String getAuthor( String str ) {
            if ( str.startsWith( AUTHOR ) )
                return str.substring( AUTHOR.length() ).trim();
            return null;
        }

        private String getRevision(String revision ) {
            if ( revision.contains( LOCKED ) ) {
                return revision.substring( revision.length() - LOCKED.length() );
            }
            
            return revision;
        }
    }
    
    private DateFormat getDateFormater()
    {
      if ( _dateFormat == null )
      {
        _dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      }
      return _dateFormat;
    }
    
}
