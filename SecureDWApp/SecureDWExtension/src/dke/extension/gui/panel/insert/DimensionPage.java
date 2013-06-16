package dke.extension.gui.panel.insert;

import dke.extension.logic.Controller;
import dke.extension.logic.ControllerImpl;
import dke.extension.mvc.SecureDWModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import oracle.javatools.ui.Header;
import oracle.javatools.ui.HeaderPanel;
import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.VerticalFlowLayout;


public class DimensionPage extends TransparentPanel {

    private final JButton test = new JButton();

    public DimensionPage() {
        setLayout(new VerticalFlowLayout());

        layoutComponents();
    }

    private void layoutComponents() {
        addPageHeader("Dimension members");

        addSubPanel("Insert dimension members",
                    "Select dimesion to insert new members",
                    new DimensionPanel());
    }


    private void addSubPanel(String title, String hint, JComponent component) {

        HeaderPanel stuff = new HeaderPanel();
        stuff.getHeader().setText(title);
        stuff.getHeader().setLevel(Header.Level.SUB);
        stuff.setStaticHelpText(hint);
        stuff.setHostedComponent(component);

        add(stuff);
    }

    private void addPageHeader(String pageTitle) {
        Header pageHeader = new Header();
        pageHeader.setText(pageTitle);
        pageHeader.setLevel(Header.Level.PAGE);

        add(pageHeader);
    }

}
