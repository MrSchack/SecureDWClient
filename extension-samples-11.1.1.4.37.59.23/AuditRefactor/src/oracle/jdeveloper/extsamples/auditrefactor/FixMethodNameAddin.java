/* $Header: FixMethodNameAddin.java 16-aug-2007.20:11:54 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/16/07 - Clean up code (rename, make package private and
                           final)
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.auditrefactor;

import oracle.jdeveloper.audit.AbstractAuditAddin;

final class FixMethodNameAddin extends AbstractAuditAddin {
    private static final Class[] ANALYZERS = new Class[] { MethodNames.class };

    public Class[] getAnalyzers() {
        return ANALYZERS;
    }
}
