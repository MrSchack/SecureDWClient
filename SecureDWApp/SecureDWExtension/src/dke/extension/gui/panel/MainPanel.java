package dke.extension.gui.panel;


import dke.extension.gui.panel.config.ConfigPage;
import dke.extension.gui.panel.help.HelpPage;
import dke.extension.gui.panel.insert.DimensionPage;
import dke.extension.gui.panel.insert.FactPage;
import dke.extension.gui.panel.olap.OlapPage;
import dke.extension.gui.panel.view.ViewPage;

import dke.extension.logging.MyLogger;

import dke.extension.logic.Controller;

import dke.extension.logic.ControllerImpl;

import dke.extension.mvc.SecureDWModel;

import java.awt.BorderLayout;

import java.io.IOException;

import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.plaf.FlatTabbedPaneUI;


/**
 * SecureDW main GUI component.
 */
public class MainPanel extends TransparentPanel {
    @SuppressWarnings("compatibility:7732353150938544521")
    private static final long serialVersionUID = -1775992474646200405L;
    private JScrollPane viewPage;
    private JScrollPane olapPage;
    private JScrollPane factPage;
    private JScrollPane dimPage;
    private JScrollPane configPage;
    private JScrollPane helpPage;
    
    private JTabbedPane tabbedPane;

    private SecureDWModel model;
    private Controller ctrl;
    
    private String viewName = "View";
    
    public MainPanel() {
        setLayout(new BorderLayout());
        model = new SecureDWModel();

        tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new FlatTabbedPaneUI());
              
        addTabPages(tabbedPane); // create SecureDW View
        add(tabbedPane, BorderLayout.CENTER);
        enableScrollPanes(false); // disable all scroll panes
        
        ctrl = ControllerImpl.getInstance(model);
        ctrl.setModel(model);
        
        // change listener in order to update the star schema tree
        tabbedPane.addChangeListener(new ChangeListener() {
              public void stateChanged(ChangeEvent e) {
                  JTabbedPane source = (JTabbedPane) e.getSource(); 
                  if (source.getTitleAt(source.getSelectedIndex()).equals(viewName)) {
                      model.updateTree();
                  }
              }
          });
  
        try {
            MyLogger.logMessage("Start SecureDW initialization ... ");
            ctrl.initialize();
            MyLogger.logMessage("... SecureDW initialization successfully completed!");
            
            enableScrollPanes(true);
        } catch (SQLException e) {
            MyLogger.logMessage(e.getMessage());
            enableScrollPanes(true);
            tabbedPane.setSelectedComponent(configPage);
        } catch (IOException e) {
            MyLogger.logMessage(e.getMessage());
        }
    }

    private void addTabPages(JTabbedPane tabbedPane) {
        initPages();
        tabbedPane.add(viewName, this.viewPage);
        tabbedPane.add("OLAP", this.olapPage);
        tabbedPane.add("Fact", this.factPage);
        tabbedPane.add("Dimension", this.dimPage);
        tabbedPane.add("Config", this.configPage);
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
        this.viewPage = createScrollPane(new ViewPage(model));
        this.olapPage = createScrollPane(new OlapPage());
        this.factPage = createScrollPane(new FactPage());
        this.dimPage = createScrollPane(new DimensionPage());
        this.configPage = createScrollPane(new ConfigPage());
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
        this.configPage.setEnabled(enabled);
        this.helpPage.setEnabled(enabled);
    }
}

