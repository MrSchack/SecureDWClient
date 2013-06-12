package dke.extension.gui.panel;


import dke.extension.data.preferencesData.ExtensionPreferencesData;
import dke.extension.data.preferencesData.LocalConnectionData;
import dke.extension.gui.panel.config.ConfigPage;
import dke.extension.gui.panel.help.HelpPage;
import dke.extension.gui.panel.insert.DimensionPage;
import dke.extension.gui.panel.insert.FactPage;
import dke.extension.gui.panel.olap.OlapPage;
import dke.extension.gui.panel.view.ViewPage;

import dke.extension.logging.MyLogger;

import dke.extension.logic.Controller;

import dke.extension.logic.ControllerImpl;

import java.awt.BorderLayout;

import java.io.File;
import java.io.IOException;

import java.sql.SQLException;

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
    private JScrollPane viewPage;
    private JScrollPane olapPage;
    private JScrollPane factPage;
    private JScrollPane dimPage;
    private JScrollPane configPane;
    private JScrollPane helpPage;
    
    public MainPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new FlatTabbedPaneUI());
        configPane = createScrollPane(new ConfigPage());
       
        addTabPages(tabbedPane);
        add(tabbedPane, BorderLayout.CENTER);
       
        Controller ctrl = new ControllerImpl();
  
        try {
            MyLogger.logMessage("Start initialization ... ");
            // disable all scroll panes
            ctrl.checkInitState();
            MyLogger.logMessage("... initialization successfully completed!");
        } catch (SQLException e) {
            MyLogger.logMessage(e.getMessage());
            tabbedPane.setSelectedComponent(configPane);
        } catch (IOException e) {
            MyLogger.logMessage(e.getMessage());
            File db = new File(
                ExtensionPreferencesData.getExtensionDir() + File.separator +
                ExtensionPreferencesData.getSecureDWFileDir() + File.separator + 
                LocalConnectionData.DBNAME);
            
            db.delete(); //TODO: for testing purpose
        }
    }

    private void addTabPages(JTabbedPane tabbedPane) {
        initPages();
        tabbedPane.add("View", this.viewPage);
        tabbedPane.add("OLAP", this.olapPage);
        tabbedPane.add("Fact", this.factPage);
        tabbedPane.add("Dimension", this.dimPage);
        tabbedPane.add("Config", this.configPane);
        tabbedPane.add("Help", this.helpPage);
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

    /*
    * Initializes alle pages
    */
    private void initPages() {
        this.viewPage = createScrollPane(new ViewPage());
        this.olapPage = createScrollPane(new OlapPage());
        this.factPage = createScrollPane(new FactPage());
        this.dimPage = createScrollPane(new DimensionPage());
        this.configPane = createScrollPane(new ConfigPage());
        this.helpPage = createScrollPane(new HelpPage());
    }
    
    /*
     * Enables or disables all tabs.
     */
    private void enableScrollPanes(boolean enabled) {
        this.viewPage.setEnabled(enabled);
        this.olapPage.setEnabled(enabled);
        this.factPage.setEnabled(enabled);
        this.dimPage.setEnabled(enabled);
        this.configPane.setEnabled(enabled);
        this.helpPage.setEnabled(enabled);
    }
}

