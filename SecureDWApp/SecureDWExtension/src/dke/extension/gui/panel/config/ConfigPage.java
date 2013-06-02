package dke.extension.gui.panel.config;

import javax.swing.JComponent;

import oracle.javatools.ui.Header;
import oracle.javatools.ui.HeaderPanel;
import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.VerticalFlowLayout;


public class ConfigPage extends TransparentPanel {
    public ConfigPage() {
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new VerticalFlowLayout());

        addPageHeader("General");

        addSubPanel("Connection",
                    "Enter connection details or import a connection file",
                    new ConnectionPanel());
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
