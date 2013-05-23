/* $Header: EditorPanel.java 16-aug-2007.22:08:14 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.flateditor;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.plaf.FlatTabbedPaneUI;


/**
 * The main GUI component of the example flat editor.
 */
// Note: we extend TransparentPanel to make sure that we contain no opaque
// components. In general, it's a good idea to use TransparentPanel as the
// parent of all containers in a flat editor.
final class EditorPanel extends TransparentPanel {
    EditorPanel() {
        setLayout(new BorderLayout());

        // Use the standard "finger tab" visual appearance for category tabs
        // in a flat editor.
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new FlatTabbedPaneUI());

        addTabPages(tabbedPane);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void addTabPages(JTabbedPane tabbedPane) {
        tabbedPane.add("General", createScrollPane(new GeneralPage()));
        tabbedPane.add("Widgets", createScrollPane(new WidgetsPage()));
        tabbedPane.add("Sprockets", createScrollPane(new SprocketPage()));
    }

    /**
     * Creates a scrollpane for a tab page.
     */
    private JScrollPane createScrollPane(JComponent c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIManager.getColor("window"));
        sp.getVerticalScrollBar().setUnitIncrement(20);
        sp.getHorizontalScrollBar().setUnitIncrement(20);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return sp;
    }
}
