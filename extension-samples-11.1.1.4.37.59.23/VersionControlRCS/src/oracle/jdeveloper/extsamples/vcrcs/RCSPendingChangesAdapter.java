/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    The adapter that links the rcs extension to the pending changes window. For
    RCS the pending changes window has two tabs candidates and out going 
    changes.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import oracle.jdeveloper.vcs.changelist.ChangeList;
import oracle.jdeveloper.vcs.generic.PendingChangesAdapter;
import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.spi.VCSPropertyMap;


public class RCSPendingChangesAdapter extends PendingChangesAdapter {
    
    private static final String CL_ID_OUTGOING = 
      "oracle.jdeveloper.extsamples.vcrcs.OUTGOING";
    
    private static final String CL_ID_CANDIDATES = 
    "oracle.jdeveloper.extsamples.vcrcs.CANDIDATES";

    private static final String PROP_ID_CHANGE_LIST_STATUS = "changeList";

    public RCSPendingChangesAdapter() {
        super();
    }

    protected VCSProfile getProfile() {
        return VCSProfiles.getInstance().getProfile(RCSProfileListener.RCS_PROFILE_ID);
    }
    
    public VCSPropertyMap getPreferences()
    {
      return VCSProfiles.getInstance().getProfile(
        RCSProfileListener.RCS_PROFILE_ID).getData(
          RCSProfileListener.VTOOLS_SETTINGS_KEY);
    }

    public Object resolvePropertyValue(
      String changeListId,
      String propertyId,
      URL url)
    {
      if ( changeListId.equals( CL_ID_OUTGOING ) )
      {
        if ( propertyId.equals( PROP_ID_CHANGE_LIST_STATUS ) )
        {
          return "check out";
        }
      }
      else if ( changeListId.equals( CL_ID_CANDIDATES ) )
      {
        return "add";
      }
      
      return super.resolvePropertyValue(changeListId, propertyId, url);
    }
    


    /**
     * Display the action dialog ie. display the checkin dialog or just checkin
     * @param changeList
     * @param actionId
     * @return
     */
    public boolean isActionSilent(ChangeList changeList, String actionId)
    {
      VCSProfile profile = getProfile();
      
      if (profile == null)
      {
        return false;
      }
      
      VCSPropertyMap vtoolsPrefs = profile.getData(
        RCSProfileListener.VTOOLS_SETTINGS_KEY);
      
      Integer policy = (Integer)vtoolsPrefs.getMap().get(
        RCSProfileListener.PENDING_CHANGES_DIALOG_KEY);
      
      switch (policy.intValue())
      {
        case 0:
          return false;
        case 1:
          return changeList.isOptionsVisible();
        case 2:
          return true;
        default :
          throw new IllegalStateException();
      }
    }

}
