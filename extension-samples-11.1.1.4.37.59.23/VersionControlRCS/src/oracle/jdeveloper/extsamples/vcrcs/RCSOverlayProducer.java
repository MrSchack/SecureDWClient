/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Produce icon overlays for the application navigator, icons show rcs version
    state eg check out, checked in.
    Listen to the JDeveloper preference changes so that the producer acts on
    the user turning on/off the icon overlays.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import oracle.ide.explorer.IconOverlay;

import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.spi.VCSOverlayItemProducer;
import oracle.jdeveloper.vcs.spi.VCSPropertyKey;
import oracle.jdeveloper.vcs.spi.VCSStatus;
import oracle.jdeveloper.vcs.spi.VCSStatusCache;

public class RCSOverlayProducer extends VCSOverlayItemProducer 
                                implements ChangeListener{
    private VCSProfile _profile;
    private Map _properties = new HashMap(10);
    
    public RCSOverlayProducer(VCSStatusCache vcsStatusCache) {
        super(vcsStatusCache);
        
    _profile = VCSProfiles.getInstance().getProfile( RCSProfileListener.RCS_PROFILE_ID );

    }

    public void stateChanged(ChangeEvent e) {
        // listener for preference change. User may have turned off/on 
        // label decoration and/or icon overlays
        synchronized( _properties )
        {
          _properties.putAll( 
            _profile.getData( RCSProfileListener.GENERAL_ENVIRONMENT_SETTINGS_KEY ).getMap()
          );
        }
   
    }
    
    protected IconOverlay produceOverlay(URL url, VCSStatus status)
      throws Exception {
        
        String revision = null;
        
        if ( getLabelDecoration() )
            revision = RCSRevisionCache.getInstance().getRevision( url );
        
        if ( revision == null ){
            if ( getOverlay() ) {
                   return super.produceOverlay( url, status );
            }
            else 
                return null;
        }
        
        return new IconOverlay(
          ( getOverlay() ) ? status.getOverlay().getIcon() : null,
          revision,
          status.getOverlay().getToolTipText()
        );
    }
    
    private boolean getLabelDecoration()
    {
      Boolean label;
      
      synchronized( _properties )
      {
        label = (Boolean)_properties.get( VCSPropertyKey.KEY_USE_LABEL_DECORATIONS );
      }
      
      if ( label == null )
        return true;
      
      return label.booleanValue();
    }
    
    private boolean getOverlay()
    {
      Boolean overlay;
      
      synchronized( _properties )
      {   
        overlay = (Boolean)_properties.get( VCSPropertyKey.KEY_USE_OVERLAYS );    
      }
      
      if ( overlay == null )
        return true;
      
      return overlay.booleanValue();
    }

}
