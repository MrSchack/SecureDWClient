package dke.extension.gui.panel;


import dke.extension.gui.panel.config.ConfigPage;
import dke.extension.gui.panel.help.HelpPage;
import dke.extension.gui.panel.insert.DimensionPage;
import dke.extension.gui.panel.insert.FactPage;
import dke.extension.gui.panel.olap.OlapPage;
import dke.extension.gui.panel.view.ViewPage;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.plaf.FlatTabbedPaneUI;


/**
 * SecureDW main GUI component.
 */
public class MainPanel extends TransparentPanel {
    public MainPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new FlatTabbedPaneUI());

        addTabPages(tabbedPane);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void addTabPages(JTabbedPane tabbedPane) {
        tabbedPane.add("View", createScrollPane(new ViewPage()));
        tabbedPane.add("OLAP", createScrollPane(new OlapPage()));
        tabbedPane.add("Fact", createScrollPane(new FactPage()));
        tabbedPane.add("Dimension", createScrollPane(new DimensionPage()));
        tabbedPane.add("Config", createScrollPane(new ConfigPage()));
        tabbedPane.add("Help", createScrollPane(new HelpPage()));
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

