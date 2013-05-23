/* $Header: WidgetsHook.java 16-aug-2007.22:10:50 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.samples.customhook;

import javax.ide.extension.ElementName;

import oracle.ide.extension.HashStructureHook;

/**
 * A custom hook using HashStructureHook.
 *
 * @author Brian.Duff@oracle.com
 */
public final class WidgetsHook extends HashStructureHook {
    /**
     * The element name that identifies this hook. This can be used to
     * retrieve the single instance of this hook from
     * ExtensionRegistry.getHook().
     */
    public static final ElementName NAME =
        new ElementName("http://xmlns.oracle.com/ide/samples/customhooks",
                        "widgets");

    /**
     * Gets the widgets data model.
     *
     * @return the widgets data model.
     */
    public Widgets getWidgets() {
        return Widgets.getInstance(getHashStructure());
    }
}
