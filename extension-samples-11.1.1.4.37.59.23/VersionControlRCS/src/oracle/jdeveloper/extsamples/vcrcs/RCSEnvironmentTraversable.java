/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Adds RCS preferences to the JDeveloper preference dialog 

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.spi.VCSPropertyCustomizer;
import oracle.jdeveloper.vcs.spi.VCSPropertyKey;

public class RCSEnvironmentTraversable extends VCSPropertyCustomizer {
    
    public static String DATA_KEY = "oracle.jdeveloper.vcs.data.COMMON_SETTINGS";

    private static final String[] COMMON_KEYS = new String[] {
      VCSPropertyKey.KEY_USE_OVERLAYS,
      VCSPropertyKey.KEY_USE_LABEL_DECORATIONS,
      VCSPropertyKey.KEY_AUTO_CHECKOUTS,
      VCSPropertyKey.KEY_AUTO_LOG_MESSAGES,
      VCSPropertyKey.KEY_OPERATION_TIMEOUT};

    public RCSEnvironmentTraversable()
    {
      super(COMMON_KEYS, null);
      setHelpID(null);
    }

    protected String getDataKey()
    {
      VCSProfile profile = VCSProfiles.getInstance().getProfile(
        RCSProfileListener.RCS_PROFILE_ID );
      if (profile == null)
      {
        throw new IllegalStateException();
      }
      
      return profile.getQualifiedDataKey( RCSProfileListener.GENERAL_ENVIRONMENT_SETTINGS_KEY );
    }}
