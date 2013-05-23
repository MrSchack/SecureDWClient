/* $Header: SampleBean.java 16-aug-2007.22:25:48 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/21/07 - Add copyright header. Reformat to JCS.
 */
package oracle.ide.extsamples.pageprovider;

import oracle.ide.Context;

import oracle.ide.palette2.PalettePageProvider;
import oracle.ide.palette2.PalettePages;

/**
 * SamplePageProvider
 * <p>
 * This is an example of a PalettePageProvider that adds a palette page to the
 * Component Palette(CP).  As required this class extends PalettePageProvider.
 * </p>
 * <p>
 * Element pageProvider.providerClassName in extension.xml registers
 * this class with the CP as a page provider.
 * </p>
 * <p>
 * The CP will call method createPalettePages() with the current Context.
 * Class SamplePages which extends PalettePages is constructed with the current context.
 * Using the current context SamplePages should have sufficient information to
 * assemble a list of palette pages when method getPages() is called by the CP.
 * </p>
 *
 * @see SamplePages
 * @see SamplePalettePage
 * @see SamplePaletteGroup
 * @see SamplePaletteItem
 */
public class SamplePageProvider extends PalettePageProvider {

    /**
     * Default constructor.
     *
     */
    public SamplePageProvider() {
    }

    /**
     * Override the returns SamplePages.
     *
     * @param context
     * @return PalettePages
     */
    public PalettePages createPalettePages(Context context) {
        SamplePages pages = SamplePages.getInstance();
        pages.initialize(context);
        return pages;
    }

}
