/* $Header: Widgets.java 16-aug-2007.22:10:47 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.samples.customhook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;

/**
 * A JavaBean representing widgets assembled from extension manifests.<p>
 *
 * This encapsulates knowledge of the internals of the hash structure and
 * exposes a bean interface for widgets to callers.
 *
 * @author Brian.Duff@oracle.com
 */
public final class Widgets extends HashStructureAdapter {
    private Widgets(HashStructure hash) {
        super(hash);
    }

    /**
     * Gets an instance of widgets that adapts the specified hash structure.
     *
     * @param hash a hash structure to adapt.
     * @return an instance of widgets.
     */
    public static Widgets getInstance(HashStructure hash) {
        return new Widgets(hash);
    }

    /**
     * Gets all registered widgets.
     *
     * @return an unmodifiable list of all registered widgets.
     */
    public List<Widget> getWidgets() {
        final List data = _hash.getAsList("widget");
        final List<Widget> widgets = new ArrayList<Widget>(data.size());
        for (Object h : data) {
            widgets.add(Widget.getInstance((HashStructure)h));
        }
        return Collections.unmodifiableList(widgets);
    }

}
