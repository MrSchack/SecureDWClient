/* $Header: FixMethodName.java 16-aug-2007.20:11:54 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/16/07 - Code cleanup
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.auditrefactor;

import java.util.List;

import oracle.javatools.parser.java.v2.model.SourceMethod;
import oracle.javatools.parser.java.v2.model.SourceVariable;

import oracle.jdeveloper.audit.java.JavaTransformAdapter;
import oracle.jdeveloper.audit.java.JavaTransformContext;
import oracle.jdeveloper.audit.service.Localizer;
import oracle.jdeveloper.audit.transform.Transform;
import oracle.jdeveloper.refactoring.RefactoringManager;


public final class FixMethodName extends Transform {
    public FixMethodName(String name, Localizer localizer) {
        super(new JavaTransformAdapter(), name, localizer);
    }

    public void apply(JavaTransformContext context, SourceMethod method) {
        String oldName = method.getName();
        String newName = MethodNames.PREFIX + oldName;

        String className = method.getOwningClass().getQualifiedName();

        RefactoringManager rm = RefactoringManager.getRefactoringManager();

        List parameters = method.getSourceParameters();
        String[] prmStr = new String[parameters.size()];
        int i = 0;
        for (Object obj : parameters) {
            SourceVariable sv = (SourceVariable)obj;

            String val =
                sv.getSourceType().getResolvedType().getQualifiedName();

            prmStr[i++] = val;
        }
        rm.renameMethod(context.getIdeContext(), className, oldName, newName,
                        prmStr);
    }
}
