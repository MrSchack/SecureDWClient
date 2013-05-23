/* $Header: MethodNames.java 16-aug-2007.20:11:54 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/16/07 - Code cleanup.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.auditrefactor;

import oracle.javatools.parser.java.v2.model.SourceMethod;

import oracle.jdeveloper.audit.analyzer.Analyzer;
import oracle.jdeveloper.audit.analyzer.AuditContext;
import oracle.jdeveloper.audit.analyzer.Category;
import oracle.jdeveloper.audit.analyzer.Rule;
import oracle.jdeveloper.audit.analyzer.Severity;
import oracle.jdeveloper.audit.analyzer.ViolationReport;
import oracle.jdeveloper.audit.service.Localizer;


/**
 * Simple example of an audit analyzer which checks to see whether public
 * static final methods have a prefix "_pfs_" and raises a violation if not.
 */
public class MethodNames extends Analyzer {
    static final String PREFIX = "_pfs_";

    // Refers to custom.rules.audit.properties
    private static final Localizer LOCALIZER =
        Localizer.instance("oracle.jdeveloper.extsamples.auditrefactor.audit");
    private final Category SAMPLE_CATEGORY =
        new Category("sample-category", LOCALIZER);

    private static final String FIX_METHOD_NAME = "fix-method-name";

    private final FixMethodName fixMethodName =
        new FixMethodName(FIX_METHOD_NAME, LOCALIZER);

    private final Rule NAME_VERIFICATION =
        new Rule("name-verification", SAMPLE_CATEGORY, Severity.WARNING,
                 LOCALIZER, fixMethodName);

    {
        // Do this to make the rule enabled by default.
        NAME_VERIFICATION.setEnabled(true);
    }

    public Rule[] getRules() {
        return new Rule[] { NAME_VERIFICATION };
    }

    public void enter(AuditContext ctx, SourceMethod method) {
        if (!NAME_VERIFICATION.isEnabled()) return;
        if (!(method.isPublic() && method.isStatic() && method.isFinal())) return;

        String name = method.getName();
        if (!name.startsWith(PREFIX)) {
            ViolationReport vr = ctx.report(NAME_VERIFICATION);
            vr.addParameter("name", name);
        }
    }
}
