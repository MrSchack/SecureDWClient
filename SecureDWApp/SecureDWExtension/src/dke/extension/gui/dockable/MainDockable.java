package dke.extension.gui.dockable;


import dke.extension.gui.panel.MainPanel;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JComponent;

import oracle.ide.docking.DockableWindow;
import oracle.ide.layout.ViewId;

import oracle.javatools.icons.OracleIcons;


public final class MainDockable extends DockableWindow {
    private static final String VIEW_NAME = "MainDockable";
    public static final ViewId VIEW_ID =
        new ViewId(MainDockableFactory.FAMILY, VIEW_NAME);
    private JComponent ui;

    public MainDockable() {
        super(VIEW_ID.getId());
    }

    public Component getGUI() {
        if (ui == null) {
            ui = new MainPanel();
        }

        return ui;
    }

    public String getTitleName() {
        return "SecureDW View";
    }

    public String getTabName() {
        return "SecureDW View";
    }

    @Override
    public Icon getTabIcon() {
        return OracleIcons.getIcon(OracleIcons.USER);
    }
}
