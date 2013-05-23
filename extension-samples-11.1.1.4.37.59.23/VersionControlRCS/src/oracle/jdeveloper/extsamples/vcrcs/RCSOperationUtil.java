/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;

import oracle.ide.net.URLFileSystem;

public class RCSOperationUtil {
    public RCSOperationUtil() {
        super();
    }
    
    public static boolean doCheckOut( URL[] urls ){
        RCSShellRunner runner = new RCSShellRunner();
        
        Collection<String> cmds = new ArrayList<String>();
        cmds.add("co");
        cmds.add("-l");
        
        for ( URL url: urls ) {
            cmds.add(URLFileSystem.getPlatformPathName(url));
        }
        
        runner.setCmdList(cmds);

        try {
            runner.execAndWait();
        } catch (Exception e) {
            return false;
        }
        
        return true;
        
    }
    
}
